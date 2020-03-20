package cn.abel.rest.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import cn.abel.rest.shiro.ext.QuartzSessionValidationScheduler;
import cn.abel.rest.shiro.credentials.RetryLimitHashedCredentialsMatcher;
import cn.abel.rest.shiro.filter.ShiroFormAuthenticationFilter;
import cn.abel.rest.shiro.filter.ShiroLogoutFilter;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;


/**
 * @date 2018/08/07 23:43
 */
@Configuration
public class ShiroConfig {
    public final static String SHIRO_REALM_NAME = "shiroRealm";

    /**
     * shiro的Web过滤器
     *
     * @param securityManager
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);

        //配置鉴权与登出的拦截器。
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("authc", formAuthenticationFilter());
        filterMap.put("logout", new ShiroLogoutFilter());
        bean.setFilters(filterMap);

        //配置各路径需要使用的拦截器。
        //anon表示anonymous。
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/font/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");

        filterChainDefinitionMap.put("/**", "authc");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }

    /**
     * 缓存管理器 使用Ehcache实现
     *
     * @return
     */
    @Bean(name = "shiroRedisCacheManager")
    //@ConditionalOnClass(name = {"org.apache.shiro.cache.ehcache.EhCacheManager"})
    @ConditionalOnMissingBean(name = "shiroRedisCacheManager")
    public CacheManager shiroRedisCacheManager() {
        //EhCacheManager cacheManager = new EhCacheManager();
        //cacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        //return cacheManager;
        ShiroRedisCacheManager cacheManager = new ShiroRedisCacheManager();
        return cacheManager;
    }

    /**
     * realm
     *
     * @return
     */
    @Bean
    public Realm realm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setName(SHIRO_REALM_NAME);
        shiroRealm.setCredentialsMatcher(credentialsMatcher());
        shiroRealm.setCachingEnabled(true);

        //不设置账号缓存，每次登录都实时调用用户服务的登录接口。
        shiroRealm.setAuthenticationCachingEnabled(false);

        //不设置角色权限缓存，每次登录都从权限服务实时拉取。
        shiroRealm.setAuthorizationCachingEnabled(false);

        return shiroRealm;
    }

    /**
     * 会话验证调度器
     *
     * @return
     */
    @Bean(name = "sessionValidationScheduler")
    @ConditionalOnClass(name = {"org.quartz.Scheduler"})
    @ConditionalOnMissingBean(SessionValidationScheduler.class)
    public SessionValidationScheduler sessionValidationScheduler(DefaultWebSessionManager sessionManager) {
        QuartzSessionValidationScheduler scheduler = new QuartzSessionValidationScheduler(sessionManager);
        //scheduler.setSessionValidationInterval(1800000);
        //scheduler.setSessionManager(sessionManager());
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionValidationInterval(1800000);
        sessionManager.setSessionValidationScheduler(scheduler);
        return scheduler;
    }

    /**
     * 会话DAO
     *
     * @return
     */
    @Bean
    public SessionDAO sessionDAO() {
        RedisSessionDao redisSessionDao = new RedisSessionDao();
        redisSessionDao.setActiveSessionsCacheName("shiro-activeSessionCache");
        redisSessionDao.setSessionIdGenerator(new UuidSessionIdGenerator());
        return redisSessionDao;
    }

    /**
     * 会话Cookie模板
     *
     * @return
     */
    @Bean
    public Cookie sessionIdCookie() {
        SimpleCookie cookie = new SimpleCookie("sid");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(-1);
        return cookie;
    }

    /**
     * rememberMe Cookie
     *
     * @return
     */
    @Bean
    public Cookie rememberMeCookie() {
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setHttpOnly(true);
        //30天
        cookie.setMaxAge(2592000);
        return cookie;
    }

    /**
     * 会话管理器
     *
     * @return
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        //SessionManager sessionManager = new SessionManager();
        HttpHeaderSessionManager sessionManager = new HttpHeaderSessionManager();
        sessionManager.setGlobalSessionTimeout(43200000);
        sessionManager.setDeleteInvalidSessions(true);
        //sessionManager.setSessionValidationSchedulerEnabled(true);
        //sessionManager.setSessionValidationScheduler(sessionValidationScheduler(sessionManager));
        //sessionManager.setSessionValidationScheduler(sessionValidationScheduler());
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setSessionIdCookieEnabled(false);
//        sessionManager.setSessionIdCookie(sessionIdCookie());
        //去掉URL中的JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    /**
     * 安全管理器
     *
     * @return
     */
    @Bean
    @DependsOn("redisTemplate")
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm());
        securityManager.setCacheManager(shiroRedisCacheManager());
        securityManager.setSessionManager(sessionManager());
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    /**
     * rememberMe管理器
     *
     * @return
     */
    @Bean
    public RememberMeManager rememberMeManager() {
        CookieRememberMeManager manager = new CookieRememberMeManager();
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度（128 256 512 位）
        manager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        manager.setCookie(rememberMeCookie());
        return manager;
    }

    /**
     * 凭证匹配器
     *
     * @return
     */
    @Bean(name = "credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() {
        //明文密码
        //CustomCredentialsMatcher matcher = new CustomCredentialsMatcher();
        //HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        RetryLimitHashedCredentialsMatcher matcher = new RetryLimitHashedCredentialsMatcher(shiroRedisCacheManager());
        //matcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        //matcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
        matcher.setStoredCredentialsHexEncoded(true);
        return matcher;
    }

    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean() {
        MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
        factoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        factoryBean.setArguments(securityManager());
        return factoryBean;
    }

    /**
     * Shiro生命周期处理器
     *
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    public ShiroFormAuthenticationFilter formAuthenticationFilter() {
        ShiroFormAuthenticationFilter filter = new ShiroFormAuthenticationFilter();
        filter.setUsernameParam("username");
        filter.setPasswordParam("password");
        filter.setRememberMeParam("rememberMe");
        filter.setLoginUrl("/login");
        filter.setSuccessUrl("/index");
        return filter;
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,
     * 并在必要时进行安全逻辑验证 * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)
     * 即可实现此功能
     *
     * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     * 需要aspectj支持
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
                new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}