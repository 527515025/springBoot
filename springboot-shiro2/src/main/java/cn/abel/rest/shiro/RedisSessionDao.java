package cn.abel.rest.shiro;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import cn.abel.rest.utils.ServletKit;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 重写SessionDAO，实现session的CRUD功能
 *
 * @date 2019/02/22 14:06
 */
public class RedisSessionDao extends EnterpriseCacheSessionDAO {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(RedisSessionDao.class);

    private Cache cache() {
        Cache<Object, Object> cache = getCacheManager().getCache(this.getClass().getName());
        return cache;
    }

    /**
     * 创建session，保存到数据库
     *
     * @param session
     * @return
     */
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session);
        cache().put(sessionId.toString(), session);
        return sessionId;
    }

    /**
     * 获取session
     *
     * @param sessionId
     * @return
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = null;
        HttpServletRequest request = ServletKit.getRequest();
        if (request != null) {
            String uri = request.getServletPath();
            if (ServletKit.isStaticFile(uri)) {
                return null;
            }
            session = (Session) request.getAttribute("session_" + sessionId);
        }
        if (session == null) {
            session = super.doReadSession(sessionId);
        }
        if (session == null) {
            session = (Session) cache().get(sessionId.toString());
        }
        return session;
    }

    /**
     * 更新session的最后一次访问时间
     *
     * @param session
     */
    @Override
    protected void doUpdate(Session session) {
        HttpServletRequest request = ServletKit.getRequest();
        if (request != null) {
            String uri = request.getServletPath();
            if (ServletKit.isStaticFile(uri)) {
                return;
            }
        }

        super.doUpdate(session);
        cache().put(session.getId().toString(), session);

        logger.debug("{}", session.getAttribute("shiroUserId"));
    }

    /**
     * 删除session
     *
     * @param session
     */
    @Override
    protected void doDelete(Session session) {
        super.doDelete(session);
        cache().remove(session.getId().toString());
    }
}