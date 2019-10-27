package com.sss.common.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sss.common.annotation.AccessToken;
import com.sss.common.entity.SssMenu;
import com.sss.common.entity.SssUser;
import com.sss.common.entity.TokenInfo;
import com.sss.common.jwt.MyJwtUtil;
import com.sss.common.service.ISssMenuService;
import com.sss.common.service.ISssUserService;
import com.sss.common.shiro.CustomRealm;
import com.sss.common.shiro.service.JwtToken;
import com.sss.common.util.ResponseDataHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author sss
 * @since 2019-09-06
 */
@RestController
@RequestMapping("/user")
@AccessToken
public class SssUserController {
     @Autowired
    private ISssUserService sssUserService;
     @Autowired
     private ISssMenuService sssMenuService;
     @Autowired
     private RedisCacheManager redisCacheManager;

     @GetMapping
     public Object listUsers(){
         HashMap<Object, Object> map = new HashMap<>();
         List<SssUser> list = sssUserService.list();
         map.put("user",list);
         return map;
     }

     @PostMapping("/login")
     public Object login(SssUser sssUser){
         SssUser user = sssUserService.getOne(new QueryWrapper<SssUser>().eq("user_name", sssUser.getUserName()).eq("password", sssUser.getPassword()));
         // -------shiro登录验证start-----------
         TokenInfo tokenInfo = MyJwtUtil.createTokenInfo(user.getUserName(), user.getId().toString());
         UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getId().toString(), user.getUserName());
         Subject subject = SecurityUtils.getSubject();
         subject.login(usernamePasswordToken);
       //   -------shiro登录验证end-----------
         //把token存进redis
         //  userTokenManagerForRedis.saveUserByJwtTokenPlatform(activeUser, token);
         // stringRedisTemplate.opsForValue().set(TokenConts.TOKEN_KEY + user.getId(), token, 1L, TimeUnit.DAYS);
      //  clearCache();
         return ResponseDataHelper.success(tokenInfo);
     }

     @GetMapping("/menus")
     public Object listMenus(){
         List<SssMenu> list = sssMenuService.list();
         return  list;
     }




    //清空缓存
    public void clearCache() {
        Cache<Object, Object> cache  = redisCacheManager.getCache("com.sss.common.shiro.CustomRealm.authorizationCache");
        Set<Object> keys = cache.keys();
        //清除所有缓存
        cache.clear();
        //清除指定的缓存
        RedisManager redisManager =(RedisManager) redisCacheManager.getRedisManager();
        for (Object key : keys) {
            if (key.toString().equals("shiro:cache:com.sss.common.shiro.CustomRealm.authorizationCache:2")) {
                redisManager.del(key.toString().getBytes());
            }

        }

//        cache.clear();
//        RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
//        //AccountAuthorizationRealm为在项目中定义的realm类
//        CustomRealm shiroRealm = (CustomRealm) rsm.getRealms().iterator().next();
//        Subject subject = SecurityUtils.getSubject();
//        String realmName = subject.getPrincipals().getRealmNames().iterator().next();
//        Object principal = subject.getPrincipal();
//        SimplePrincipalCollection principals = new SimplePrincipalCollection(subject.getPrincipal(), realmName);
//        subject.runAs(principals);
//        //用realm删除principle
//        shiroRealm.getAuthorizationCache().remove(subject.getPrincipals());
//        shiroRealm.getAuthorizationCache().remove("shiro:cache:com.sss.common.shiro.CustomRealm.authorizationCache:2");
//        //切换身份也就是刷新了
//        subject.releaseRunAs();
    }


}

