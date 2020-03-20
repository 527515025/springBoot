package cn.abel.exception;

import cn.abel.code.*;

/**
 * 服务异常类
 *
 */
@SuppressWarnings("serial")
public class ServiceException extends Exception {

    //默认的错误码
    private int errorCode = 500;

    private String errorCodes;

    public ServiceException() {
    }

    public ServiceException(Throwable e) {
        super(e);
    }

    public ServiceException(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorCodes() {
        return errorCodes;
    }

    public ServiceException(InfoCode infoCode){
        super(infoCode.getMsg());
        this.errorCode = infoCode.getStatus();
    }

    public ServiceException(int errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    public ServiceException(String errorCode, String msg) {
        super(msg);
        this.errorCodes = errorCode;
    }

    public ServiceException(int errorCode, String msg, Throwable e) {
        super(msg, e);
        this.errorCode = errorCode;
    }

    public ServiceException(int errorCode, Throwable e) {
        super(e);
        this.errorCode = errorCode;
    }
}
