package com.hhj.seckill.common.excetion;

import com.hhj.seckill.common.enums.ErrorEnum;
import lombok.Data;

/**
 * @deprecated 自定义异常类
 * @Author virtual
 * @Date 2021/6/1 13:15
 * @Version 1.0
 */
@Data
public class MyException extends RuntimeException{
    private static final long serialVersionUID=-8502128347608362068L;
    private String msg;
    private int code;

    public MyException(ErrorEnum errorEnum){
        super(errorEnum.getMsg());
        this.msg = errorEnum.getMsg();
        this.code = errorEnum.getCode();
    }

    public MyException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public MyException(ErrorEnum errorEnum, Throwable e) {
        super(errorEnum.getMsg(), e);
        this.msg = errorEnum.getMsg();
        this.code = errorEnum.getCode();
    }


}
