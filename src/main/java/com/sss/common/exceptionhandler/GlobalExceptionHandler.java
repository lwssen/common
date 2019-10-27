package com.sss.common.exceptionhandler;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.sss.common.exception.CustomException;
import com.sss.common.util.ResponseDataHelper;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author: sss
 * @date: 2019-09-06 17:29
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 未授权异常
     **/
    @ExceptionHandler(UnauthorizedException.class)
    public Object unAuthor(){
        return "you not have permission!!!";
    }

    /**
     * 自定义异常
     **/
    @ExceptionHandler(CustomException.class)
    public Object handlerCustom(CustomException ex){
        return ResponseDataHelper.fail(ex.getCode(),ex.getMessage());
    }


    @ExceptionHandler(SignatureVerificationException.class)
    public Object handlerCustom2(){
        return ResponseDataHelper.fail("token不合法！！！");
    }

    @ExceptionHandler(TokenExpiredException.class)
    public Object handlerCustom3(){
        return ResponseDataHelper.fail("token过期！！！");
    }

    /**
     * 参数校验不通过异常处理
     * @param manv
     * @return java.lang.Object
     **/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException manv){
        List<ObjectError> errors = manv.getBindingResult().getAllErrors();
        StringBuffer errorMsg=new StringBuffer();
        errors.stream().forEach(x -> errorMsg.append(x.getDefaultMessage()).append(";"));
        return errorMsg;
    }
}
