package com.hhj.seckill.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hhj.seckill.entry.Good;
import com.hhj.seckill.entry.SecOrder;
import com.hhj.seckill.mapper.SecOrderMapper;
import com.hhj.seckill.service.SecOrderService;
import com.hhj.seckill.vo.SecKillOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author virtual
 * @Date 2021/5/31 22:46
 * @Version 1.0
 */
@Service
public class SecOrderServiceImpl implements SecOrderService {
    @Autowired
    SecOrderMapper secOrderMapper;

    @Override
    public PageInfo<SecOrder> selectPage(int curPage, int size) {
        PageHelper.startPage(curPage,size);
        List<SecOrder> goods = secOrderMapper.selectList();
        System.out.println(goods.toString());
        Page page = PageHelper.startPage(curPage, size);
        PageInfo<SecOrder> info = new PageInfo<>(goods);
        System.out.println(info.toString());

        return info;
    }

    @Override
    public boolean generateOrder(SecKillOrder secKillOrder) {
        boolean i = secOrderMapper.generateOrder(secKillOrder);
        return i;
    }

    @Override
    public boolean payById(int id, Date payTime) {
        boolean b = secOrderMapper.payById(id, payTime);
        return b;
    }
}
