package com.ideasStudio.website.service.ex;

/**
 * 在获取问题板块时返回值为null 就是没有问题板块时抛出的异常
 * @author 覃远祠
 *
 */
public class GetProblemsIsNullException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public GetProblemsIsNullException() {
	}

	public GetProblemsIsNullException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public GetProblemsIsNullException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public GetProblemsIsNullException(String arg0) {
		super(arg0);
	}

	public GetProblemsIsNullException(Throwable arg0) {
		super(arg0);
	}

}
