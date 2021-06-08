package com.hhj.seckill.controller;

import com.github.pagehelper.PageInfo;
import com.hhj.seckill.common.Result;
import com.hhj.seckill.common.enums.SeckillEnum;
import com.hhj.seckill.entry.SecGood;
import com.hhj.seckill.service.SecGoodService;
import com.hhj.seckill.vo.SecGoodVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author virtual
 * @Date 2021/5/29 20:37
 * @Version 1.0
 */
@RestController
@RequestMapping("/secgood")
@Api("秒杀商品表")
@Slf4j
public class SecGoodController {

    @Autowired
    SecGoodService secGoodService;

    @PostMapping("add")
    public Result add(@RequestBody SecGood map){
        System.out.println(map.toString());
        System.out.println(map.getStartTime().toString());
        secGoodService.add(map);
        int add = 1;
        add=1;
        if(add>0){
            log.info("新增了一个秒杀商品");
            return Result.success(null);
        }
        return Result.error("操作失败");
    }

    @GetMapping("list")
    @ApiOperation("分页查询")
    public Result page(int curpage,int size){
        PageInfo<SecGood> secGoodPageInfo = secGoodService.selectPage(curpage, size);
        return Result.success(secGoodPageInfo);
    }

    @GetMapping("{id}")
    @ApiOperation("通过id查询秒杀商品")
    public Result selectById(@PathVariable("id")int id){
//        secGoodService.
        SecGoodVo secGoodVo = secGoodService.selectById(id);
        if(secGoodVo==null){
            return Result.exception(SeckillEnum.DATE_REWRITE.getMsg());
        }
        System.out.println(secGoodVo.toString());
        return Result.success(secGoodVo);
    }
}
