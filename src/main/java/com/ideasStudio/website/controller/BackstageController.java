package com.ideasStudio.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 处理获取后台请求的Controller
 * @author 覃远祠
 *
 */
@Controller
@RequestMapping("/backstage")
public class BackstageController extends BaseController{
	
	/**
	 * 获得后台管理员首页方法
	 * @param modelAndView  spring容器传入modelandview
	 * @return 返回添加页面视图的modelandview
	 */
	@RequestMapping("/admin.do")
	public ModelAndView getAdminPage(ModelAndView modelAndView){
		modelAndView.setViewName("admin");
		return modelAndView;
	}
}
