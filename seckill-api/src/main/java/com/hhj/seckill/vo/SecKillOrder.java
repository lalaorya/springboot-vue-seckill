package com.hhj.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author virtual
 * @Date 2021/6/6 14:41
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecKillOrder {
    // 秒杀流水号
    private int secId;
    // 用户id
    private int userId;
    // 创建时间
    private Date createTime;

}
