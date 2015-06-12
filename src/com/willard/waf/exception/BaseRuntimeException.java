package com.willard.waf.exception;

public class BaseRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public BaseRuntimeException() {
		super();
	}
	
	public BaseRuntimeException(String msg) {
		super(msg);
	}
	
	public BaseRuntimeException(Throwable ex) {
		super(ex);
	}
	
	public BaseRuntimeException(String msg,Throwable ex) {
		super(msg,ex);
	}

}
