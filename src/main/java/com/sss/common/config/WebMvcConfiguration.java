package com.sss.common.config;

import com.sss.common.interceptor.ApiInterceteptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author: sss
 * @date: 2019-09-06 15:53
 **/
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Value("${image.windows.path}")
    private   String imageWindowsSavePath;
    @Value("${image.config.path}")
    private   String imageConfigPath;

    /**
     * 不需要登录拦截的url
     */
    final String[] notLoginInterceptPaths = {"/not-login","/not-permission","/static/**","/user/login"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ApiInterceteptor()).addPathPatterns("/**").excludePathPatterns(notLoginInterceptPaths);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(imageConfigPath,"/**").addResourceLocations(imageWindowsSavePath);
    }

    /**
     *  解析CurrUser注解参数
     * @param
     * @return com.sss.common.config.CurrUserMethodArgumentResolver
     * @author sss
    **/
//    @Bean
//    public CurrUserMethodArgumentResolver currUserMethodArgumentResolver(){
//        return new CurrUserMethodArgumentResolver();
//    }


    /**
     * 解决跨域
     * @param registry
     * @return void
     * @author WYY-SSS
    **/
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//            registry.addMapping("/**")
//                    .allowedOrigins("*")
//                    .allowedMethods("*")
//                    .allowedHeaders("*");
//        }

}
