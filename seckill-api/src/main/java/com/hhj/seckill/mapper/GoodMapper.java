package com.hhj.seckill.mapper;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hhj.seckill.entry.Good;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodMapper {


    /**查询全部商品
     * @return
     */
//    @Select("select * from good")
    public List<Good> selectList();



}
