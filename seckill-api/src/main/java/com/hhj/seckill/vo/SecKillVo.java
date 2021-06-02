package com.hhj.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @Author virtual
 * @Date 2021/6/1 16:39
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecKillVo {
    // 秒杀流水号
    private int secId;
    // 用户id
    private int userId;
    // md5
    private String md5;
}
