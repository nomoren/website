package com.ideasStudio.website.service.ex;

/**
 * 插入数据失败，可能数据库服务关了
 * @author ME
 *
 */
public class InsertException extends ServiceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsertException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InsertException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public InsertException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public InsertException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public InsertException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	
}
