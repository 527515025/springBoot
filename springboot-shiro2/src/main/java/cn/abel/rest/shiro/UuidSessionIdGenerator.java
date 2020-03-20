package cn.abel.rest.shiro;

import java.io.Serializable;
import java.util.UUID;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

/**
 * 会话ID生成器
 *
 * @date 2018/08/08 11:04
 */
public class UuidSessionIdGenerator implements SessionIdGenerator {

    @Override
    public Serializable generateId(Session session) {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
}
