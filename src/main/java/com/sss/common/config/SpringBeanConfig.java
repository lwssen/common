package com.sss.common.config;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: sss
 * @date: 2019-09-06 15:50
 **/
@Configuration
public class SpringBeanConfig {


    /**
     * 解决跨域问题
     * @param
     * @return org.springframework.boot.web.servlet.FilterRegistrationBean
     * @author sss
    **/
    @Bean
    public FilterRegistrationBean CorsFilterRegistration() {
         FilterRegistrationBean registration = new FilterRegistrationBean(new CorsFilter());
        registration.addUrlPatterns("/**");
        registration.setOrder(0);
        return registration;
    }


}
