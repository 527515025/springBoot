package cn.abel.rest.shiro;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @date 2019/02/26 14:39
 */
@Configuration
public class ShiroProperty {

    @Value("${shiro.retryExpireTimeRedis}")
    private int shiroRetryExpireTimeRedis;

    @Value("${shiro.authorizationExpireTimeRedis}")
    private int shiroAuthorizationExpireTimeRedis;

    @Value("${shiro.retryMax}")
    private int shiroRetryMax;

    @Value("${shiro.sessionExpireTimeRedis}")
    private Integer shiroSessionExpireTimeRedis;

    public int getShiroRetryExpireTimeRedis() {
        return shiroRetryExpireTimeRedis;
    }

    public void setShiroRetryExpireTimeRedis(int shiroRetryExpireTimeRedis) {
        this.shiroRetryExpireTimeRedis = shiroRetryExpireTimeRedis;
    }

    public int getShiroAuthorizationExpireTimeRedis() {
        return shiroAuthorizationExpireTimeRedis;
    }

    public void setShiroAuthorizationExpireTimeRedis(int shiroAuthorizationExpireTimeRedis) {
        this.shiroAuthorizationExpireTimeRedis = shiroAuthorizationExpireTimeRedis;
    }

    public int getShiroRetryMax() {
        return shiroRetryMax;
    }

    public void setShiroRetryMax(int shiroRetryMax) {
        this.shiroRetryMax = shiroRetryMax;
    }

    public Integer getShiroSessionExpireTimeRedis() {
        return shiroSessionExpireTimeRedis;
    }

    public void setShiroSessionExpireTimeRedis(Integer shiroSessionExpireTimeRedis) {
        this.shiroSessionExpireTimeRedis = shiroSessionExpireTimeRedis;
    }
}
