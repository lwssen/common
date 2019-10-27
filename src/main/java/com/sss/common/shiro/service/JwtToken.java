package com.sss.common.shiro.service;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

/**
 * @author: wyy-sss
 * @date: 2019-10-26 10:35
 **/
public class JwtToken implements AuthenticationToken {

    private String token;




    public JwtToken() {
    }
    public JwtToken(String token) {
        this.token=token;
    }





    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }


}
