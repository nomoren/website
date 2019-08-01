package com.ideasStudio.website.service.ex;

/**
 * 插入问题详情时发生的异常
 * @author 覃远祠
 */
public class InsertQAFailException extends ServiceException{

	private static final long serialVersionUID = 1L;

	public InsertQAFailException() {
		super();
	}

	public InsertQAFailException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public InsertQAFailException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public InsertQAFailException(String arg0) {
		super(arg0);
	}

	public InsertQAFailException(Throwable arg0) {
		super(arg0);
	}
	
	
	
}
