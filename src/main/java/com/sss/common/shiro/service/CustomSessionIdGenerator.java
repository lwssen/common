package com.sss.common.shiro.service;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;
import java.util.UUID;

/**
 * 自定义seesionId生成
 * @author: wyy-sss
 * @date: 2019-10-25 13:37
 **/
public class CustomSessionIdGenerator implements SessionIdGenerator {

    private final String SHIRO_SESSION_ID="-user-shiro-session";

    @Override
    public Serializable generateId(Session session) {
       // return SHIRO_SESSION_ID;
        return UUID.randomUUID().toString().replace("-","");
    }
}
