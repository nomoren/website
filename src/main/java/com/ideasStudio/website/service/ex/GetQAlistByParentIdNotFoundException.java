package com.ideasStudio.website.service.ex;

/**根据父级板块id抄找不到问题详情时发生的异常
 * @author 覃远祠
 */
public class GetQAlistByParentIdNotFoundException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public GetQAlistByParentIdNotFoundException() {
	}

	public GetQAlistByParentIdNotFoundException(String arg0, Throwable arg1,
			boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public GetQAlistByParentIdNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public GetQAlistByParentIdNotFoundException(String arg0) {
		super(arg0);
	}

	public GetQAlistByParentIdNotFoundException(Throwable arg0) {
		super(arg0);
	}

}
