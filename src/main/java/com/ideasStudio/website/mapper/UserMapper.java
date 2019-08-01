package com.ideasStudio.website.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ideasStudio.website.entity.User;

/**
 * 处理用户信息demapper
 * @author ME
 *
 */
public interface UserMapper {
	/**
	 * 查询号码是否存在
	 * @param phone
	 * @return 
	 */
	User searchPhone(String phone);
	/**
	 * 注册
	 * @param phone 手机号
	 * @return 
	 */
	Integer register(User user);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	Integer modifiedUserInfo(User user);
	
	/**
	 * 根据用户id查询用户姓名
	 * @param id
	 * @return
	 */
	String getUserNameById(String id);
	/**
	 * 查询用户数量
	 * @return
	 */
	Integer getUserCount(Integer status);
	/**
	 * 分页查询用户列表
	 * @param jump 跳过的条数
	 * @return
	 */
	List<User> getUserList(@Param("jump")Integer jump,@Param("num")Integer num,@Param("status")Integer status);
	/**
	 * 改变用户状态
	 * @param status 0，1
	 * @return
	 */
	Integer updateUserStatus(@Param("status")Integer status,@Param("id")String id);
	/**
	 * 删除用户信息
	 * @param id
	 * @return
	 */
	Integer deleteUserById(String id);
	/**
	 * 获取用户状态
	 * @param id
	 * @return
	 */
	Integer getUserStatus(String id);
	/**
	 * 根据openid查询用户
	 * @param openid
	 * @return
	 */
	User getUserByOpenId(String unionid);
	/**
	 * 微信登陆后，插入openid
	 * @param openid
	 * @return
	 */
	Integer insertOpenid(@Param("uid")String uid,@Param("unionid")String unionid);
	/**
	 * 查看当天新增的用户数量
	 * @param start
	 * @param end
	 * @return
	 */
	Integer getDayUserCount(@Param("start")String start,@Param("end")String end);
}
