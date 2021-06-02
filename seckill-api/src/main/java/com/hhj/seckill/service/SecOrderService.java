package com.hhj.seckill.service;

import java.util.Date;

/**
 * @Author virtual
 * @Date 2021/5/31 22:46
 * @Version 1.0
 */
public interface SecOrderService {

    public boolean generateOrder(int goodId, int userId, Date createTime);
}
