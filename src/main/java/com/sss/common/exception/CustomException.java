package com.sss.common.exception;

/**
 * @author: wyy-sss
 * @date: 2019-09-10 08:52
 **/
public class CustomException extends RuntimeException {
    private Integer code;


    public CustomException( Integer code,String message) {
        super(message);
        this.code = code;
    }

    public CustomException(String message) {
        super(message);
    }

    public Integer getCode() {
        return code;
    }
}
