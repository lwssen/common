package com.sss.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 *
 **/
public class ErrorResult {
    /**
     * 错误消息map
     *
     * @param result
     * @return java.lang.Object
     **/
    public static Object resultErrorMsg(ErrorMsgEnum result) {
        Map<Object, Object> resultMap = new HashMap<>();
        resultMap.put("code", result.getCode());
        resultMap.put("msg", result.getMeg());
        return resultMap;
    }
}
