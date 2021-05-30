package com.hhj.seckill.mapper;

import com.hhj.seckill.entry.SecGood;
import com.hhj.seckill.vo.SecGoodVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SecGoodMapper {
    /**
     * 插入一个秒杀商品
     * @return
     */
//    @Insert("        insert into\n" +
//            "        secgood(good_id,`name`,stock,price,sec_price,start_time,end_time)\n" +
//            "        values(#{goodId},#{name},#{stock},#{price},#{secPrice},#{startTime},#{endTime})")
    void add(SecGood secGood);

//    @Select("select * from secgood")
    List<SecGood> selectList();


    /**
     * 通过id查询秒杀商品 联表查询
     */
    SecGoodVo selectById(int id);
}
