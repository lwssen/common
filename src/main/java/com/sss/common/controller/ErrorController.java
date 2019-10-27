package com.sss.common.controller;

import com.sss.common.enums.ErrorMsgEnum;
import com.sss.common.enums.ErrorResult;
import com.sss.common.util.ResponseDataHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: wyy-sss
 * @date: 2019-10-25 16:34
 **/
@RestController
@RequestMapping
public class ErrorController {

    @GetMapping("/not-login")
    public Object notLogin(){
        return ErrorResult.resultErrorMsg(ErrorMsgEnum.UNLOGIN);
    }


    @GetMapping("/not-permission")
    public Object notPermission(){
        return ErrorResult.resultErrorMsg(ErrorMsgEnum.UNAUTHORIZED);
    }


    @GetMapping("/token/error")
    public Object TokenError(){
        return  ErrorResult.resultErrorMsg(ErrorMsgEnum.UNAUTHORIZEDFAILED);
    }
}
