package com.ideasStudio.website.service.ex;

/**
 * 更新留言是否发送成功状态时发生的异常
 * @author 	覃远祠
 */
public class UpdateLeaveMessageStatusException extends ServiceException{

	private static final long serialVersionUID = 1L;

	public UpdateLeaveMessageStatusException() {
		super();
	}

	public UpdateLeaveMessageStatusException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public UpdateLeaveMessageStatusException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public UpdateLeaveMessageStatusException(String arg0) {
		super(arg0);
	}

	public UpdateLeaveMessageStatusException(Throwable arg0) {
		super(arg0);
	}


}
