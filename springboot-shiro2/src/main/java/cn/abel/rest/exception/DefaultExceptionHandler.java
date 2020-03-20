package cn.abel.rest.exception;

import cn.abel.exception.ServiceException;
import cn.abel.response.ResponseEntity;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

/**
 * 统一异常处理
 *
 */
@ControllerAdvice
public class DefaultExceptionHandler {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    private static final String ERROR_500 = "error/500";
    private static final String ERROR_403 = "error/403";
    private static final int ARG_ERROR_CODE = 400;


    /**
     * 参数验证异常。
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(
            HttpServletRequest request,
            HttpServletResponse response,
            MethodArgumentNotValidException ex) {
        String errMsg = null;
        if (ex.getBindingResult() != null && ex.getBindingResult().hasErrors()) {
            errMsg = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        }
        return ResponseEntity.error(ARG_ERROR_CODE, errMsg);
    }


    /**
     * 参数验证异常。
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolationException(
            HttpServletRequest request,
            HttpServletResponse response,
            ConstraintViolationException ex) {
        String errMsg = ((ConstraintViolationException) ex).getConstraintViolations()
                                                           .iterator().next().getMessage();
        return ResponseEntity.error(ARG_ERROR_CODE, errMsg);
    }


    /**
     * ServiceException异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler({ServiceException.class})
    @ResponseStatus(HttpStatus.OK)
    public String processServiceException(Exception exception, Model model) {
        model.addAttribute("exception", exception.getMessage());
        logger.error("rpc接口调用异常。{}", exception.getMessage());
        return ERROR_500;
    }

    /**
     * 没有权限 异常
     * <p/>
     * 后续根据不同的需求定制即可
     * 应用到所有@RequestMapping注解的方法，在其抛出UnauthorizedException异常时执行
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String processUnauthorizedException(NativeWebRequest request, Model model, UnauthorizedException e) {
        model.addAttribute("exception", e.getMessage());
        logger.error("权限异常。{}", e.getMessage());
        return ERROR_403;
    }

    /**
     * 运行时异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.OK)
    public String processException(RuntimeException exception, Model model) {
        model.addAttribute("exception", exception.getMessage());
        logger.error("程序异常", exception);
        return ERROR_500;
    }

    /**
     * Exception异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.OK)
    public String processException(Exception exception, Model model) {
        model.addAttribute("exception", exception.getMessage());
        logger.error("程序异常", exception);
        return ERROR_500;
        //logger.info("自定义异常处理-Exception");
        //ModelAndView m = new ModelAndView();
        //m.addObject("exception", exception.getMessage());
        //m.setViewName("error/500");
        //return m;
    }
}
