package com.ideasStudio.website.service.ex;

/**
 * 插入大问题板块时抛出的异常
 * @author 覃远祠
 */
public class InsertProblemsFailException extends ServiceException{

	private static final long serialVersionUID = 1L;

	public InsertProblemsFailException() {
		super();
	}

	public InsertProblemsFailException(String arg0, Throwable arg1,
			boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public InsertProblemsFailException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public InsertProblemsFailException(String arg0) {
		super(arg0);
	}

	public InsertProblemsFailException(Throwable arg0) {
		super(arg0);
	}
	
}
