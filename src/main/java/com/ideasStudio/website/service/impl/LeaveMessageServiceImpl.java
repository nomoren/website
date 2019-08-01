package com.ideasStudio.website.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideasStudio.website.entity.LeaveMessage;
import com.ideasStudio.website.mapper.ContactUsMapper;
import com.ideasStudio.website.service.LeaveMessageService;
import com.ideasStudio.website.service.ex.InsertLeaveMessageFailException;
import com.ideasStudio.website.service.ex.MessageTypeSwitchException;
import com.ideasStudio.website.service.ex.UpdateLeaveMessageStatusException;
import com.ideasStudio.website.util.SendEmail;
import com.ideasStudio.website.util.ex.SendEmailFailException;

/**
 * 留言的业务层处理接口实现
 * @author 覃远祠
 *
 */
@Service("leaveMessageService")
public class LeaveMessageServiceImpl implements LeaveMessageService{
	
	/**
	 * 自动装配mapper层
	 */
	@Autowired
	private ContactUsMapper leaveMessageMapper;
	
	/**
	 * 往数据库中插入一条留言
	 */
	public void insertLeaveMessage(LeaveMessage message) throws InsertLeaveMessageFailException{
		message.setCreateName(message.getName());	//插入留言人信息
		message.setCreateTime(new Date());			//插入留言时间
		//TODO		留言进入数据库的地方
		/*Integer rows =leaveMessageMapper.insertLeaveMessage(message);
		if(rows!=1){		//当往数据库中插入一条留言时，返回的受影响的行数不为1时，则说明插入失败，抛出异常
			throw new InsertLeaveMessageFailException("留言失败，请联系系统管理员！");
		}*/
		try {
			boolean isSendEmail=sendEmail(message);
			/*updateSendEmailStatus(message.getId(), isSendEmail);*///这句是更新留言状态的方法  
		} catch (SendEmailFailException e) {
			
		}
	}
	
	/**
	 * 更改数据库中客户留言内容的转台，
	 * @param messageID  需要更改的留言的ID
	 * @param sendEmailStatus 需要更改的状态
	 */
	public void updateSendEmailStatus(Integer messageID,boolean sendEmailStatus){
		Integer rows = leaveMessageMapper.updateIsSendEmail(messageID,sendEmailStatus);
		if(rows!=1){
			throw new UpdateLeaveMessageStatusException("邮件发送失败，稍后重试！");
		}
	}
	
	/**
	 * 发送邮件的方法，调用Util工具类中的SendEmail发送邮件即可
	 * @param message 需要发送的消息
	 */
	private boolean sendEmail(LeaveMessage message) {
		 try {
			String messageType = getMessageType(message.getMessageType());
			SendEmail sendemail = new SendEmail();   //new一个留言发送工具
			sendemail.setToEmail("wanggang@ideas-tudio.com");	//添加收件人邮箱
			sendemail.setFromEmail("2535672054@qq.com"); //添加发件人邮箱
			sendemail.setAutorizationCode("cxvwenplbjdyebbe"); //添加QQ授权代码
			sendemail.setTitleContent("客户留言-----"+messageType); //添加邮件标题内容
			sendemail.setContentEntity(setContentEntityFormat(message)); //添加邮件实体内容
			return sendemail.send();	//发送邮件
		 } catch (SendEmailFailException e) {
			throw e;
		}
	   }
	
	   
	/**
	 * 通过客户选择的服务类型代码，获取具体的服务类型的描述
	 * @throws MessageTypeSwitchException 此处会抛出选择服务类型异常 客户选择的类型不匹配
	 */
	public String getMessageType(String messageType) throws MessageTypeSwitchException{
		switch(messageType){
			case "1":return "产品咨询";
			case "2":return "下单方式";
			case "3":return "订单问题";
			case "4":return "企业合作";
			case "5":return "其他";
		}
		throw new MessageTypeSwitchException("客户服务类型异常，详情联系管理员!");
	}
	
	/**
	 * 此方法是设置邮件实体内容中的消息格式
	 * @return 返回待发送的邮件实体内容
	 */
	public String setContentEntityFormat(LeaveMessage message){
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss E");  //日期格式化
		String regexEmail = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+";
		sb.append("留言人:"+message.getName()+"\r\n");		//拼接上留言人
		sb.append("留言日期:"+sdf.format(message.getCreateTime())+"\r\n");	//拼接上留言日期
		sb.append("手机号码："+message.getPhone()+"\r\n");		//拼接上留言人手机号码
		//拼接上留言人邮箱地址(如果存在，如果不存在则拼接一个空串)
		String emailaddress = message.getEmail().matches(regexEmail)?message.getEmail():"用户未填写";
		sb.append("邮箱地址:"+emailaddress+"\r\n");
		sb.append("服务类型:"+getMessageType(message.getMessageType())+"\r\n");//拼接服务类型
		sb.append("留言内容:"+message.getMessageEntity()+"\r\n");  //拼接留言内容
		return sb.toString().trim();  //返回格式化好的留言实体内容
	}
	
}
