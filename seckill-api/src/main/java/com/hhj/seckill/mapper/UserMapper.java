package com.hhj.seckill.mapper;

import com.hhj.seckill.entry.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author virtual
 * @Date 2021/5/31 22:45
 * @Version 1.0
 */
@Mapper
public interface UserMapper {

    public User selectByNick(String nick);
}
