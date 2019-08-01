package com.ideasStudio.website.util.ex;

/**
 * 
 * @author 	覃远祠
 *	这个类是在发送邮件时失败抛出的异常
 */
public class SendEmailFailException extends UtilException {

	private static final long serialVersionUID = 1L;

	public SendEmailFailException() {
	}

	public SendEmailFailException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public SendEmailFailException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public SendEmailFailException(String arg0) {
		super(arg0);
	}

	public SendEmailFailException(Throwable arg0) {
		super(arg0);
	}
}
