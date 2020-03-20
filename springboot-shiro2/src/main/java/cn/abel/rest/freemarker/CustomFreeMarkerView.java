package cn.abel.rest.freemarker;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

/**
 * freemarker全局变量
 *
 * @date 2018/08/12 17:06
 */
public class CustomFreeMarkerView extends FreeMarkerView {

    private static final String CONTEXT_PATH = "base";

    @Override
    protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int port = request.getServerPort();
        String path = request.getContextPath();
        String basePath = scheme + "://" + serverName + ":" + port + path;
        model.put(CONTEXT_PATH, basePath);

        super.exposeHelpers(model, request);
    }
}