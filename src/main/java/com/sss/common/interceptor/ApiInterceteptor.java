package com.sss.common.interceptor;


import com.sss.common.annotation.AccessToken;
import com.sss.common.exception.CustomException;
import com.sss.common.jwt.MyJwtUtil;
import com.sss.common.util.MyDateTimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author wyy
 * @date: 2019-09-06 15:58
 **/
public class ApiInterceteptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("-------------------"+"进入拦截器------------");

        if (handler instanceof HandlerMethod) {
            HandlerMethod myHandlerMethod= (HandlerMethod) handler;
            AccessToken annotation = myHandlerMethod.getBeanType().getAnnotation(AccessToken.class) ;
            if (myHandlerMethod.getMethodAnnotation(AccessToken.class)!= null) {
                annotation=myHandlerMethod.getMethodAnnotation(AccessToken.class);
            }
            if (!annotation.isCheck()) {
             return  super.preHandle(request,response,handler);
            }
            String token = request.getHeader("authorization");
            if (StringUtils.isEmpty(token)) {
                throw new CustomException("header不存在token！！！");
            }
            Map<String, Object> resultMap = MyJwtUtil.verifyToken(token);
            long expire = (long) resultMap.get("expTime");
            if (MyDateTimeUtils.comparisonNow(expire)) {
                throw new CustomException(40005, "token已过期！！！");
            }
            String id = resultMap.get("userId").toString();
            request.setAttribute("uid", id);
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
