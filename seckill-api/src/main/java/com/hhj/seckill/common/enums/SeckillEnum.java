package com.hhj.seckill.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author virtual
 * @Date 2021/6/1 16:58
 * @Version 1.0
 */
@AllArgsConstructor
@Getter
public enum  SeckillEnum {

    // 秒杀枚举类型
    SUCCESS(20001,"秒杀成功"),
    EAARY(20006,"秒杀活动尚未开始"),
    END(20002,"秒杀活动已结束"),
    REPEAT(20003,"禁止重复秒杀"),
    INNER_ERROR(20004,"系统异常"),
    DATE_REWRITE(20005,"数据被篡改"),
    STOCK_ZERT(20006,"已被抢光");

    private int code;
    private String msg;
}
