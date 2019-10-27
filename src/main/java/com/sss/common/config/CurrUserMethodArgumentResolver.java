package com.sss.common.config;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 **/
public class CurrUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        if (methodParameter.hasParameterAnnotation(CurrUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        CurrUser currentUserAnnotation = methodParameter.getParameterAnnotation(CurrUser.class);
        Object currUser2 = nativeWebRequest.getAttribute("currUser", NativeWebRequest.SCOPE_SESSION);
        System.out.println(currUser2);
        Object currUser= nativeWebRequest.getAttribute("currUser", RequestAttributes.SCOPE_REQUEST);
        System.out.println(currUser);
        return nativeWebRequest.getAttribute("currUser", NativeWebRequest.SCOPE_SESSION);
    }
}
