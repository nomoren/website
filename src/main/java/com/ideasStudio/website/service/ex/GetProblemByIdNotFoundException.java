package com.ideasStudio.website.service.ex;

/**
 * 通过problems的id获取不到problem时发生的异常
 * @author 覃远祠
 *
 */
public class GetProblemByIdNotFoundException extends ServiceException {
	private static final long serialVersionUID = 1L;

	public GetProblemByIdNotFoundException() {
		super();
	}

	public GetProblemByIdNotFoundException(String arg0, Throwable arg1,
			boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public GetProblemByIdNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public GetProblemByIdNotFoundException(String arg0) {
		super(arg0);
	}

	public GetProblemByIdNotFoundException(Throwable arg0) {
		super(arg0);
	}

}
