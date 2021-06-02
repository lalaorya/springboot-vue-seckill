package com.hhj.seckill.common.util;

import cn.hutool.crypto.SecureUtil;
import org.springframework.stereotype.Component;

/**
 * @Author virtual
 * @Date 2021/5/31 23:54
 * @Version 1.0
 */
@Component
public class MdUtil {

    /**
     *
     * @param firstPassword
     * @param salt 该用户的盐值
     * @return
     */
    public String md5(String firstPassword,String salt){
        // 通过盐值加密
        firstPassword= ""+salt.charAt(0)+salt.charAt(2)+firstPassword+salt.charAt(4)+salt.charAt(5);
        return SecureUtil.md5(firstPassword);
    }
}
