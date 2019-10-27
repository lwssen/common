package com.sss.common.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sss.common.entity.SssMenu;
import com.sss.common.entity.SssRoleMenu;
import com.sss.common.entity.SssUser;
import com.sss.common.entity.SssUserRole;
import com.sss.common.jwt.MyJwtUtil;
import com.sss.common.service.*;
import com.sss.common.shiro.service.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: sss
 * @date: 2019-09-06 15:36
 **/
@Slf4j
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private ISssUserService sssUserService;
    @Autowired
    private ISssRoleService sssRoleService;
    @Autowired
    private ISssRoleMenuService sssRoleMenuService;
    @Autowired
    private ISssUserRoleService sssUserRoleService;
    @Autowired
    private ISssMenuService sssMenuService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
      // return token instanceof JwtToken;
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("--------shiro权限授权开启-----------");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
       String userId = (String) principals.getPrimaryPrincipal();
        //自行优化查询
        SssUser user = sssUserService.getById(Integer.valueOf(userId));
        SssUserRole userRole = sssUserRoleService.getOne(new QueryWrapper<SssUserRole>().eq("user_id", user.getId()));
        List<SssRoleMenu> roleMenus = sssRoleMenuService.list(new QueryWrapper<SssRoleMenu>().eq("role_id", userRole.getRoleId()));
        Set<Integer> menuIds = roleMenus.stream().map(SssRoleMenu::getMenuId).collect(Collectors.toSet());
        List<SssMenu> menulist=sssMenuService.list(new QueryWrapper<SssMenu>().in("id",menuIds));
        menulist.forEach(k-> System.out.println("--------------查出来"+k.getPermission()));
        //把相应的权限字符串为该用户赋予权限
        for (SssMenu sssMenu : menulist) {
            String s = sssMenu.getPermission();
            String permission = s.substring(s.indexOf("[")+1, s.lastIndexOf("]"));
            System.out.println(permission);
            authorizationInfo.addStringPermission(permission);
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        log.info("--------shiro登录认证开启-----------");
       UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String userId = usernamePasswordToken.getUsername();
        String username = new String( usernamePasswordToken.getPassword());
        if (userId != null && username!=null ) {
            SssUser sssUser = sssUserService.getOne(new QueryWrapper<SssUser>().eq("id", userId).eq("user_name", username));
            if (sssUser!=null) {
                return new SimpleAuthenticationInfo(userId, username, getName());
            }
        }
              return null;
    }

}
