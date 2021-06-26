package com.hhj.seckill.controller;

import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import com.hhj.seckill.common.Result;
import com.hhj.seckill.common.enums.ErrorEnum;
import com.hhj.seckill.common.excetion.MyException;
import com.hhj.seckill.common.util.CaptchaUtils;
import com.hhj.seckill.common.util.JwtUtil;
import com.hhj.seckill.common.util.RedisUtil;
import com.hhj.seckill.entry.User;
import com.hhj.seckill.service.UserService;
import com.hhj.seckill.vo.LoginVo;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.hhj.seckill.common.util.MdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @Author virtual
 * @Date 2021/6/1 10:23
 * @Version 1.0
 */
@RestController
@Slf4j
@Api("登录控制")
public class LoginController {

    private final String CODE="seckill:userId:code:";

    @Autowired
    UserService service;

    @Autowired
    MdUtil md5Util;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    RedisUtil redisUtil;


    @PostMapping(path = {"login"})
    public Result doLogin(@RequestBody @Validated LoginVo vo, HttpServletResponse response, HttpServletRequest request){
        // 判断验证码
        String uuid = request.getHeader("capityUUID").replace('"',' ').trim();
        System.out.println(uuid);
        Object code = redisUtil.getObj(CODE+uuid,Object.class);
        log.info(code.toString());
        if(code==null){
            throw new MyException(ErrorEnum.CAPTCHA_EXPIRE);
        }else if (!(code.equals(vo.getCode()))){
            throw new MyException(ErrorEnum.CAPTCHA_WRONG);
        }

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
        // 把token存进redis，默认过期时间为7天
        redisUtil.set(jwt,0,7, TimeUnit.DAYS);
        response.setHeader("Authorization",jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");
        User user1 = new User();
        BeanUtil.copyProperties(user,user1, new String[]{"password", "salt"});
        return Result.success(user1,"登录成功");
    }



    @GetMapping("getCaptcha")
    @ApiOperation("获取验证码")
    public Result getCaptcha(HttpServletResponse response){
        String s = UUID.randomUUID().toString();
        log.info(s);
        response.addHeader("uuid",s);
        LineCaptcha lineCaptcha = CaptchaUtils.generateCode();
        String code = lineCaptcha.getCode();
        redisUtil.set(CODE+s,code,60);
        return Result.success(lineCaptcha.getImageBase64Data());
    }

}
