package com.hhj.seckill.entry;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

/**
 * @Author virtual
 * @Date 2021/5/29 15:43
 * @Version 1.0
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class SecGood {

    private int id;
    private int goodId;
    private String name;
    private int stock;
    private double price;
    private double secPrice;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
}
