package com.hhj.seckill.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hhj.seckill.entry.Good;
import com.hhj.seckill.entry.SecGood;
import com.hhj.seckill.mapper.SecGoodMapper;
import com.hhj.seckill.service.SecGoodService;
import com.hhj.seckill.vo.SecGoodVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author virtual
 * @Date 2021/5/29 22:49
 * @Version 1.0
 */
@Service
public class SecGoodServiceImpl implements SecGoodService {

    @Autowired
    SecGoodMapper mapper;
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
        return null;
    }
}
