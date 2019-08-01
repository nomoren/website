package com.ideasStudio.website.service.ex;

/**
 * 通过问题详情的id获取问题是找不到对应的问题时抛出的异常
 * @author 覃远祠
 *
 */
public class GetQAByIdNotFoundException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public GetQAByIdNotFoundException() {
	}

	public GetQAByIdNotFoundException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public GetQAByIdNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public GetQAByIdNotFoundException(String arg0) {
		super(arg0);
	}

	public GetQAByIdNotFoundException(Throwable arg0) {
		super(arg0);
	}

}
