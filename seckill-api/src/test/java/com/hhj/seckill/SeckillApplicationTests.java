package com.hhj.seckill;

import com.hhj.seckill.service.GoodService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SeckillApplicationTests {

    @Autowired
    GoodService service;

    @Test
    void contextLoads() {
        service.selectPage(1,10);
    }



}
