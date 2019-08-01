package com.ideasStudio.website.service.ex;

/**
 * @author 	覃远祠
 * 此异常是当发送邮件时选择发送邮件的类型不匹配时抛出的异常
 */
public class MessageTypeSwitchException extends ServiceException {

	public MessageTypeSwitchException() {
	}

	public MessageTypeSwitchException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public MessageTypeSwitchException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public MessageTypeSwitchException(String arg0) {
		super(arg0);
	}

	public MessageTypeSwitchException(Throwable arg0) {
		super(arg0);
	}
}
