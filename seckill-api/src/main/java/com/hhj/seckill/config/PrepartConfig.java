package com.hhj.seckill.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**库存预热配置类
 * 注入一个map容器
 * @Author virtual
 * @Date 2021/6/11 11:07
 * @Version 1.0
 */
@Configuration
public class PrepartConfig {

    @Bean
    public HashMap<String,Long> setMap(){
        return new HashMap<>();
    }
}
