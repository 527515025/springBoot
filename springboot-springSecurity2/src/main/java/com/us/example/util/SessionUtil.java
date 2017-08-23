package com.us.example.util;

import com.us.example.domain.SysUser;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by yangyibo on 8/23/17.
 */
public class SessionUtil {
    /**
     * 辨别用户是否已经登录
     *
     * @param request
     * @param sessionRegistry
     * @param loginedUser
     */
    public static void deleteSameUser(HttpServletRequest request, SessionRegistry sessionRegistry, SysUser loginedUser) {
        SecurityContext sc = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        List<SessionInformation> sessionsInfo;
        sessionsInfo = sessionRegistry.getAllSessions(sc.getAuthentication().getPrincipal(), true);
        String currentSessionId;
        if (null != sessionsInfo && sessionsInfo.size() == 0) {
            sessionRegistry.registerNewSession(request.getSession().getId(), sc.getAuthentication().getPrincipal());
            sessionsInfo = sessionRegistry.getAllSessions(sc.getAuthentication().getPrincipal(), false);
        }
        currentSessionId = sessionsInfo.get(0).getSessionId();
        List<Object> o = sessionRegistry.getAllPrincipals();
        for (Object principal : o) {
            if (principal instanceof SysUser && (loginedUser.getUsername().equals(((SysUser) principal).getUsername()))) {
                List<SessionInformation> oldSessionsInfo = sessionRegistry.getAllSessions(principal, false);
                if (null != oldSessionsInfo && oldSessionsInfo.size() > 0 && !oldSessionsInfo.get(0).getSessionId().equals(currentSessionId)) {
                    for (SessionInformation sessionInformation : sessionsInfo) {
                        //当前session失效
                        sessionInformation.expireNow();
                        sc.setAuthentication(null);
                        sessionRegistry.removeSessionInformation(currentSessionId);
                        throw new SessionAuthenticationException("当前用户已经在线，登录失败！！！");
                    }
                }
            }
        }
    }

    /**
     * 剔除前一个用户
     *
     * @param request
     * @param sessionRegistry
     * @param loginedUser
     */
    public static void dropPreviousUser(HttpServletRequest request, SessionRegistry sessionRegistry, SysUser loginedUser) {
        SecurityContext sc = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        List<SessionInformation> sessionsInfo;
        sessionsInfo = sessionRegistry.getAllSessions(sc.getAuthentication().getPrincipal(), true);
        String currentSessionId;
        currentSessionId = sessionsInfo.get(0).getSessionId();
        List<Object> o = sessionRegistry.getAllPrincipals();
        for (Object principal : o) {
            if (principal instanceof SysUser && (loginedUser.getUsername().equals(((SysUser) principal).getUsername()))) {
                List<SessionInformation> oldSessionsInfo = sessionRegistry.getAllSessions(principal, false);
                if (null != oldSessionsInfo && oldSessionsInfo.size() > 0 && !oldSessionsInfo.get(0).getSessionId().equals(currentSessionId)) {
                    for (SessionInformation sessionInformation : oldSessionsInfo) {
                        //发送消息通知
                        //当前session失效
                        sessionInformation.expireNow();
                    }
                }
            }
        }
    }


    /**
     * session 失效
     *
     * @param request
     * @param sessionRegistry
     */
    public static void expireSession(HttpServletRequest request, SysUser user, SessionRegistry sessionRegistry) {
        List<SessionInformation> sessionsInfo = null;
        if (null != user) {
            List<Object> o = sessionRegistry.getAllPrincipals();
            for (Object principal : o) {
                if (principal instanceof SysUser && (user.getUsername().equals(((SysUser) principal).getUsername()))) {
                    sessionsInfo = sessionRegistry.getAllSessions(principal, false);
                }
            }
        } else if (null != request) {
            SecurityContext sc = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
            if (null != sc.getAuthentication().getPrincipal()) {
                sessionsInfo = sessionRegistry.getAllSessions(sc.getAuthentication().getPrincipal(), false);
                sc.setAuthentication(null);
            }
        }
        if (null != sessionsInfo && sessionsInfo.size() > 0) {
            for (SessionInformation sessionInformation : sessionsInfo) {
                //当前session失效
                sessionInformation.expireNow();
                sessionRegistry.removeSessionInformation(sessionInformation.getSessionId());
            }
        }
    }
}
