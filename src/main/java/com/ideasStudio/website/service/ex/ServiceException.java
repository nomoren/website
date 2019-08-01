package com.ideasStudio.website.service.ex;

/**
 * 业务层所有逻辑异常的超类，在业务层发生的非检测异常都是改异常的子类
 * @author 覃远祠
 *
 */
public class ServiceException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ServiceException() {
		super();
	}

	public ServiceException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ServiceException(String arg0) {
		super(arg0);
	}

	public ServiceException(Throwable arg0) {
		super(arg0);
	}
	
}
