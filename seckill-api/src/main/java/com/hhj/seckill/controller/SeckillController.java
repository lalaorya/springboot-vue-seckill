package com.hhj.seckill.controller;

import cn.hutool.core.bean.BeanUtil;
import com.hhj.seckill.common.Result;
import com.hhj.seckill.common.enums.ErrorEnum;
import com.hhj.seckill.common.enums.SeckillEnum;
import com.hhj.seckill.common.excetion.MyException;
import com.hhj.seckill.common.util.RedisUtil;
import com.hhj.seckill.entry.SecGood;
import com.hhj.seckill.service.SecGoodService;
import com.hhj.seckill.service.SecKillService;
import com.hhj.seckill.vo.Exposer;
import com.hhj.seckill.vo.SecGoodVo;
import com.hhj.seckill.vo.SecKillOrder;
import com.hhj.seckill.vo.SecKillVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Author virtual
 * @Date 2021/6/1 16:57
 * @Version 1.0
 */
@RestController
@Slf4j
@Api("秒杀操作")
public class SeckillController implements InitializingBean {

    private final String SEC_KILL_STOCK="seckill:stock:";

    @Autowired
    SecGoodService secGoodService;

    @Autowired
    SecKillService secKillService;

    @Autowired
    RedisUtil util;


    @GetMapping("/exposer/{secId}")
    public Result getExposer(@PathVariable("secId")int id){
        Exposer exposer = secKillService.exposerSecAddress(id);
        if (! exposer.isExposed()){
            return Result.error(exposer);
        }
        return Result.success(exposer);
    }



    @PostMapping("/seckill")
    public Result doSecKill(@RequestBody SecKillVo vo){

        boolean b1 = secKillService.verifyMd5(vo.getMd5(), vo.getSecId());
        if (b1==false){
            // 秒杀接口地址错误
            throw new MyException(ErrorEnum.DATE_REWRITE.getMsg());
        }

//        SecGood secGood = secGoodService.selectById2(vo.getSecId());
        // 减库存 生成订单
        // 失败 事务回滚
        // 事务操作
        // TODO 重复秒杀判断
//        if(secGood.getStock()==0){
//            throw new MyException(ErrorEnum.STOCK_ZERT);
//        }

        SecKillOrder secKillOrder = new SecKillOrder(vo.getSecId(), vo.getUserId(), new Date());
        SeckillEnum seckillEnum = secKillService.doSecKill(secKillOrder);
        return Result.success(null,seckillEnum.getMsg());
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        // 库存预热
        // 读取所有秒杀商品及其库存 进行redis缓存
        List<SecGood> secGoods = secGoodService.selectList();
        for(SecGood secGood:secGoods){
            util.set(SEC_KILL_STOCK+secGood.getId(),
                            secGood.getStock(),
                    secGood.getEndTime().getTime()-System.currentTimeMillis());
        }
    }
}
