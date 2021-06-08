package com.hhj.seckill.entry;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private int secId;
    private int userId;
    private int status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date payTime;
}
