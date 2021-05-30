package com.hhj.seckill.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author virtual
 * @Date 2021/5/29 15:47
 * @Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private int id;
    private String telephone;
    private String password;
}
