package com.hhj.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
//@MapperScan(basePackages = "com.hhj.seckill.mapper")
public class SeckillApplication {

    public static void main(String[] args) {

        SpringApplication.run(SeckillApplication.class, args);

//        String uuid = StrUtil.uuid();
//        System.out.println(uuid);
//        String s = Md5Util.md5("21232f297a57a5a743894a0e4a801fc3", uuid);
//        System.out.println(s);
    }

}
