package com.sss.common.shiro;

import com.sss.common.config.ShiroLifecycleBeanPostProcessorConfig;
import com.sss.common.entity.SssMenu;
import com.sss.common.service.ISssMenuService;
import com.sss.common.shiro.filter.JwtFilter;
import com.sss.common.shiro.filter.MyShiroFilter;
import com.sss.common.shiro.service.CustomSessionIdGenerator;
import com.sss.common.util.MySpringBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.crazycake.shiro.serializer.ObjectSerializer;
import org.crazycake.shiro.serializer.StringSerializer;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: sss
 * @date: 2019-09-06 15:34
 **/
@Configuration
@AutoConfigureAfter(ShiroLifecycleBeanPostProcessorConfig.class)
@Slf4j
public class ShiroConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;
    @Autowired
    private ISssMenuService sssMenuService;


    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) throws Exception {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<>(1);
     //  filterMap.put("jwt", new JwtFilter());
     //   filterMap.put("jwt", statelessAuthcFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        // 未登录访问接口时返回未登录消息
        shiroFilterFactoryBean.setLoginUrl("/not-login");
        // 登录成功后要跳转的链接
        //shiroFilterFactoryBean.setSuccessUrl("/login/home");
        //访问未授权接口时返回未授权消息;
        shiroFilterFactoryBean.setUnauthorizedUrl("/not-permission");
//         ----------------动态态配置权限----------------------------------
        // 权限控制map.从数据库获取
       Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/user/login", "anon");
        filterChainDefinitionMap.put("/not-login", "anon");
        filterChainDefinitionMap.put("/not-permission", "anon");
        filterChainDefinitionMap.put("/token/error", "anon");
        filterChainDefinitionMap.put("/static/templates/**", "anon");
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->


        List<SssMenu> list = sssMenuService.list(null);
        for (SssMenu sssMenu : list) {
            filterChainDefinitionMap.put(sssMenu.getUrl(),sssMenu.getPermission());
        }
       // filterChainDefinitionMap.put("/**", "jwt");
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        log.info("Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;



 //----------------静态配置权限----------------------------------
//        //自定义拦截器
//        // 添加自己的过滤器并且取名为jwt
//        Map<String, Filter> filterMap = new HashMap<>();
//        filterMap.put("jwt", new MyShiroFilter());
//        shiroFilterFactoryBean.setFilters(filterMap);
        // 配置不会被拦截的链接 顺序判断   拦截器. anon 代表不拦截  authc代表拦截
//        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
//       // filterChainDefinitionMap.put("/**", "anon");
//        filterChainDefinitionMap.put("/user/login", "anon");
//        filterChainDefinitionMap.put("/user/unlogin", "anon");
//        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
//        filterChainDefinitionMap.put("/logout", "logout");
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
//        filterChainDefinitionMap.put("/static/templates/**", "anon");
//       filterChainDefinitionMap.put("/**", "authc");
     //   filterChainDefinitionMap.put("/**", "anon");
       // filterChainDefinitionMap.put("/**", "jwt");
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//        return shiroFilterFactoryBean;
    }

    /**
     * 凭证匹配器  目前没有用到
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * ）
     * @return
     */
    //    @Bean
    //    public HashedCredentialsMatcher hashedCredentialsMatcher(){
    //        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
    //        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
    //        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
    //        return hashedCredentialsMatcher;
    //    }

    /**
     * 自定义的realm
     *
     * @return com.hciot.mes.shiro.CustomRealm
     **/
    @Bean
    public CustomRealm realm() {
        CustomRealm realm = new CustomRealm();
        //是否开启权限缓存 默认true
       // realm.setCachingEnabled(false);
        return realm;
    }

    /**
     * 配置securityManager 安全管理器
     *
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager webSecurityManager = new DefaultWebSecurityManager();
//        // 注意这里必须配置securityManager
//        SecurityUtils.setSecurityManager(webSecurityManager);
//        // 设置Subject工厂
//        webSecurityManager.setSubjectFactory(subjectFactory());
        //配置认证器
        webSecurityManager.setRealm(realm());
        // 关闭Shiro自带的session
       // webSecurityManager.setSubjectDAO(subjectDAO());
        // 禁用Session作为存储策略的实现。
//        DefaultSubjectDAO defaultSubjectDAO = (DefaultSubjectDAO)webSecurityManager .getSubjectDAO();
//        DefaultSessionStorageEvaluator defaultSessionStorageEvaluatord = (DefaultSessionStorageEvaluator) defaultSubjectDAO
//                .getSessionStorageEvaluator();
//        defaultSessionStorageEvaluatord.setSessionStorageEnabled(false);
        webSecurityManager.setSessionManager(sessionManager());
        webSecurityManager.setCacheManager(redisCacheManager());
        return webSecurityManager;
    }


    /**
     * shiro session的管理
     */
    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(-1L);
        // 禁用掉会话调度器
          sessionManager.setSessionValidationSchedulerEnabled(false);
        return sessionManager;
    }


//    @Bean
//    public DefaultSessionStorageEvaluator sessionStorageEnabled() {
//        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
//        sessionStorageEvaluator.setSessionStorageEnabled(false);
//        return sessionStorageEvaluator;
//    }
//
//
//    @Bean
//    public DefaultSubjectDAO subjectDAO() {
//        DefaultSubjectDAO defaultSubjectDAO = new DefaultSubjectDAO();
//        // 解决报错，组装默认的subjectDAO
//        defaultSubjectDAO.setSessionStorageEvaluator(sessionStorageEnabled());
//
//        return defaultSubjectDAO;
//    }


//    /**
//     * 自定义的无状态（无session）Subject工厂
//     *
//     * @return
//     */
//    @Bean
//    public StatelessDefaultSubjectFactory subjectFactory() {
//        return new StatelessDefaultSubjectFactory();
//    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     */
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager() {

        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        //设置权限缓存的时间
        //redisCacheManager.setExpire(1800);
        //指定存入Redis的主键
        //  redisCacheManager.setPrincipalIdFieldName("id");
        return redisCacheManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
//    @Bean
//    public RedisSessionDAO redisSessionDAO() {
//        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//        redisSessionDAO.setRedisManager(redisManager());
//        //设置seesion的时间
//        redisSessionDAO.setExpire(1800);
//        //自定义sessionId生成
//      // redisSessionDAO.setSessionIdGenerator(new CustomSessionIdGenerator());
//        return redisSessionDAO;
//    }


    /**
     * Session Manager
     * 使用的是shiro-redis开源插件
     */
//    @Bean
//    public SessionManager sessionManager() {
//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        sessionManager.setSessionDAO(redisSessionDAO());
//        sessionManager.setGlobalSessionTimeout(-1);
//        return sessionManager;
//    }









    // 下面三个方法对 开启注解权限
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
    ) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager());
        return advisor;
    }


//    @Bean(name = "lifecycleBeanPostProcessor")
//    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
//        return new LifecycleBeanPostProcessor();
//    }

//    @Bean
//    @DependsOn({"lifecycleBeanPostProcessor"})
//    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//        advisorAutoProxyCreator.setProxyTargetClass(true);
//        return advisorAutoProxyCreator;
//    }
}
