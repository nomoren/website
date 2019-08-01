package com.ideasStudio.website.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 留言消息实体类，用于保存用户的留言消息
 * @author 覃远祠
 *
 */
public class LeaveMessage implements Serializable{
	private Integer id;     //留言ID
	private String name;	//留言人
	private String phone;   //留言人电话
	private String email;   //留言人邮箱
	private String messageType; //留言类型
	private String messageEntity; //留言消息实体
	private String createName;   //创建留言人
	private Date createTime;   //创建留言时间
	public LeaveMessage() {
	}
	
	/**
	 * 整个构造器用于存储用户新增留言是用到的构造器
	 * @param name  留言人
	 * @param phone 留言人电话
	 * @param email 留言人邮箱
	 * @param messageType 留言人留言类型
	 * @param messageEntity 留言尸体类容
	 */
	public LeaveMessage(String name,String phone,String email,String messageType,String messageEntity){
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.messageType = messageType;
		this.messageEntity = messageEntity;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getMessageEntity() {
		return messageEntity;
	}
	public void setMessageEntity(String messageEntity) {
		this.messageEntity = messageEntity;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "LeaveMessage [id=" + id + ", name=" + name + ", phone=" + phone + ", email=" + email + ", messageType="
				+ messageType + ", messageEntity=" + messageEntity + ", createName=" + createName + ", createTime="
				+ createTime + "]";
	}
	
}
