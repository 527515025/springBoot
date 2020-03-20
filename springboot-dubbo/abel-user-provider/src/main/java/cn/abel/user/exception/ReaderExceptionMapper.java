package cn.abel.user.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import cn.abel.code.InfoCode;
import cn.abel.response.ResponseEntity;
import org.jboss.resteasy.spi.ReaderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * json转换错误
 *
 * @author ye
 * @date 2018/11/02 14:20
 */
public class ReaderExceptionMapper implements ExceptionMapper<ReaderException> {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(ReaderExceptionMapper.class);

    @Override
    public Response toResponse(ReaderException exception) {
        ResponseEntity entity = ResponseEntity.error(InfoCode.REQUEST_PARAM_ERROR);
        logger.info("{}", InfoCode.REQUEST_PARAM_ERROR.getMsg());
        return Response.ok(entity, MediaType.APPLICATION_JSON).build();
    }

}
