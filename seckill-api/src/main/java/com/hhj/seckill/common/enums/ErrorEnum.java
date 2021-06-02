package com.hhj.seckill.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 异常枚举类
 * @Author virtual
 * @Date 2021/6/1 10:57
 * @Version 1.0
 */
@AllArgsConstructor
@Getter
public enum ErrorEnum {

    // 登录模块
    LOGIN_FAIT(10001,"登录失败"),
    CAPTCHA_WRONG(10002,"验证码错误"),
    USERNAME_OR_PASSWORD_WRONG(10003,"用户名或密码错误"),
    TOKEN_EXPIRED(10004,"token已过期，请重新登录"),

    // 秒杀模块枚举类型
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
