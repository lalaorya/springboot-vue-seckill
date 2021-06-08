package com.hhj.seckill.common.util;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.hhj.seckill.entry.User;
import com.hhj.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 批量注册用户工具类
 * @Author virtual
 * @Date 2021/6/7 22:59
 * @Version 1.0
 */

@Component
public class RegisterUtil {

    @Autowired
    MdUtil util;

    @Autowired
    UserService service;

    public  void doRegister(){
//        new User(null,null,"admin1",);

        for (int i = 1; i <= 1000; i++) {
            String salt= RandomUtil.randomString(10);
            // 前端一次加密
            String md5 = SecureUtil.md5("admin"+i);
            String password = util.md5(md5, salt);
            User user = new User(null, password, "admin" + i, salt, new Date(), null, 0);
            service.addUser(user);
        }
    }


}
