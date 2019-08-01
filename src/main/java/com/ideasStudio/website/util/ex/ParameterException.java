package com.ideasStudio.website.util.ex;

/**
 * ZIP压缩文件时发生的参数错误异常
 * @author Administrator
 *
 */
public class ParameterException extends UtilException {

	public ParameterException() {
	}

	public ParameterException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ParameterException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ParameterException(String arg0) {
		super(arg0);
	}

	public ParameterException(Throwable arg0) {
		super(arg0);
	}
}
