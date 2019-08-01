package com.ideasStudio.website.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * HTML页面过滤器，有些页面需要登录后才能访问
 */
public class HtmlFilter implements Filter {
	//配置黑名单，在名单内的页面需要登陆后才能访问
	private List<String> blackList=new ArrayList<>();
	//后台管理系统，后台几乎所有页面都需要登录才能访问
	private List<String> adminList=new ArrayList<>();
	//初始化黑名单
	public void init(FilterConfig fConfig) throws ServletException {
		blackList.add("cart.html");
		blackList.add("saved_resource.html");
		blackList.add("satellite-59d355d764746d56a2000b55.html");
		blackList.add("checkout.html.html");
		blackList.add("checkout-pay.html.html");
		
		adminList.add("cate.html");adminList.add("city.html");adminList.add("echarts1.html");
		adminList.add("echarts2.html");adminList.add("echarts3.html");adminList.add("echarts4.html");
		adminList.add("echarts5.html");adminList.add("echarts6.html");adminList.add("echarts7.html");
		adminList.add("echarts8.html");adminList.add("member-add.html");
		adminList.add("member-del.html");adminList.add("member-edit.html");adminList.add("member-list.html");
		adminList.add("order-add.html");adminList.add("order-list.html");adminList.add("role-add.html");
		adminList.add("unicode.html");adminList.add("addCoupons.html");
		adminList.add("addCoupons.html");adminList.add("addEmpcode.html");adminList.add("checkEmpCodeRecord.html");
		adminList.add("coupons.html");adminList.add("empCode.html");
		adminList.add("okStatus.html");adminList.add("adminIndex.html");
		adminList.add("outStatus.html");
		adminList.add("payStatus.html");
		adminList.add("takeStatus.html");
		adminList.add("userAddress.html");
	}
	public void doFilter(ServletRequest Srequest, ServletResponse Sresponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest) Srequest;
		HttpServletResponse response=(HttpServletResponse) Sresponse;
		HttpSession session=request.getSession();
		String uri=request.getRequestURI();
		String htmlName=uri.substring(uri.lastIndexOf("/")+1);
		if(blackList.contains(htmlName)) {//判断是否在黑名单内
			if(session.getAttribute("uid")==null) {//如果没有登录，重定向到列表页
				System.out.println("html拦截");
				response.sendRedirect(request.getContextPath()+"/store/productlist.html");
				return;
			}
		}
		if(adminList.contains(htmlName)) {
			if(session.getAttribute("admin")==null) {
				response.sendRedirect(request.getContextPath()+"/admin/login.html");
				return;
			}
		}
		chain.doFilter(request, response);
	}

	public void destroy() {
	}


}
