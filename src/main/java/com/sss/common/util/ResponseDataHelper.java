package com.sss.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wyy-sss
 * @date 2019-09-17 15:20
 **/
public class ResponseDataHelper {

    public static final Integer SUCCESS_CODE=200;

    public static final Integer FAIL_CODE=-1;

    public static final String SUCCESS_MSG="ok";

    public static final String FAIL_MSG="error";

    public static Map<String,Object> getAllBaseMap(String msg,Integer code ,Object data){
        HashMap<String, Object> map = new HashMap<>(16);
        map.put("msg",msg);
        map.put("code",code);
        map.put("data",data);
        return map;
    }

    public static Map<String, Object> success(Object data){
      return   getAllBaseMap(SUCCESS_MSG,SUCCESS_CODE,data);
    }

    public static Map<String, Object> success(){
      return   getAllBaseMap(SUCCESS_MSG,SUCCESS_CODE,new HashMap(16));
    }

    public static Map<String, Object> fail(){
        return   getAllBaseMap(FAIL_MSG,FAIL_CODE,new HashMap(16));
    }

    public static Map<String, Object> fail(Object data){
        return   getAllBaseMap(FAIL_MSG,FAIL_CODE,data);
    }

    public static Map<String, Object> fail(Integer code,String msg){
        return   getAllBaseMap(msg,code,new HashMap(16));
    }

    public static Map<String, Object> fail(String msg){
        return   getAllBaseMap(msg,FAIL_CODE,new HashMap(16));
    }
}
