package com.ideasStudio.website.admin;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ideasStudio.website.controller.BaseController;
import com.ideasStudio.website.entity.ResponseResult;
import com.ideasStudio.website.entity.User;
import com.ideasStudio.website.service.UserService;
import com.ideasStudio.website.service.ex.ServiceException;
/**
 * 后台用户管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin")
public class UserManageController extends BaseController{

	@Autowired
	private UserService userService;
	/**
	 * admin登录
	 * @param password 密码对即可登录,密码可在config.properties修改
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/login.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> toLogin(String password,HttpSession session){
		Properties prop=new Properties();
		InputStream in=UserManageController.class.getClassLoader().getResourceAsStream("config.properties");
		String pwd=null;
		try {
			prop.load(in);
			pwd=prop.getProperty("adminPwd");
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(!password.equals(pwd)) {
			throw new ServiceException("密码错误");
		}
		session.setAttribute("admin", "admin");
		return new ResponseResult<>();
	}
	
	/**
	 * 分页查询用户列表
	 * @param num 第几页 status 商品状态
	 * @return
	 */
	@RequestMapping(value="/getUserList.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Map<Object, Object>> getUserList(Integer page,Integer status){
		Map<Object, Object> map=new HashMap<>();
		Integer number=userService.getCountUser(status);//用户总数
		Integer allPage=1;//总页，当数据不够分页，默认一页
		if(number>5) {
			allPage=number%5==0?number/5:number/5+1;//可以分页时能分几页
		}
		if(page>allPage) {
			throw new ServiceException("没有数据了");
		}
		map.put("num", number);//总数
		map.put("allPage", allPage);//共几页
		List<User> list=userService.getUserList(page,status);
		map.put("list", list);//用户数据
		ResponseResult<Map<Object, Object>> rr=new ResponseResult<>();
		rr.setData(map);
		return rr;
	}
	
	/**
	 * 更改用户状态
	 * @param status
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/changeUserStatus.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> changUserStatus(Integer status,String id){
		userService.updateUserStatus(status, id);
		return new ResponseResult<>();
	}
	/**
	 * 删除用户
	 * @param id 用户id
	 * @return
	 */
	@RequestMapping(value="/deleteUser.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> deleteUser(String id){
		userService.deleteUser(id);
		return new ResponseResult<>();
	}
}
