package com.ideasStudio.website.util.ex;

/**
 * 
 * @author 覃远祠
 *	这个类是所有工具类发生的非检测异常的超类
 */
public class UtilException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UtilException() {
		super();
	}

	public UtilException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public UtilException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public UtilException(String arg0) {
		super(arg0);
	}

	public UtilException(Throwable arg0) {
		super(arg0);
	}
	
	

}
