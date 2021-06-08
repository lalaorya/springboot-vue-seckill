package com.hhj.seckill.service;

import com.github.pagehelper.PageInfo;
import com.hhj.seckill.entry.SecGood;
import com.hhj.seckill.entry.SecOrder;
import com.hhj.seckill.vo.SecKillOrder;
import org.apache.ibatis.annotations.Param;

import java.awt.*;
import java.util.Date;

/**
 * @Author virtual
 * @Date 2021/5/31 22:46
 * @Version 1.0
 */
public interface SecOrderService {

    public PageInfo<SecOrder> selectPage(int curPage, int size);

    public boolean generateOrder(SecKillOrder secKillOrder);

    public boolean payById(@Param("id") int id, @Param("payTime") Date payTime);
}
