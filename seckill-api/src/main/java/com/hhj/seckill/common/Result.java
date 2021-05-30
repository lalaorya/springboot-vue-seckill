package com.hhj.seckill.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<T> {

    /**
     * 响应成功200
     * 响应失败400
     */
    int code;
    // 失败不需要返回数据，返回失败原因
    T data;

    public Result(T object){
        this.code=200;
        this.data=object;
    }



    public static Result success(Object data){
        return new Result(data);
    }



    public static Result error(Object msg){
        return new Result(400,msg);
    }
}
