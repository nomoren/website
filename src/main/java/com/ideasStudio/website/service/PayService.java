package com.ideasStudio.website.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 支付接口
 * @author Administrator
 *
 */
public interface PayService {
	
	/**
	 * 支付宝支付
	 * @param request	请求对象
	 * @param response	相应对象
	 * @param id 订单编号
	 */
	void Alipay(HttpServletRequest request,HttpServletResponse response,String id);
	
	/**
	 * 微信支付
	 * @param request	请求对象
	 * @param response	相应对象
	 * @param id 订单编号
	 */
	void WeiXinPay(HttpServletRequest request,HttpServletResponse response,String id);
	
	/**
	 * 微信支付完成回调
	 * @param request	请求对象
	 * @param response 相应对象
	 */
	void WXNotify(HttpServletRequest request,HttpServletResponse response);
	
	
	/**
	 * 支付宝支付完成异步回调方法
	 * @param request  请求对象
	 */
	void AliNotify(HttpServletRequest request,HttpServletResponse response);
	
	/**
	 * 支付宝支付成功同步通知
	 * @param request
	 */
	void AliReturn(HttpServletRequest request);
}
