package com.hhj.seckill.controller;

import com.github.pagehelper.PageInfo;
import com.hhj.seckill.common.Result;
import com.hhj.seckill.entry.Good;
import com.hhj.seckill.service.GoodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author virtual
 * @Date 2021/5/29 17:23
 * @Version 1.0
 */
@RestController
@RequestMapping("/good")
@Api("操作商品表")
public class GoodController {

    @Autowired
    GoodService service;

    @GetMapping("list")
    @ApiOperation("分页查询")
    public Result page(int curpage,int size){
        PageInfo<Good> goodPageInfo = service.selectPage(curpage, size);
        return Result.success(goodPageInfo);
    }
}
