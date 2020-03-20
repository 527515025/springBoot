package cn.abel.user.exception;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.NotSupportedException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http调用错误
 *
 * @author ye
 * @date 2018/11/02 14:20
 */
public class RestExceptionMapper implements ExceptionMapper<WebApplicationException> {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(WebApplicationException.class);

    @Override
    public Response toResponse(WebApplicationException e) {
        if (e instanceof NotAllowedException) {
            logger.info("method错误。{}", e.getMessage());
            return null;
        }

        //415
        if (e instanceof NotSupportedException) {
            logger.info("content-type错误。{}", e.getMessage());
            return null;
        }

        if (e instanceof NotFoundException) {
            logger.info("url错误。{}", e.getMessage());
            return null;
        }

        return null;
    }

}
