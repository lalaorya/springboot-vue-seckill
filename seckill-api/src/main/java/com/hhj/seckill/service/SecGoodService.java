package com.hhj.seckill.service;

import com.github.pagehelper.PageInfo;
import com.hhj.seckill.entry.Good;
import com.hhj.seckill.entry.SecGood;
import com.hhj.seckill.vo.SecGoodVo;

import java.util.List;

/**
 * @Author virtual
 * @Date 2021/5/29 22:48
 * @Version 1.0
 */
public interface SecGoodService {
    /**
     * 添加一个秒杀商品
     */
    public void add(SecGood secGood);

    List<SecGood> selectList();


    /**
     * 分页查询所有的商品
     * @return
     */
    public PageInfo<SecGood> selectPage(int curPage, int size);

    /**
     * 通过id查询秒杀商品
     * 连表查询
     */
    public SecGoodVo selectById(int id);

    public SecGood selectById2(int id);

    /**
     * 通过秒杀流水号减库存
     * @param id
     */
    int reduceStock(int id);

    /**
     * 秒杀商品库存预热
     */
    boolean prepare(int id);
}
