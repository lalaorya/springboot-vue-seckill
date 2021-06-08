package com.hhj.seckill.service;

import com.hhj.seckill.common.Result;
import com.hhj.seckill.common.enums.SeckillEnum;
import com.hhj.seckill.vo.Exposer;
import com.hhj.seckill.vo.SecKillOrder;

/**
 * @Author virtual
 * @Date 2021/6/1 22:51
 * @Version 1.0
 */
public interface SecKillService {

    /**
     * 执行秒杀操作 减库存 生成订单
     * @param secId 秒杀流水号
     * @param goodId 商品id
     * @param userId 用户id
     * @return
     */
    public SeckillEnum doSecKill(SecKillOrder secKillOrder);

    /**前端秒杀要发送两个请求，第一个获得秒杀信息（是否开启，秒杀真正地址）
     * 第二个才执行秒杀
     * 暴露秒杀地址
     * @param id 秒杀流水号
     * @return
     */
    public Exposer exposerSecAddress(int id);

    /**
     * 校验前端加密的md5，看看数据有没有修改
     * @param secId
     * @return
     */
    public boolean verifyMd5(String md5,int secId);

    public SeckillEnum seckill(SecKillOrder secKillOrder);
}
