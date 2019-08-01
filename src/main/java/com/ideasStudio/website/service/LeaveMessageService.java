package com.ideasStudio.website.service;

import com.ideasStudio.website.entity.LeaveMessage;
import com.ideasStudio.website.service.ex.InsertLeaveMessageFailException;
/**
 * 业务层操作留言的接口文件
 * @author 覃远祠
 *
 */
public interface LeaveMessageService {
	/**
	 * 用户插入一条留言的方法
	 * @param message  插入的留言实体类
	 * @return	返回数据库中受影响的行数
	 * @throws InsertLeaveMessageFailException 新增留言失败抛出的异常
	 */
	void insertLeaveMessage(LeaveMessage message)throws InsertLeaveMessageFailException;
	
}
