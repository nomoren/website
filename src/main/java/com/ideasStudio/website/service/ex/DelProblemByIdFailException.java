package com.ideasStudio.website.service.ex;

/**
 * 根据模块的id删除模块时抛出的异常
 * @author 覃远祠
 *
 */
public class DelProblemByIdFailException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public DelProblemByIdFailException() {
	}

	public DelProblemByIdFailException(String arg0, Throwable arg1,
			boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public DelProblemByIdFailException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public DelProblemByIdFailException(String arg0) {
		super(arg0);
	}

	public DelProblemByIdFailException(Throwable arg0) {
		super(arg0);
	}

}
