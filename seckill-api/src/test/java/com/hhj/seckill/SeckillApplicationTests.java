package com.hhj.seckill;

import com.hhj.seckill.service.GoodService;
import com.hhj.seckill.service.SecOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class SeckillApplicationTests {

    @Autowired
    SecOrderService secOrderService;

    @Test
    void contextLoads() {
        boolean b = secOrderService.generateOrder(22, 1, new Date());
        System.out.println(b);
    }



}
