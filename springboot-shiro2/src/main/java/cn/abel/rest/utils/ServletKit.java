package cn.abel.rest.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

/**
 * Servlet工具类
 *
 * @date 2019/02/22 14:07
 */
public class ServletKit {

    /**
     * 在任意位置获取Request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 判断请求uri是否是静态资源方法
     *
     * @param uri
     * @return
     */
    public static boolean isStaticFile(String uri) {
        ResourceUrlProvider resourceUrlProvider = SpringContextKit.getBean(ResourceUrlProvider.class);
        String staticUri = resourceUrlProvider.getForLookupPath(uri);
        return staticUri != null;
    }
}