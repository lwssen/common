package com.sss.common.shiro.filter;


import com.sss.common.jwt.MyJwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: sss  extends AccessControlFilter
 * @date: 2019-09-06 08:36
 **/
@Slf4j
public class MyShiroFilter extends FormAuthenticationFilter {

    /**
     *返回false
     * @param servletRequest
     * @param servletResponse
     * @param o
     * @return 返回结果是false的时候才会执行下面的onAccessDenied方法
     * @throws Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) {
        log.info("----------JWT验证开始-------------");
       // StringRedisTemplate stringRedisTemplate = BeanUtils.getBean(StringRedisTemplate.class);

        HttpServletRequest request= (HttpServletRequest) servletRequest;
        System.out.println("--------------------请求路径为"+request.getRequestURI());
        System.out.println("--------------------请求路径为2"+request.getRequestURL());

        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())){
            return true;
        }
        return false;
    }

    /**
     * 从请求头获取token并验证，验证通过后交给realm进行登录
     * @param servletRequest
     * @param servletResponse
     * @return 返回结果为true表明登录通过
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        log.info("-----------验证token是否可用开启---------------");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        System.out.println("--------------------请求路径为"+request.getRequestURI());
        String token = request.getHeader("Authorization");
        //验证token是否有效
        Map<String, Object> map =new HashMap<>();
        try {
           map = MyJwtUtil.verifyToken(token);
        }catch (Exception ex){
            //throw new RuntimeException("token过期或不存在");
            redirectToLogin(servletRequest,servletResponse);
            return false;
        }
       String userName = (String) map.get("userName");
       String userId = (String) map.get("userId");
       // 委托realm进行登录认证
        if(token !=null) {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userId, userName);
            Subject subject = getSubject(servletRequest, servletResponse);
            subject.login(usernamePasswordToken);
            return true;
        }
       saveRequestAndRedirectToLogin(servletRequest, servletResponse);
        return false;
//       return true;
    }

    /**
     * 重定向到token错误接口
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        log.info("-------------token过期或不存在，重定向到token错误接口返回消息给前端------------");
        WebUtils.issueRedirect(request, response, "/token/error");

    }

    /**
     * 重定向到未登录接口
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        log.info("-------------未登录，重定向到未登录接口返回消息给前端------------");
        WebUtils.issueRedirect(request, response, "/not-login");
    }
}
