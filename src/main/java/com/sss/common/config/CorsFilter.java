package com.sss.common.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 **/
@Slf4j
public class CorsFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String origin = request.getHeader("Origin");
        log.info("-----------------origin等于:{}", origin);
        String token = request.getHeader("Authorization");
        log.info("-----------------aa等于:{}", token);
        if (!StringUtils.isEmpty(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
        }
        String headers = request.getHeader("Access-Control-Allow-Headers");
        log.info("----------------------headers等于:{}", headers);
        if (!StringUtils.isEmpty(headers)) {
            response.setHeader("Access-Control-Allow-Headers", headers);
        } else {
            response.setHeader("Access-Control-Allow-Headers",
                    "authentication,Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, " +
                            "Cache-Control, Expires, Content-Type, X-E4M-With,userId,token,authorization");
        }
        response.setHeader("Access-Control-Allow-Credentials", "true");
        // response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE,PATCH,OPTIONS,HEAD");
        response.setHeader("Access-Control-Max-Age", "3600");
        // 如果是OPTIONS请求则结束
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            log.info("----------------------OPTIONS等于:{}", HttpStatus.NO_CONTENT.value());
            response.setStatus(HttpStatus.NO_CONTENT.value());
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
