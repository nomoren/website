package com.ideasStudio.website.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideasStudio.website.entity.User;
import com.ideasStudio.website.mapper.UserMapper;
import com.ideasStudio.website.service.CartService;
import com.ideasStudio.website.service.OrderService;
import com.ideasStudio.website.service.UserService;
import com.ideasStudio.website.service.ex.PhoneIsExistException;
import com.ideasStudio.website.service.ex.ServiceException;

/**
 * 用户信息服务接口
 * @author ME
 *
 */
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper UserMapper;
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;

	//用户登录
	@Override
	public User loginByPhoneAndCode(String phone) {
		User user=UserMapper.searchPhone(phone);//查看号码是否已已存在
		if(user==null) {//不存在，是新用户
			User newUser=createUser(phone);
			Integer num=UserMapper.register(newUser);
			if(num!=1) {
				throw new ServiceException("服务器繁忙，请稍后再试");
			}
			return newUser;//添加这个新用户，登录成功
		}
		return user;//旧用户，登录成功
	}
	/**
	 * 跟新用户的个人信息
	 */
	@Override
	public void modifiedUserInfo(User user) {
		User data=UserMapper.searchPhone(user.getPhone());
		if(data!=null) {
			if(!data.getId().equals(user.getId())) {
			throw new PhoneIsExistException("该号码已被注册");
			}
		}
		Integer num=UserMapper.modifiedUserInfo(user);
		if(num!=1) {
			throw new ServiceException("服务器繁忙，请稍后再试");
		}
	}
	//获取用户姓名
	@Override
	public String getUserName(String id) {
		return UserMapper.getUserNameById(id);
	}
	//分页查询用户列表
	@Override
	public List<User> getUserList(Integer page,Integer status) {
		Integer num=5;//每页5条数据
		Integer jump=(page-1)*5;//跳过了多少条
		List<User> list=UserMapper.getUserList(jump,num,status);
		return list;
	}
	//获取用户数量
	@Override
	public Integer getCountUser(Integer status) {
		return UserMapper.getUserCount(status);
	}
	//修改用户状态
	@Override
	public void updateUserStatus(Integer status, String id) {
		UserMapper.updateUserStatus(status, id);
	}
	//删除用户
	@Override
	public void deleteUser(String id) {
		Integer status=UserMapper.getUserStatus(id);
		if(status==1) {
			throw new ServiceException("启用状态的用户不能删除");
		}
		String userName=UserMapper.getUserNameById(id);
		Integer cartCount=cartService.getCartCount(id);
		Integer orderCount=orderService.getUserOrderCount(id);
		if(userName!=null||cartCount!=0||orderCount!=0) {
			throw new ServiceException("该用户关联了其他操作，不能删除，只能停用");
		}
		UserMapper.deleteUserById(id);
	}
	//根据微信登录的openid查询用户
	@Override
	public User getUserByOpenId(String unionid) {		
		return UserMapper.getUserByOpenId(unionid);
	}
	//微信绑定手机号登录，如果用户是第一次使用微信登录，绑定下手机号
	@Override
	public User wxBangDing(String phone, String unionid) {
		User user=UserMapper.searchPhone(phone);
		if(user==null) {//新用户，第一次微信登录，绑定。
			User newUser=createUser(phone);
			newUser.setUnionid(unionid);
			Integer num=UserMapper.register(newUser);
			if(num!=1) {
				throw new ServiceException("服务器繁忙，请稍后再试");
			}
			return newUser;
		}else {//已有用户，第一次微信登录，绑定
			UserMapper.insertOpenid(user.getId(), unionid);
		}
		return user;
	}

	//创建一个新用户
	private User createUser(String phone) {
		User newUser=new User();
		String id=UUID.randomUUID().toString().substring(0, 8);
		newUser.setId(id);
		newUser.setPhone(phone);
		newUser.setCreateTime(new Date());
		newUser.setStatus(1);
		return newUser;
	}
	//查询当天新增的用户数量
	@Override
	public Integer getDayUserCount() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String end=sdf.format(new Date());//截止至当前时间
		Calendar c=Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);//00:00:00
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		String start=sdf.format(c.getTime());
		return UserMapper.getDayUserCount(start, end);
	}
	
	
	
	/*@Override
	public void insert(User user) {
		User isExist=UserMapper.searchPhone(user.getPhone());
		if(isExist!=null) {
			throw new PhoneConflictException("注册失败，此号码"+user.getPhone()+"已存在");
		}else {
			UserMapper.register(user);
		}

	}*/
}



