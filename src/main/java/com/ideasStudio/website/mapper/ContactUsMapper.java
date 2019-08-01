package com.ideasStudio.website.mapper;

import org.apache.ibatis.annotations.Param;

import com.ideasStudio.website.entity.LeaveMessage;

/**
 * 留言表操作数据库接口
 * @author 覃远祠
 *
 */
public interface ContactUsMapper {
	
	/**
	 * 用户插入一条留言的方法
	 * @param message  插入的留言实体类
	 * @return	返回数据库中受影响的行数
	 */
	Integer insertLeaveMessage(LeaveMessage message);
	
	/**
	 * 更新数据库中留言表状态，是否已经发送邮件
	 * @param id	被更改的id
	 * @param isSend	是否发送状态码 true为已经发送成功，
	 * @return	返回受影响的行数
	 */
	Integer updateIsSendEmail(@Param("id")Integer id,@Param("SendEmail")boolean isSend);
}
