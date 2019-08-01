package com.ideasStudio.website.service;

import java.util.List;



import com.ideasStudio.website.entity.User;

/**
 * 用户信息
 * @author ME
 *
 */
public interface UserService {
	
	/**
	 * 普通登录
	 * @param phone
	 */
	User loginByPhoneAndCode(String phone);
	
	/**
	 * 修改用户个人信息
	 * @param user
	 */
	void modifiedUserInfo(User user);
	
	/**
	 * 查询用户姓名
	 * @param id
	 * @return
	 */
	String getUserName(String id);
	
	/**
	 * 分页查询用户列表
	 * @param page 第几页  status 0，1
	 * @return
	 */
	List<User> getUserList(Integer page,Integer status);
	
	/**
	 * 获取用户总数
	 * @return
	 */
	Integer getCountUser(Integer status);
	/**
	 * 改变用户的状态
	 * @param status 0，1
	 * @param id 用户id
	 */
	void updateUserStatus(Integer status,String id);
	
	/**
	 * 删除用户
	 * @param id 用户id
	 */
	void deleteUser(String id);
	
	
	/**
	 * 根据openid查询用户
	 * @param openid
	 * @return
	 */
	User getUserByOpenId(String unionid);
	
	/**
	 * 微信绑定登录
	 * @param phone 手机号
	 * @param openid 微信登录唯一标识符
	 */
	User wxBangDing(String phone,String unionid);
	/**
	 * 查询当天新增的用户数量
	 * @return
	 */
	Integer getDayUserCount();
	/**
	 * 插入一条号码
	 * @param phone
	 *//*
	void insert(User user);*/
}
