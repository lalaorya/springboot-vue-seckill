package com.hhj.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @Author virtual
 * @Date 2021/6/1 10:25
 * @Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginVo {
    @NotBlank
    private String nick;
    @NotBlank
    private String password;
}
