package cn.abel.rest.shiro.credentials;

import java.util.concurrent.atomic.AtomicInteger;
import cn.abel.rest.shiro.ShiroUser;
import cn.abel.rest.shiro.ShiroProperty;

import cn.abel.user.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.Session;

/**
 * 登录验证
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
    private static final Logger logger = LoggerFactory.getLogger(RetryLimitHashedCredentialsMatcher.class);

    @Autowired
    private ShiroProperty shiroProperty;
    @Reference
    private UserService userService;

    private Cache<String, AtomicInteger> passwordRetryCache;

    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken originToken, AuthenticationInfo info) {
        ThirdPartySupportedToken token = (ThirdPartySupportedToken) originToken;
        ShiroUser shiroUser = (ShiroUser) info.getPrincipals().getPrimaryPrincipal();

        //优先使用第三方token进行登录验证。
        if (StringUtils.isNotBlank(token.getAccessToken())) {
            Session session = null;
//            try {
                //TODO 从用户服务中通过 token 获取 session
//                session = userService.getSessionByToken(token.getAccessToken());
//            } catch (ServiceException e) {
//                throw new ExpiredCredentialsException();
//            }
            if (session == null) {
                throw new ExpiredCredentialsException();
            }
            //TODO 从session 中获取用户相关信息，放入shiro 验证对象 shiroUser
//            shiroUser.setUserId(session.getUser().getProfileId());
//            shiroUser.setLoginName(session.getCurrentLoginName());
//            shiroUser.setNickName(session.getUser().getNickName());
            return true;
        }

        String username = token.getUsername();
        String password = String.valueOf(token.getPassword());

        //判断该账号登录重试次数是否超过上限。
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if (retryCount != null && retryCount.get() >= shiroProperty.getShiroRetryMax()) {
            throw new ExcessiveAttemptsException();
        }

        //TODO 没有token，则调用用户服务的登录接口重新登录 获取 session
//        Login login = new Login();
//        login.setIp(Constants.CLIENT_IP);
//        login.setLoginMode(2);
//        login.setLoginName(username);
//        login.setLoginType(3);
//        login.setPassword(password);
//        try {
//            Session session = userService.login(login);
//            if (session == null) {
//                logger.error("登录失败。username={}", username);
//                throw new IncorrectCredentialsException();
//            }
//            if (session.getUser().getDeleteFlag() != 1) {
//                logger.error("登录失败。用户已被锁定。username={}", username);
//                throw new LockedAccountException();
//            }

            //登录成功，清空登录次数缓存。
            if (retryCount != null) {
                passwordRetryCache.remove(username);
            }
            //TODO 将 session 中的用户id 信息放入 shiro 对shiroUser象中
//            shiroUser.setUserId(session.getUser().getProfileId());
//            return true;
//        } catch (ServiceException e) {
            //TODO 根据登录接口的异常信息判读 失败情况
//            if (e.getErrorCode() == InfoCode.USER_LOGIN_NOT_EXIST.getStatus()
//                    || e.getErrorCode() == InfoCode.REQUEST_PARAM_ERROR.getStatus()) {
//                logger.error("登录失败，用户不存在。username={}", username);
//                throw new UnknownAccountException();
//            } else if (e.getErrorCode() == InfoCode.PASSWORD_ERROR.getStatus()) {
//                //密码错误缓存登录重试次数缓存。
//                if (retryCount == null) {
//                    retryCount = new AtomicInteger(0);
//                }
//                retryCount.incrementAndGet();
//                passwordRetryCache.put(username, retryCount);
//
//                logger.error("登录失败，用户名或密码错误。username={}", username);
//                throw new IncorrectCredentialsException();
//            } else {
//                logger.error("登录失败，msg={}", e.getMessage());
//            }
//        }

        return false;
    }
}
