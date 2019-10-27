package com.sss.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *
 * @author sss
 */
@Component("BeanUtils")
public class MySpringBeanUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    /**
     * 获取applicationContext
     *
     * @return ApplicationContext 实例
     */
    private static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (MySpringBeanUtils.applicationContext == null) {
            MySpringBeanUtils.applicationContext = applicationContext;
        }
    }

    /**
     * 通过 Bean 名获取 Bean
     *
     * @return Bean 对象
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过类名获取 Bean
     *
     * @return Bean 对象
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);

    }

    /**
     * 通过 Bean 名及类名获取 Bean
     *
     * @return Bean 对象
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}
