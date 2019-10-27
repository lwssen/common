package com.sss.common.enums;

/**
 * @author wyy
 */

public enum ErrorMsgEnum {
    /**
     * code 错误状态码
     * msg  返回消息
     */
    ACCOUNTNOTEXIST(20001, "用户名或密码错误"),
    UNAUTHORIZED(40004, "未授权，请联系管理员"),
    UNAUTHORIZEDFAILED(40005, "token过期或不存在！！！"),
    ERRORMSG(50000, "服务器内部繁忙，请稍后重试！！！"),
    UNLOGIN(40000, "未登录，请先登录！！！");

    private Integer code;

    private String meg;

    ErrorMsgEnum(Integer code, String meg) {
        this.code = code;
        this.meg = meg;
    }

    public Integer getCode() {
        return code;
    }


    public String getMeg() {
        return meg;
    }

}
