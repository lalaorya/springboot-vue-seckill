package com.hhj.seckill;

import com.hhj.seckill.common.util.CaptchaUtils;
import com.hhj.seckill.common.util.RegisterUtil;
import com.hhj.seckill.mq.MqConsumer;
import com.hhj.seckill.mq.MqSender;
import com.hhj.seckill.service.GoodService;
import com.hhj.seckill.service.SecOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.Collections;
import java.util.Date;

@SpringBootTest
class SeckillApplicationTests {

    @Autowired
    SecOrderService secOrderService;

    @Autowired
    MqSender sender;

    @Autowired
    MqConsumer consumer;

    @Autowired
    RegisterUtil util;

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
//        String lua="if redis.cal(\"get\",KEYS[1] )> 0 then\n" +
//                "\treturn redis.call(\"decr\",KEYS[1])\n" +
//                "else\n" +
//                "\treturn 0\n" +
//                "end";
//        DefaultRedisScript<Long> longDefaultRedisScript = new DefaultRedisScript<>(lua,Long.class);
////        longDefaultRedisScript.setScriptSource(new ResourceScriptSource(
////                new ClassPathResource("/delstock.lua")
////        ));
////        longDefaultRedisScript.setResultType(Long.class);
//        Object execute = redisTemplate.execute(longDefaultRedisScript, Collections.singletonList("seckill:stock:23"));
//        System.out.println(execute);
//        util.doRegister();
        CaptchaUtils.generateCode();

    }



}
