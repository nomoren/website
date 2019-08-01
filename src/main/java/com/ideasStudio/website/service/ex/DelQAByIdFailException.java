package com.ideasStudio.website.service.ex;

/**
 * 根据问题的id删除问题时抛出的异常
 * @author 覃远祠
 *
 */
public class DelQAByIdFailException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public DelQAByIdFailException() {
	}

	public DelQAByIdFailException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public DelQAByIdFailException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public DelQAByIdFailException(String arg0) {
		super(arg0);
	}

	public DelQAByIdFailException(Throwable arg0) {
		super(arg0);
	}

}
