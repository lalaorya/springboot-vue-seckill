package com.hhj.seckill.controller;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageInfo;
import com.hhj.seckill.common.Result;
import com.hhj.seckill.common.enums.ErrorEnum;
import com.hhj.seckill.entry.Good;
import com.hhj.seckill.entry.SecOrder;
import com.hhj.seckill.service.SecOrderService;
import com.hhj.seckill.vo.SecKillVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Author virtual
 * @Date 2021/5/31 17:21
 * @Version 1.0
 */
@RestController
@RequestMapping("/secorder")
@Api("秒杀订单")
public class SecOrderController {
    @Autowired
    SecOrderService service;

    @GetMapping("list")
    @ApiOperation("分页查询")
    public Result page(int curpage,int size){
        PageInfo<SecOrder> goodPageInfo = service.selectPage(curpage, size);
        return Result.success(goodPageInfo);
    }


    @PutMapping("payById")
    @ApiOperation("用户付款")
    public Result payById(@RequestParam("id")int id){
        Date date = new Date();
        boolean b = service.payById(id, date);
        if(b){
            return Result.success(DateUtil.format(date,"yyyy-MM-dd HH:mm:ss"),"付款成功");
        }
        return Result.error("付款失败");
    }

    @PostMapping("selectEntry")
    public Result selectByForm(@RequestBody SecKillVo vo){
        Long l = service.selectBySecIdAndUserId(vo.getSecId(), vo.getUserId());
        if(l != null){
            return Result.success(null,"秒杀成功");
        }

        return Result.error(ErrorEnum.QUEUE_NOW.getMsg());
    }



}
