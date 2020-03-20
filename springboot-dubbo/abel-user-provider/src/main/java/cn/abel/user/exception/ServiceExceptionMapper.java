package cn.abel.user.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import cn.abel.exception.ServiceException;
import cn.abel.response.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ServiceExceptionMapper
 *
 * @author ye
 * @date 2018/11/02 14:20
 */
public class ServiceExceptionMapper implements ExceptionMapper<ServiceException> {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(ServiceException.class);

    @Override
    public Response toResponse(ServiceException exception) {
        ResponseEntity entity = ResponseEntity.error(exception.getErrorCode(), exception.getMessage());
        logger.error("{}，{}", exception.getErrorCode(), exception.getMessage());
        return Response.ok(entity, MediaType.APPLICATION_JSON).build();
    }
}
