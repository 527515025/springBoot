package cn.abel.exception;

public class HttpExeption extends Exception {
	
	private static final long serialVersionUID = -4707210535385221192L;

	public HttpExeption() {
	}

	public HttpExeption(String msg) {
		super(msg);
	}

	public HttpExeption(String msg, Throwable e) {
		super(msg, e);
	}

	public HttpExeption(Throwable e) {
		super(e);
	}
}