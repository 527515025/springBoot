package cn.abel.rest.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 注销filter
 *
 * @date 2019/02/26 13:32
 */
public class ShiroLogoutFilter extends LogoutFilter {

    private static final Logger logger = LoggerFactory.getLogger(ShiroLogoutFilter.class);

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        subject.logout();
        return true;
    }
}