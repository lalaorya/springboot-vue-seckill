package com.hhj.seckill.common.excetion;

import com.hhj.seckill.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author virtual
 * @Date 2021/6/1 13:22
 * @Version 1.0
 */
@Slf4j
@RestControllerAdvice
public class MyExceptionHandler {

    /**
     * 自定义异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(MyException.class)
    public Result handleMyException(MyException e){
        log.error(e.getMessage());
        return Result.exception(e.getMsg());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handler(MethodArgumentNotValidException e) {
        log.error("参数校验异常：{}", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        return Result.exception(objectError.getDefaultMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.exception(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.exception(e.getMessage());
    }

}
