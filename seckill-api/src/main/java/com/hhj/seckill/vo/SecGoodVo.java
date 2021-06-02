package com.hhj.seckill.vo;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhj.seckill.entry.Good;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author virtual
 * @Date 2021/5/29 20:45
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
/**
 * 返回商品详情的vo
 */
public class SecGoodVo {

    private int id;
    private int goodId;
    private String name;
    private double price;
    private double secPrice;
    private int stock;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    private Good good;
//    private List<String> time;
}
