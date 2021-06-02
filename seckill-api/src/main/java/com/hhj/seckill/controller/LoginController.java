package com.hhj.seckill.controller;

import cn.hutool.core.bean.BeanUtil;
import com.hhj.seckill.common.Result;
import com.hhj.seckill.common.enums.ErrorEnum;
import com.hhj.seckill.common.excetion.MyException;
import com.hhj.seckill.common.util.JwtUtil;
import com.hhj.seckill.entry.User;
import com.hhj.seckill.service.UserService;
import com.hhj.seckill.vo.LoginVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import com.hhj.seckill.common.util.MdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author virtual
 * @Date 2021/6/1 10:23
 * @Version 1.0
 */
@RestController
@Slf4j
@Api("登录控制")
public class LoginController {

    @Autowired
    UserService service;

    @Autowired
    MdUtil md5Util;

    @Autowired
    JwtUtil jwtUtil;


    @PostMapping(path = {"login"})
    public Result doLogin(@RequestBody @Validated LoginVo vo, HttpServletResponse response){
        // 通过昵称查找用户
        User user = service.selectByNick(vo.getNick());
        if(user==null){
            log.error(ErrorEnum.USERNAME_OR_PASSWORD_WRONG.getMsg());
            throw new MyException(ErrorEnum.USERNAME_OR_PASSWORD_WRONG);
        }
        // 判断密码
        String temp = md5Util.md5(vo.getPassword(), user.getSalt());
        if(! temp.equals(user.getPassword())){
            // 登录失败
            log.error(ErrorEnum.USERNAME_OR_PASSWORD_WRONG.getMsg());
            throw new MyException(ErrorEnum.USERNAME_OR_PASSWORD_WRONG);
        }
        log.info("用户{}：{}上线了",user.getId(),user.getNick());
        // 返回jwt给前端
        // 清空redis中的旧token
        String jwt = jwtUtil.generateToken(user.getId());
        System.out.println(jwt);
        response.setHeader("Authorization",jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");
        User user1 = new User();
        BeanUtil.copyProperties(user,user1, new String[]{"password", "salt"});
        return Result.success(user1,"登录成功");
    }

//    @PostMapping("/register")
//    public Result doRegister(@RequestBody )

}
