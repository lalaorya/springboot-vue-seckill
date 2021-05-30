package com.hhj.seckill.config;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Plugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @Author virtual
 * @Date 2021/5/29 16:55
 * @Version 1.0
 */
@Configuration
public class MybatisConfig {
    // 配置分页插件
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("dialect", "mysql");
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        pageHelper.setProperties(p);
        return pageHelper;
    }

    }
