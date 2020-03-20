package cn.abel.user.exception;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;

import cn.abel.code.InfoCode;
import cn.abel.response.ResponseEntity;
import com.alibaba.dubbo.rpc.protocol.rest.RpcExceptionMapper;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

/**
 * 参数校验异常
 *
 * @author ye
 * @date 2018/11/04 16:53
 */
public class ValidationExceptionMapper extends RpcExceptionMapper {

    @Override
    protected Response handleConstraintViolationException(ConstraintViolationException cve) {
        //ViolationReport report = new ViolationReport();
        String msg = "";
        for (ConstraintViolation cv : cve.getConstraintViolations()) {
            //report.addConstraintViolation(new RestConstraintViolation(cv.getPropertyPath().toString(),
            //        cv.getMessage(), cv.getInvalidValue() == null ? "null" : cv.getInvalidValue().toString()));
            msg = cv.getPropertyPath().toString() + " " + cv.getMessage();
        }

        ResponseEntity entity = ResponseEntity.error(InfoCode.REQUEST_PARAM_ERROR.getStatus(),
                InfoCode.REQUEST_PARAM_ERROR.getMsg() + "(" + msg + ")");

        // 采用json输出代替xml输出
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(entity).type(ContentType.APPLICATION_JSON_UTF_8).build();
    }
}
