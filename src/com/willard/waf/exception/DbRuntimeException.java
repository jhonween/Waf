package com.willard.waf.exception;

public class DbRuntimeException extends BaseRuntimeException {
	private static final long serialVersionUID = 1L;
	
	public DbRuntimeException() {}
	
	
	public DbRuntimeException(String msg) {
		super(msg);
	}
	
	public DbRuntimeException(Throwable ex) {
		super(ex);
	}
	
	public DbRuntimeException(String msg,Throwable ex) {
		super(msg,ex);
	}
	
}
