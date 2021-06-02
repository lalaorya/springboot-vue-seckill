package com.hhj.seckill.service.Impl;

import com.hhj.seckill.entry.User;
import com.hhj.seckill.mapper.UserMapper;
import com.hhj.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author virtual
 * @Date 2021/5/31 22:46
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper mapper;
    @Override
    public User selectByNick(String nick) {
        User user = mapper.selectByNick(nick);
        return user;
    }
}
