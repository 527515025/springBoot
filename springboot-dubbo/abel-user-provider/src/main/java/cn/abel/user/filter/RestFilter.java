package cn.abel.user.filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Priority;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志-复制并替代com.alibaba.dubbo.rpc.protocol.rest.support.LoggingFilter
 *
 * @author ye
 * @date 2018/11/02 15:20
 */
@Priority(900)
@PreMatching
public class RestFilter implements ContainerRequestFilter, ClientRequestFilter, ContainerResponseFilter,
        ClientResponseFilter {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(RestFilter.class);

    @Override
    public void filter(ClientRequestContext context) throws IOException {
        logHttpHeaders(context.getStringHeaders());
    }

    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
        logHttpHeaders(responseContext.getHeaders());
    }

    @Override
    public void filter(ContainerRequestContext context) throws IOException {
        logHttpHeaders(context.getHeaders());
    }

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {
        logHttpHeaders(responseContext.getStringHeaders());
    }

    protected void logHttpHeaders(MultivaluedMap<String, String> headers) {
        StringBuilder msg = new StringBuilder("The HTTP headers are: \n");
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            msg.append(entry.getKey()).append(": ");
            for (int i = 0; i < entry.getValue().size(); i++) {
                msg.append(entry.getValue().get(i));
                if (i < entry.getValue().size() - 1) {
                    msg.append(", ");
                }
            }
            msg.append("\n");
        }
        logger.info(msg.toString());
    }
}
