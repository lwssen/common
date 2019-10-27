package com.sss.common.mybatisplus.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * @author: wyy-sss
 * @date: 2019-09-17 10:20
 **/
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Object createTime = getFieldValByName("createTime", metaObject);

        if (createTime==null) {
            setFieldValByName("createTime",new Timestamp(System.currentTimeMillis()),metaObject);
            setFieldValByName("updateTime",new Timestamp(System.currentTimeMillis()),metaObject);
        }
        log.info("createTime等于:{}",createTime);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object updateTime = getFieldValByName("updateTime", metaObject);

        if (updateTime==null) {
            setFieldValByName("updateTime",new Timestamp(System.currentTimeMillis()),metaObject);
        }
        setFieldValByName("updateTime",new Timestamp(System.currentTimeMillis()),metaObject);
        log.info("updateTime等于:{}",updateTime);
    }
}
