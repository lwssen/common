package com.sss.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: wyy-sss
 * @date: 2019-09-19 11:37
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenInfo {

    /**
     * token令牌
    **/
    private String accessToken;


    /**
     * 刷新token令牌
     **/
    private String refreshToken;

    /**
     * token令牌过期时间
     **/
    private Long expireTime;
}
