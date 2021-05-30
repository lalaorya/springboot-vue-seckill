package com.hhj.seckill.service;

import com.github.pagehelper.PageInfo;
import com.hhj.seckill.entry.Good;

import java.util.List;

/**
 * @Author virtual
 * @Date 2021/5/29 17:15
 * @Version 1.0
 */
public interface GoodService {
    /**
     * 查询所有商品
     */
    public List<Good> selectList();

    /**
     * 分页查询所有的商品
     * @return
     */
    public PageInfo<Good> selectPage(int curPage, int size);
}
