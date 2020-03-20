package cn.abel.rest.shiro;

import java.util.List;
import java.util.Random;
import java.util.Set;

import cn.abel.exception.ServiceException;
import cn.abel.user.models.Permission;
import cn.abel.user.models.Role;
import cn.abel.user.models.User;
import cn.abel.user.service.PermissionService;
import cn.abel.user.service.RoleService;
import cn.abel.user.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @date 2018/08/07 23:48
 */
@Component
public class ShiroRealm extends AuthorizingRealm {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @Reference(check = false)
    private UserService userService;


    /**
     * 登录 认证回调函数,登录时调用
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();

        Random random = new Random();
        Long userId = random.nextLong();

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
        return new SimpleAccount(new ShiroUser(userId, username, username), "", getName());
    }

    /**
     * 授权 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        Set<String> roles = Sets.newHashSet();
        Set<String> permissions = Sets.newHashSet();

//        try {
            //TODO 此处需要获取当前用户的所有角色和权限，放入验证器
            //登录名为超级管理员账号
//
//                //获取用户所有角色
//                User user = userService.getById( shiroUser.getUserId());
//                if (CollectionUtils.isEmpty(user.getRoles())) {
//                    return authorizationInfo;
//                }
//
//                for (Role role : user.getRoles()) {
//                    //停用的角色不查询权限
//                    roles.add(role.getName());
//                    List<Permission> permissionList = permissionService.getByMap(role.getId());
//                    permissionList.forEach(p -> {
//                        permissions.add(p.getCode());
//                    });
//                }
//        } catch (ServiceException e) {
//            logger.error(e.getMessage());
//            return authorizationInfo;
//        }

        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);

        return authorizationInfo;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
        clearAllCache();
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
        clearAllCache();
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
        clearAllCache();
    }

    public void clearAllCachedAuthorizationInfo() {
        if (getAuthorizationCache() != null) {
            getAuthorizationCache().clear();
        }
    }

    public void clearAllCachedAuthenticationInfo() {
        if (getAuthenticationCache() != null) {
            getAuthenticationCache().clear();
        }

    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}
