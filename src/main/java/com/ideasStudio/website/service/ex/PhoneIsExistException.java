package com.ideasStudio.website.service.ex;

//号码已存在，抛异常
public class PhoneIsExistException extends ServiceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PhoneIsExistException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PhoneIsExistException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public PhoneIsExistException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public PhoneIsExistException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public PhoneIsExistException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
}
