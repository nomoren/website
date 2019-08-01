package com.ideasStudio.website.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ideasStudio.website.entity.ResponseResult;
import com.ideasStudio.website.entity.User;
import com.ideasStudio.website.service.UserService;
import com.ideasStudio.website.service.ex.CodeNotSameException;
import com.ideasStudio.website.service.ex.CodeOutOfDateException;
import com.ideasStudio.website.service.ex.ServiceException;
import com.ideasStudio.website.util.SentMessage;
import com.ideasStudio.website.util.WeiXinUtil;

import net.sf.json.JSONObject;

/**
 * 处理用户信息controller，包括注册和登陆
 * @author 赵志斌
 *
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	private String copyCode;//用于判断用户是否没有获取验证码就进行登录或注册
	@Autowired
	private UserService userService;
	/**
	 * 登陆
	 * @param phone 手机号
	 * @param code  验证码
	 * @return
	 */
	@RequestMapping(value="/login.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> login(String phone,HttpSession session,
									  String code,@RequestParam(value="unionid",required=false)String unionid){
		String realCode=(String) session.getAttribute(phone);
		System.out.println("用户"+phone+"输入的："+code);
		System.out.println("后台发给"+phone+"的："+realCode);
		synchronized (this) {
			if(realCode!=null) {
				copyCode=realCode;
			}
			if(realCode==null) {//用户获取了验证码，但是过期了
				if(code.equals(copyCode)) {
					throw new CodeOutOfDateException("验证码已过期，请重新获取");
				}
				throw new CodeNotSameException("请获取验证码");//用户没有获取验证码
			}
		}
		if(code.length()!=6||!code.equals(realCode)) {
			throw new CodeNotSameException("验证码错误");
		}
		//登录有两种情况，普通登录和微信绑定手机号后登录
		User user=null;
		System.out.println("登录+"+unionid);
		if(unionid!=null) {
			user=userService.wxBangDing(phone, unionid);
		}else {
			//普通登录
			user=userService.loginByPhoneAndCode(phone);
		}
		if(user.getStatus()==0) {
			throw new ServiceException("您已被禁止登陆系统!");
		}
		session.setAttribute("uid", user.getId());
		return new ResponseResult<>();
	}
	/**
	 * 检查用户的登录状态
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/checkLogin.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Map<Object, Object>> checkLogin(HttpSession session){
		Map<Object, Object> map=new HashMap<>();
		Integer loginStatus=0;//未登录
		String userName="";
		if(session.getAttribute("uid")!=null) {
			loginStatus=1;//已登录,把用户姓名也查询来
			userName=userService.getUserName(session.getAttribute("uid").toString());
		}
		map.put("loginStatus", loginStatus);
		map.put("name", userName);
		ResponseResult<Map<Object, Object>> rr=new ResponseResult<>();
		rr.setData(map);
		return rr;
	}
	//退出登录
	@RequestMapping(value="/outLogin.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> outLogin(HttpSession session){
		session.invalidate();//删除会话记录
		return new ResponseResult<>();
	}
	/**
	 * 修改用户个人信息
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/changeUserInfo.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> modifiedUserInfo(HttpSession session,User user){
		String uid=session.getAttribute("uid").toString();
		user.setId(uid);
		userService.modifiedUserInfo(user);
		return new ResponseResult<>();
	}
	
	
	/**
	 * 发送验证码
	 * @param phone 手机号
	 * @param session 
	 */
	@ResponseBody
	@RequestMapping(value="/getCode.do",method=RequestMethod.POST)
	public ResponseResult<Void> sendMessage(final String phone,final HttpSession session) {
		String code=null;
		try {
			code = SentMessage.sent(phone);
		} catch (Exception e) {
			throw new ServiceException("验证码获取失败，请稍后再试");
		}//发送验证码到用户手机
		session.setAttribute(phone, code);//保存验证码，用于登录注册验证
		//开启一个定时器，5分钟后删除验证码
		final Timer timer=new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				session.removeAttribute(phone);
				timer.cancel();
				System.out.println(phone+"的验证码删除成功");
			}
		}, SentMessage.TIMEOUT);
		return new ResponseResult<>();
	}
	/**
	 * 微信手机登录接口
	 * @param response
	 */
	@RequestMapping(value="/mWxLogin.do",method=RequestMethod.GET)
	public void mWeixinLogin(HttpServletResponse response) {
		String redirect_uri="http://www.ideas-tudio.com/user/mwxBack.do";
		String url="https://open.weixin.qq.com/connect/oauth2/authorize?"
				+ "appid="+WeiXinUtil.M_APPID
				+ "&redirect_uri="+URLEncoder.encode(redirect_uri)
				+ "&response_type=code"
				+ "&scope=snsapi_userinfo"
				+ "&state=STATE#wechat_redirect";
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
	}
	/**
	 * 移动端微信登录回调接口
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/mwxBack.do",method=RequestMethod.GET)
	public String mweixinBack(HttpServletRequest request,HttpSession session) {
		request.getSession();
		String code=request.getParameter("code");
		String url="https://api.weixin.qq.com/sns/oauth2/access_token?"
				+ "appid="+WeiXinUtil.M_APPID
				+ "&secret="+WeiXinUtil.M_APPSECRET
				+ "&code="+code
				+ "&grant_type=authorization_code";
		String result=getUserInfo(url, request, session);
		return result;
	}
	/**
	 * pc微信登录接口
	 * @param response
	 */
	@RequestMapping(value="/wxLogin.do",method=RequestMethod.GET)
	public void weixinLogin(HttpServletResponse response) {
		String redirect_uri="http://www.ideas-tudio.com/user/wxBack.do";
		String url="https://open.weixin.qq.com/connect/qrconnect?"
				+ "appid="+WeiXinUtil.APPID
				+ "&redirect_uri="+URLEncoder.encode(redirect_uri)
				+ "&response_type=code"
				+ "&scope=snsapi_login"
				+ "&state=STATE#wechat_redirect";
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
	}
	/**
	 * pc微信回调接口
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/wxBack.do",method=RequestMethod.GET)
	public String weixinBack(HttpServletRequest request,HttpSession session) {
		String code=request.getParameter("code");
		String url="https://api.weixin.qq.com/sns/oauth2/access_token?"
				+ "appid="+WeiXinUtil.APPID
				+ "&secret="+WeiXinUtil.APPSECRET
				+ "&code="+code
				+ "&grant_type=authorization_code";
		String result=getUserInfo(url, request, session);
		return result;
	}
	//授权后获取用户信息
	private String getUserInfo(String url,HttpServletRequest request,HttpSession session) {
		JSONObject json=WeiXinUtil.doGetJson(url);
		String access_token=json.getString("access_token");
		String openid=json.getString("openid");
		String infoUrl="https://api.weixin.qq.com/sns/userinfo?"
				+ "access_token="+access_token
				+ "&openid="+openid
				+ "&lang=zh_CN";
		JSONObject userInfo=WeiXinUtil.doGetJson(infoUrl);//用户授权后，根据openid返回的用户信息
		/*System.out.println("====="+userInfo.getString("unionid"));*/
		User user=userService.getUserByOpenId(userInfo.getString("unionid"));
		if(user==null) {//让用户微信登录和手机号绑定
			request.setAttribute("unionid", userInfo.getString("unionid"));
			return "WEB-INF/banding";
		}
		if(user.getStatus()==0) {
			throw new ServiceException("您已被禁止登陆系统!");
		}
		session.setAttribute("uid", user.getId());//已绑定
		return "redirect:/store/productlist.html";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 注册
	 * @param user 用户信息
	 * @param code  验证码
	 * @return
	 *//*
	@RequestMapping(value="/reg.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> reg(User user,HttpSession session,String code){
		String realCode=(String) session.getAttribute(user.getPhone());
		System.out.println("用户"+user.getPhone()+"输入的："+code);
		System.out.println("后台发给"+user.getPhone()+"的："+realCode);
		synchronized (this) {
			if(realCode!=null) {
				copyCode=realCode;
			}
			if(realCode==null) {//用户获取了验证码，但是过期了
				if(code.equals(copyCode)) {
					throw new CodeOutOfDateException("验证码已过期，请重新获取");
				}
				throw new CodeNotSameException("请获取验证码");//用户没有获取验证码
			}
		}
		if(!code.equals(realCode)) {
			throw new CodeNotSameException("验证码错误");
		}
		userService.insert(user);
		session.setAttribute("uid", user.getId());
		return new ResponseResult<>();
	}*/
}
