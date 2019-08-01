package com.ideasStudio.website.service.ex;

/**
 * 当插入一条留言的时候，返回的受影响的行数不是1的时候抛出此异常
 * @author 覃远祠
 *
 */
public class InsertLeaveMessageFailException extends ServiceException{

	private static final long serialVersionUID = 1L;

	public InsertLeaveMessageFailException() {
		super();
	}

	public InsertLeaveMessageFailException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public InsertLeaveMessageFailException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public InsertLeaveMessageFailException(String arg0) {
		super(arg0);
	}

	public InsertLeaveMessageFailException(Throwable arg0) {
		super(arg0);
	}
}
