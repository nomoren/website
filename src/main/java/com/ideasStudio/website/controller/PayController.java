package com.ideasStudio.website.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ideasStudio.website.service.PayService;
import com.ideasStudio.website.util.StringUtil;

/**
 * 支付接口
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/pay")
public class PayController extends BaseController {

	@Autowired
	private PayService payservice;
	
	Logger logger =Logger.getLogger(PayController.class);
	/**
	 * 
	 * @param orderNo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/alipay.do")
	@ResponseBody
	public void aliPay(
			@RequestParam(value="orderId",required=true)String orderNo,
			HttpServletRequest request,HttpServletResponse response){
			try {
				payservice.Alipay(request, response, orderNo);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	/**
	 * wx请求支付页面  此请求会直接写出二维码
	 * @param orderNo	订单号码
	 * @param request	请求对象
	 * @param response	相应对象
	 */
	@RequestMapping("/wxpay.do")
	@ResponseBody
	public void wxPay(
			@RequestParam(value="orderId",required=true)String orderNo,
			HttpServletRequest request,HttpServletResponse response){
			try {
				payservice.WeiXinPay(request, response, orderNo);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * 转发到微信支付页面
	 * @return
	 */
	@RequestMapping("/gowxpay.do")
	public String getWxPayPage(@RequestParam(value="orderId",required=true)String orderNo,
				ModelMap map){
		map.addAttribute("orderId",orderNo);
		map.addAttribute("variate",StringUtil.getRandomString(5).toString());		//添加一个随机值，二次刷新二维码都会改变
		return "store/WeixinPay";
	}
	
	/**
	 * 支付宝支付结果异步通知
	 * @param request
	 * @return
	 */
	@RequestMapping("/aliNotifyUrl.do")
	public void aliNotify(HttpServletRequest request,HttpServletResponse response){
		logger.info("<<---------支付宝回调------->>"); 
		payservice.AliNotify(request,response);			//支付宝这里不需要传回调参数给支付宝  没有使用response
	}
	
	/**
	 * 支付宝支付成功时同步回调方法
	 * 同步通知内容eg:
	 * total_amount=9.00
	 * timestamp=2016-08-11+19%3A36%3A01
	 * sign=ErCRRVmW%2FvXu1XO76k%2BUr4gYKC5%2FWgZGSo%2FR7nbL%2FPU7yFXtQJ2CjYPcqumxcYYB5x%2FzaRJXWBLN3jJXr01Icph8AZGEmwNuzvfezRoWny6%2Fm0iVQf7hfgn66z2yRfXtRSqtSTQWhjMa5YXE7MBMKFruIclYVTlfWDN30Cw7k%2Fk%3D
	 * trade_no=2016081121001004630200142207
	 * sign_type=RSA2
	 * charset=UTF-8
	 * seller_id=2088111111116894
	 * method=alipay.trade.wap.pay.return
	 * app_id=2016040501024706
	 * out_trade_no=70501111111S001111119
	 * version=1.0
	 * @param request
	 * @return
	 */
	@RequestMapping("/aliReturnUrl.do")
	public String aliReturn(HttpServletRequest request){
		payservice.AliReturn(request);
		return "store/paysuccess"; 								   //TODO 返回支付页面

	}
	
	/**
	 * wx支付结果异步通知处理方法
	 * @param request
	 * @param response
	 */
	@RequestMapping("/wxNotifyUrl.do")
	@ResponseBody
	public void wxNotify(HttpServletRequest request,HttpServletResponse response){
		payservice.WXNotify(request,response);
	
	}
}
