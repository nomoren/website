package com.ideasStudio.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ideasStudio.website.entity.LeaveMessage;
import com.ideasStudio.website.entity.ResponseResult;
import com.ideasStudio.website.service.LeaveMessageService;

/**
 * 处理联系我们请求的Controller
 * @author 覃远祠
 *
 */
@Controller
@RequestMapping("/leavemessage")
public class ContactUsController extends BaseController{
	
	/**
	 * 自动装配业务层对象
	 */
	@Autowired	
	private LeaveMessageService leavemessageservice;
	
	/**
	 * 处理插入留言的方法
	 * @param name	留言人姓名
	 * @param phone 留言人电话
	 * @param email 留言人邮箱
	 * @param messageType 留言人留言类型
	 * @param messageEntity  留言人消息实体内容
	 * @return  返回正确插入一条留言的结果返回的状态吗为200，message为空
	 */
	@RequestMapping("/add.do")
	@ResponseBody
	public ResponseResult<Void> handleAddLeaveMessage(
		@RequestParam("name")String name,
		@RequestParam("phone")String phone,
		@RequestParam(value="email",required=false,defaultValue="0") String email,
		@RequestParam("messageType")String messageType,
		@RequestParam("messageEntity")String messageEntity){
		LeaveMessage message = new LeaveMessage(name,phone,email,messageType,messageEntity);
		leavemessageservice.insertLeaveMessage(message);   //业务层执行插入留言的操作
		return new ResponseResult<Void>();
	}
}
