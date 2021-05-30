package com.hhj.seckill.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

/**
 * @Author virtual
 * @Date 2021/5/29 15:46
 * @Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SecOrder {
    private int id;
    private int goodId;
    private int userId;
    private int status;
    private Date createTime;
    private Date payTime;
}
