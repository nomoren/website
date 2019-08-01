package com.ideasStudio.website.service.ex;

/**
 * 根据父级板块的id删除下面的所有问题详情
 * @author 覃远祠
 *
 */
public class DelQAByparentIdFailException extends ServiceException{

	private static final long serialVersionUID = 1L;

	public DelQAByparentIdFailException() {
		super();
	}

	public DelQAByparentIdFailException(String arg0, Throwable arg1,
			boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public DelQAByparentIdFailException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public DelQAByparentIdFailException(String arg0) {
		super(arg0);
	}

	public DelQAByparentIdFailException(Throwable arg0) {
		super(arg0);
	}


}
