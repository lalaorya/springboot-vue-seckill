package com.hhj.seckill.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hhj.seckill.entry.Good;
import com.hhj.seckill.mapper.GoodMapper;
import com.hhj.seckill.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author virtual
 * @Date 2021/5/29 17:18
 * @Version 1.0
 */
@Service
public class GoodServiceImpl implements GoodService {

    @Autowired
    GoodMapper mapper;

    @Override
    public List<Good> selectList() {
        return mapper.selectList();
    }

    @Override
    public PageInfo<Good> selectPage(int curPage, int size) {
        PageHelper.startPage(curPage,size);
        List<Good> goods = selectList();
        System.out.println(goods.toString());
        Page page = PageHelper.startPage(curPage, size);
        PageInfo<Good> info = new PageInfo<>(goods);
        System.out.println(info.toString());

        return info;
    }
}
