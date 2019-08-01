package com.ideasStudio.website.service.ex;
/**
 * 验证码过期
 * @author Administrator
 *
 */
public class CodeOutOfDateException extends ServiceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CodeOutOfDateException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CodeOutOfDateException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public CodeOutOfDateException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public CodeOutOfDateException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public CodeOutOfDateException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
