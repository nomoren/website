package com.ideasStudio.website.service.ex;

/**
 * 请求参数异常
 * @author Administrator
 *
 */
public class RequestArgumentException extends ServiceException{

	private static final long serialVersionUID = 1L;

	public RequestArgumentException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RequestArgumentException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public RequestArgumentException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public RequestArgumentException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public RequestArgumentException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
}
