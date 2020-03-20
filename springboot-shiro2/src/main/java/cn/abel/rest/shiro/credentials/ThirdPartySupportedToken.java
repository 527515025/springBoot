package cn.abel.rest.shiro.credentials;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Component;

/**
 * 支持第三方登录的shiro token。
 */
public class ThirdPartySupportedToken extends UsernamePasswordToken {
    private String username;
    private char[] password;
    private boolean rememberMe = false;
    private String host;

    /**
     * 第三方token。
     */
    private String accessToken;

    public ThirdPartySupportedToken(
            String username,
            String password,
            String accessToken) {
        this.username = username;
        this.password = password != null ? password.toCharArray() : null;
        this.accessToken = accessToken;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public char[] getPassword() {
        return this.password;
    }

    @Override
    public void setPassword(char[] password) {
        this.password = password;
    }

    @Override
    public Object getPrincipal() {
        return this.getUsername();
    }

    @Override
    public Object getCredentials() {
        return this.getPassword();
    }

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public boolean isRememberMe() {
        return this.rememberMe;
    }

    @Override
    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public void clear() {
        super.clear();
        this.accessToken = null;
    }
}
