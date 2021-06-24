package com.hhj.seckill.mapper;

import com.hhj.seckill.entry.Good;
import com.hhj.seckill.entry.SecOrder;
import com.hhj.seckill.vo.SecKillOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @Author virtual
 * @Date 2021/5/29 15:48
 * @Version 1.0
 */
@Mapper
public interface SecOrderMapper {

    public boolean generateOrder(SecKillOrder secKillOrder);

    public List<SecOrder> selectList();

    public boolean payById(@Param("id") int id,@Param("payTime") Date payTime);

    public Long selectBySecIdAndUserId(@Param("secId") int secId, @Param("userId") int userId);

}
