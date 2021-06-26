package com.hhj.seckill.service.Impl;

import com.hhj.seckill.common.Result;
import com.hhj.seckill.common.enums.ErrorEnum;
import com.hhj.seckill.common.enums.SeckillEnum;
import com.hhj.seckill.common.excetion.MyException;
import com.hhj.seckill.common.util.MdUtil;
import com.hhj.seckill.common.util.RedisUtil;
import com.hhj.seckill.entry.SecGood;
import com.hhj.seckill.mq.MqSender;
import com.hhj.seckill.service.SecGoodService;
import com.hhj.seckill.service.SecKillService;
import com.hhj.seckill.service.SecOrderService;
import com.hhj.seckill.vo.Exposer;
import com.hhj.seckill.vo.SecKillOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author virtual
 * @Date 2021/6/1 22:52
 * @Version 1.0
 */
@Service
@Slf4j
public class SecKillServiceImpl implements SecKillService {

    @Autowired
    SecGoodService secGoodService;

    @Autowired
    SecOrderService secOrderService;

    @Autowired
    MdUtil util;

    @Autowired
    MqSender sender;

    @Autowired
    RedisUtil redisUtil;

    private final String SEC_SALT="dfalskfjalsjfalk3485309-`-324348sfjaerj";

    private final String SEC_KILL_EXPOSER="seckill:exposer:";

    private final String SEC_KILL_STOCK="seckill:stock:";

    private final String SEC_kill_USER="seckill:secId:user:";


    @Override
    public SeckillEnum doSecKill (SecKillOrder secKillOrder) throws  RuntimeException{

        // 重复消费判断
        // 默认过期时间为两天
        // 这里过期时间应该要修改 但是又怕会影响性能 TODO
        StringBuffer append = new StringBuffer().append(SEC_kill_USER + secKillOrder.getSecId()).append(":").append(secKillOrder.getUserId());
        // TODO 每次都要从redis中拿实在是浪费时间，应该搞个库存预热从本地拿
//        Long exire = redisUtil.getExire(SEC_KILL_STOCK + secKillOrder.getSecId());
        if(! redisUtil.setnx(append.toString(), 1, 2 )){
            // 重复购买了
            throw new MyException(ErrorEnum.REPEAT);
        };

        // 查看redis中库存是否大于0 lua脚本保证原子性
        // 库存预热 这里一定有
        long stock = redisUtil.luaStock(SEC_KILL_STOCK + secKillOrder.getSecId());
        log.info("当前库存为{}",stock);

        if(stock < 0)    throw new MyException(ErrorEnum.STOCK_ZERT);

        // v2.0 订单放入消息队列
        sender.sendOrder(secKillOrder);

        return SeckillEnum.WAIT_QUEUE;


    }

    @Override
    @Transactional(rollbackFor = MyException.class)
    public SeckillEnum seckill(SecKillOrder secKillOrder){
        // 生成订单
        boolean res2 = secOrderService.generateOrder(secKillOrder);
        if(res2 == false){
            // 生成订单失败
            throw new MyException(ErrorEnum.REPEAT);
        }

        // 减库存
        int res = secGoodService.reduceStock(secKillOrder.getSecId());
        if(res < 1){// 减库存失败
            throw new MyException(ErrorEnum.STOCK_ZERT);
        }
        return SeckillEnum.SUCCESS;
    }

    @Override
    public Exposer exposerSecAddress(int id) {
        // 查看redis中有没有缓存
        if(redisUtil.hasKey(SEC_KILL_EXPOSER + id)){
            log.info("商品{}秒杀暴露接口命中缓存",id);
            return redisUtil.getObj(SEC_KILL_EXPOSER + id, Exposer.class);
        }

        SecGood secGood = secGoodService.selectById2(id);
        if(secGood==null){
            // 没有该商品，秒杀信息被篡改
            throw new MyException(SeckillEnum.DATE_REWRITE.getMsg());
        }else{
            long now = System.currentTimeMillis();
            long start = secGood.getStartTime().getTime();
            long end = secGood.getEndTime().getTime();

            if(now < start || end < now){
                // 由于pc机、浏览器的不同，客户端的时间会有偏差，因此要在后端重新做一次校验
                // 秒杀尚未开始，让前端重新调整时间
                // TODO 这里未开始的秒杀信息应该也要存进缓存
                return new Exposer(false,null,secGood.getId(),now,start,end);
            }else {
                // 返回真正地址
                String md5 = util.md5(id + "", SEC_SALT);
                Exposer exposer = new Exposer(true, md5, secGood.getId(), 0, 0, 0);
                redisUtil.set(SEC_KILL_EXPOSER+id,
                                exposer,(end-now)/1000
                        );
                return exposer;
            }
        }
    }

    @Override
    public boolean verifyMd5(String md5,int secId) {
        String verify = util.md5(secId + "", SEC_SALT);
        if (md5.equals(verify)){
            return true;
        }
        return false;
    }


}
