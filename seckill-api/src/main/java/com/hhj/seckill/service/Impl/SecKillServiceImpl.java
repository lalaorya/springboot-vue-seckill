package com.hhj.seckill.service.Impl;

import com.hhj.seckill.common.Result;
import com.hhj.seckill.common.enums.ErrorEnum;
import com.hhj.seckill.common.enums.SeckillEnum;
import com.hhj.seckill.common.excetion.MyException;
import com.hhj.seckill.common.util.MdUtil;
import com.hhj.seckill.entry.SecGood;
import com.hhj.seckill.service.SecGoodService;
import com.hhj.seckill.service.SecKillService;
import com.hhj.seckill.service.SecOrderService;
import com.hhj.seckill.vo.Exposer;
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
public class SecKillServiceImpl implements SecKillService {

    @Autowired
    SecGoodService secGoodService;

    @Autowired
    SecOrderService secOrderService;

    @Autowired
    MdUtil util;

    private final String SEC_SALT="dfalskfjalsjfalk3485309-`-324348sfjaerj";


    @Override
    @Transactional(rollbackFor = MyException.class)
    public boolean doSecKill (int secId, int goodId, int userId) throws  RuntimeException{
        // 减库存
        int res = secGoodService.reduceStock(secId);
        if(res < 1){// 减库存失败
            throw new MyException(ErrorEnum.STOCK_ZERT);
        }
        // 生成订单
        boolean res2 = secOrderService.generateOrder(goodId, userId,new Date());
        if(res2 == false){
            // 生成订单失败
            throw new MyException(ErrorEnum.INNER_ERROR);
        }
        return true;


    }

    @Override
    public Exposer exposerSecAddress(int id) {
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
                return new Exposer(false,null,secGood.getId(),now,start,end);
            }else {
                // 返回真正地址
                String md5 = util.md5(id + "", SEC_SALT);
                return new Exposer(true,md5,secGood.getId(),0,0,0);
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
