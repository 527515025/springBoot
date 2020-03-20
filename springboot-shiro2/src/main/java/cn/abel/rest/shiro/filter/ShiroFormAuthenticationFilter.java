package cn.abel.rest.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import cn.abel.code.InfoCode;
import cn.abel.response.ResponseEntity;
import cn.abel.rest.constants.Constants;
import cn.abel.rest.shiro.ShiroUser;
import cn.abel.rest.shiro.credentials.ThirdPartySupportedToken;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

/**
 * 登录filter
 *
 */
public class ShiroFormAuthenticationFilter extends FormAuthenticationFilter {
    private static final Logger logger = LoggerFactory.getLogger(ShiroFormAuthenticationFilter.class);

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //登录请求用户名和accessToken不能同时为空。
        if (isLoginRequest(request, response)) {
            if (StringUtils.isBlank(getUsername(request))
                    && StringUtils.isBlank(request.getParameter(Constants.THIRD_PARTY_ACCESS_TOKEN_NAME))) {
                return responseDirectly((HttpServletResponse) response,
                        ResponseEntity.error(InfoCode.REQUEST_PARAM_ERROR));
            }
        }
        return super.onPreHandle(request, response, mappedValue);
    }

    /**
     * 当鉴权失败的时候执行的方法。
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            }
            //登录仅限HttpPost方式，其他任何方式是不合法的。
            return responseDirectly((HttpServletResponse) response, ResponseEntity.error(InfoCode.LOGIN_TYPE_ERROR));
        }
        //调用非登录方法时认证失败的情况。
        return responseDirectly((HttpServletResponse) response, ResponseEntity.error(InfoCode.INVALID_TOKEN));
    }

    /**
     * 登录验证失败后执行的方法。
     *
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(
            AuthenticationToken token,
            AuthenticationException e,
            ServletRequest request,
            ServletResponse response) {
        HttpServletResponse resp = (HttpServletResponse) response;
        if (e instanceof ExcessiveAttemptsException) {
            return responseDirectly(resp, ResponseEntity.error(InfoCode.PASSWORD_ERROR_MORE_THAN));
        } else if (e instanceof UnknownAccountException) {
            return responseDirectly(resp, ResponseEntity.error(InfoCode.PASSWORD_ERROR));
        } else if (e instanceof IncorrectCredentialsException) {
            return responseDirectly(resp, ResponseEntity.error(InfoCode.PASSWORD_ERROR));
        } else if (e instanceof LockedAccountException) {
            return responseDirectly(resp, ResponseEntity.error(InfoCode.USER_PROFILE_LOCK));
        } else if (e instanceof ExpiredCredentialsException) {
            return responseDirectly(resp, ResponseEntity.error(InfoCode.INVALID_LOGIN));
        } else {
            return responseDirectly(resp, ResponseEntity.error(InfoCode.SERVICE_UNAVAILABLE));
        }
    }

    /**
     * 登录成功后执行的方法。
     *
     * @param token
     * @param subject
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onLoginSuccess(
            AuthenticationToken token,
            Subject subject,
            ServletRequest request,
            ServletResponse response) throws Exception {
        ShiroUser user = (ShiroUser) subject.getPrincipal();
        user.setToken(subject.getSession().getId().toString());
        return true;
    }

    /**
     * 创建包含第三方accessToken的shiro token。
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        String accessToken = request.getParameter(Constants.THIRD_PARTY_ACCESS_TOKEN_NAME);
        //shiro后续流程可能会用到username，所以如果用accessToken登录时赋值username为它的值。
        if (StringUtils.isBlank(username)) {
            username = accessToken;
        }
        return new ThirdPartySupportedToken(username, password, accessToken);
    }

    /**
     * 直接构造HttpResponse，不再执行后续的所有方法。
     *
     * @param response
     * @param entity
     * @return
     */
    private boolean responseDirectly(HttpServletResponse response, ResponseEntity entity) {
        response.reset();
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        //设置跨域信息。
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS, HEAD");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");
        try {
            response.getWriter().write(JSON.toJSONString(entity));
        } catch (IOException e) {
            logger.error("ResponseError ex:{}", ExceptionUtils.getStackTrace(e));
        }
        return false;
    }
}
