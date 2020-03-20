package cn.abel.exception;


import cn.abel.code.InfoCode;

/**
 * 接口服务抛出的异常
 *
 */
public class AppRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 2963760410777899525L;
	private InfoCode infoCode;

	public AppRuntimeException() {
		infoCode = InfoCode.SERVICE_UNAVAILABLE;
		
	}

	public AppRuntimeException(InfoCode infoCode) {
		this.infoCode = infoCode;
	}
	
	public AppRuntimeException(InfoCode infoCode, String message) {
	    super(message);
	    this.infoCode = infoCode;
	}

	public InfoCode getInfoCode() {
		return infoCode;
	}

}
