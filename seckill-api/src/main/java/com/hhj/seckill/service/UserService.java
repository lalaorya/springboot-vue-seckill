package com.hhj.seckill.service;

import com.hhj.seckill.entry.User;

/**
 * @Author virtual
 * @Date 2021/5/31 22:46
 * @Version 1.0
 */
public interface UserService {

    User  selectByNick(String nick);

    boolean addUser(User user);
}
