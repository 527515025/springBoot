package cn.abel.rest.constants;


public interface Constants {

    /**
     * 第三方登录的token。
     */
    String THIRD_PARTY_ACCESS_TOKEN_NAME = "accessToken";

    /** 用户注册和绑定时的ip */
    String CLIENT_IP = "127.0.0.1";
    /** redis缓存 key前缀 */
    String REDIS_KEY_PRE = "abel-user";
    /** 超时时间（天） */
    long REDIS_TIMEOUT = 10L;


}
