package com.hhj.seckill.service.Impl;

import cn.hutool.crypto.digest.MD5;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hhj.seckill.common.enums.ErrorEnum;
import com.hhj.seckill.common.excetion.MyException;
import com.hhj.seckill.common.util.MdUtil;
import com.hhj.seckill.common.util.RedisUtil;
import com.hhj.seckill.entry.Good;
import com.hhj.seckill.entry.SecGood;
import com.hhj.seckill.mapper.SecGoodMapper;
import com.hhj.seckill.service.SecGoodService;
import com.hhj.seckill.vo.Exposer;
import com.hhj.seckill.vo.SecGoodVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @Author virtual
 * @Date 2021/5/29 22:49
 * @Version 1.0
 */
@Service
public class SecGoodServiceImpl implements SecGoodService {

    private final String SEC_SALT="dfalskfjalsjfalk3485309-`-324348sfjaerj";

    private final String SEC_KILL_EXPOSER="seckill:exposer:";

    private final String SEC_KILL_STOCK="seckill:stock:";

    @Autowired
    SecGoodMapper mapper;

    @Autowired
    MdUtil util;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    HashMap map;

    @Override
    public void add(SecGood secGood) {
       mapper.add(secGood);

    }

    @Override
    public List<SecGood> selectList() {
        List<SecGood> secGoods = mapper.selectList();
        return secGoods;
    }

    @Override
    public PageInfo<SecGood> selectPage(int curPage, int size) {
        PageHelper.startPage(curPage,size);
        List<SecGood> goods = selectList();
        PageInfo<SecGood> info = new PageInfo<>(goods);
        return info;
    }

    @Override
    public SecGoodVo selectById(int id) {
        SecGoodVo secGoodVo = mapper.selectById(id);
        return secGoodVo;
    }

    /**
     * 高并发接口 redis缓存
     * @param id
     * @return
     */
    @Override
    public SecGood selectById2(int id) {
        SecGood secGood = mapper.selectById2(id);
        return secGood;
    }

    @Override
    public int reduceStock(int id) {
        int i = mapper.reduceStock(id);
        return i;
    }

    @Override
    public boolean prepare(int id) {
        // redis中存储秒杀暴露接口和秒杀商品库存
        SecGood secGood = selectById2(id);
        long now = System.currentTimeMillis();
        long end = secGood.getEndTime().getTime();

        if( end < now){
            // 由于pc机、浏览器的不同，客户端的时间会有偏差，因此要在后端重新做一次校验
            // 秒杀尚未开始，让前端重新调整时间
            // TODO 这里未开始的秒杀信息应该也要存进缓存
            throw new MyException(ErrorEnum.END);
        }else {
            // 返回真正地址
            String md5 = util.md5(id + "", SEC_SALT);
            Exposer exposer = new Exposer(true, md5, secGood.getId(), 0, 0, 0);
            redisUtil.set(SEC_KILL_EXPOSER+id,
                    exposer,(end-now)/1000
            );

        }

        // 秒杀商品库存预热
        redisUtil.set(SEC_KILL_STOCK+secGood.getId(),
                secGood.getStock(),
                (end-now)/1000);
        return true;

    }
}
