package com.ideasStudio.website.service.ex;

/**
 * 注册时若手机号已存在，抛出的异常
 * @author ME
 *
 */
public class PhoneConflictException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PhoneConflictException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PhoneConflictException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public PhoneConflictException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public PhoneConflictException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public PhoneConflictException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	

	
}
