package com.hhj.seckill.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @Author virtual
 * @Date 2021/5/29 15:48
 * @Version 1.0
 */
@Mapper
public interface SecOrderMapper {

    public boolean generateOrder(@Param("goodId") int goodId,
                             @Param("userId") int userId,
                             @Param("createTime") Date createTime);

}
