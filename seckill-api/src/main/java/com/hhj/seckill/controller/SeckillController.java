package com.hhj.seckill.controller;

import com.hhj.seckill.common.Result;
import com.hhj.seckill.common.enums.ErrorEnum;
import com.hhj.seckill.common.enums.SeckillEnum;
import com.hhj.seckill.common.excetion.MyException;
import com.hhj.seckill.entry.SecGood;
import com.hhj.seckill.service.SecGoodService;
import com.hhj.seckill.service.SecKillService;
import com.hhj.seckill.vo.Exposer;
import com.hhj.seckill.vo.SecGoodVo;
import com.hhj.seckill.vo.SecKillVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Author virtual
 * @Date 2021/6/1 16:57
 * @Version 1.0
 */
@RestController
@Slf4j
@Api("秒杀操作")
public class SeckillController {

    @Autowired
    SecGoodService secGoodService;

    @Autowired
    SecKillService secKillService;


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
        // 校验md5
        boolean b1 = secKillService.verifyMd5(vo.getMd5(), vo.getSecId());
        if (b1==false){
            throw new MyException(ErrorEnum.DATE_REWRITE.getMsg());
        }
        // 判断秒杀时间
        SecGood secGood = secGoodService.selectById2(vo.getSecId());
//        if(System.currentTimeMillis() >secGood.getEndTime().getTime()){
//            // 秒杀结束
//            return Result.success(SeckillEnum.END.getMsg());
//        }
//        if(secGood.getStartTime().getTime() > System.currentTimeMillis()){
//            // 秒杀活动尚未开始
//            return Result.success(SeckillEnum.END.getMsg());
//        }
        // 减库存 生成订单
        // 失败 事务回滚
        // 事务操作
        // TODO 重复秒杀判断
        boolean b = secKillService.doSecKill(vo.getSecId(), secGood.getGoodId(), vo.getUserId());
        return Result.success(null,SeckillEnum.SUCCESS.getMsg());
    }


}
