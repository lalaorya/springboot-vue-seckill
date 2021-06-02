package com.hhj.seckill.controller;

import com.github.pagehelper.PageInfo;
import com.hhj.seckill.common.Result;
import com.hhj.seckill.entry.Good;
import com.hhj.seckill.entry.SecOrder;
import com.hhj.seckill.service.SecOrderService;
import com.hhj.seckill.vo.SecKillVo;
import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
