package com.ideasStudio.website.service.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideasStudio.website.config.AlipayConfig;
import com.ideasStudio.website.config.WeixinConfig;
import com.ideasStudio.website.entity.EmpCodeRecord;
import com.ideasStudio.website.mapper.OrderMapper;
import com.ideasStudio.website.service.CouponsService;
import com.ideasStudio.website.service.PayService;
import com.ideasStudio.website.service.WebSocketCommService;
import com.ideasStudio.website.util.AliPayUtil;
import com.ideasStudio.website.util.WeixinPayUtil;
import com.ideasStudio.website.util.XmlUtil;

@Service
public class PayServiceImpl implements PayService{
	
	@Autowired
	private WebSocketCommService websocket;
	
	@Autowired	
	private OrderMapper ordermapper;
	@Autowired
	private CouponsService couponsService;
	
	Logger logger = Logger.getLogger(PayServiceImpl.class);
	

	@Override
	public void Alipay(HttpServletRequest request, HttpServletResponse response, String id) {
		AliPayUtil alipay = new AliPayUtil();
		String body = "";
		double total = ordermapper.getOrderMoneyByOrderId(id);
		String orderNo = ordermapper.getOrderNo(id);
		String User_Agent = request.getHeader("User-Agent");
		if(User_Agent.contains("Android")){
			body = alipay.UnifiedOrderMobile(orderNo, String.valueOf(total), AlipayConfig.title);
		}else{
			body = alipay.UnifiedOrder(orderNo, String.valueOf(total), AlipayConfig.title);
		}
		try {
			logger.error("支付请求参数:"+body);
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().println(body);
			response.flushBuffer();
		} catch (IOException e) {
			throw new RuntimeException("写出异常");
		}
	}

	@Override
	public void WeiXinPay(HttpServletRequest request, HttpServletResponse response, String id) {
		WeixinPayUtil wxpay = new WeixinPayUtil();
		double total = ordermapper.getOrderMoneyByOrderId(id);
		String orderNo = ordermapper.getOrderNo(id);
		BufferedImage code;		//生成的二维码
		String url = "";
		String User_Agent = request.getHeader("User-Agent");
		try {
			if(User_Agent.contains("Android")&&!User_Agent.contains("MicroMessenger")){
				System.out.println("除微信APP外的第三方浏览器支付");
				url = wxpay.UnifiedOrderMobile(request, orderNo, WeixinConfig.title, String.valueOf(total));
				response.sendRedirect(url);
				System.out.println("支付地址是："+url);
				response.flushBuffer();
			}else{
				code = wxpay.UnifiedOrder(request, orderNo, WeixinConfig.title, String.valueOf(total));
				ImageIO.write(code, "png", response.getOutputStream());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("微信支付异常");
		} 
	}

	@Override
	public void WXNotify(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> return_data = new HashMap<String,Object>();
		WeixinPayUtil wxpay = new WeixinPayUtil();
		SortedMap<Object,Object> map = wxpay.NotityResult(request);
		if(map!=null){		//说明交易成功 交易不成功返回null
			String orderNo = map.get("out_trade_no").toString();
			String endtime = map.get("time_end").toString();//格式yyyyMMddHHmmss
			Integer status = ordermapper.getOrderPayStatusByOrderNo(orderNo);
			double total_amount = Double.valueOf(map.get("total_fee").toString()) / 100.00;	//获取微信返回的金额 并且转换为元
			if(status==0){	//在订单未支付的情况下操作
				double amountPayable = ordermapper.getTotalByOrderNo(orderNo);
				if(total_amount==amountPayable){
					ordermapper.UpdatePayResult(orderNo, endtime, "1", "0");    //1默认支付状态 0位微信支付
					websocket.send("SUCCESS", orderNo, "支付成功");
					return_data.put("return_code", "SUCCESS");  				//设置微信的返回参数
					String couponsCode=ordermapper.getCouponsByOrderNo(orderNo);
					if(!"".equals(couponsCode)){
						updateCouponsUseRecord(couponsCode, orderNo);
					}
				}
			}
			return_data.put("return_code", "SUCCESS");  		//设置微信的返回参数
		}else{
			return_data.put("return_code", "FAIL");  			//如果微信返回FAIL 也返回微信FAIL 
            return_data.put("return_msg", "return_code不正确");	//添加返回FAIL 的描述信息
		}
		try {
			response.getWriter().println(XmlUtil.genXml(return_data));
		} catch (IOException e) {
			throw new RuntimeException("写出异常");
		}
	}


	@Override
	public void AliNotify(HttpServletRequest request,HttpServletResponse response) {
		logger.info("支付宝异步回调开始");
		AliPayUtil alipay = new AliPayUtil();
		Map<String,Object> result = alipay.NotityResult(request);
		boolean flag = result ==null;
		logger.info("返回结果是否为null:"+flag);
		result.entrySet().forEach(entry-> logger.info(entry.getKey()+"----支付宝回调参数-----"+entry.getValue()));
		if(result!=null){
			String orderNo = result.get("orderNo").toString();
			logger.info("回调单号:"+orderNo);
			String endtime = result.get("endtime").toString();
			logger.info("回调时间:"+endtime);
			String tradeStatus = result.get("trade_status").toString();
			logger.info("回调状态:"+tradeStatus);
			double total_amount = Double.valueOf(result.get("total_amount").toString());
			logger.info("回调金额："+total_amount);
			Integer status = ordermapper.getOrderPayStatusByOrderNo(orderNo);
			if(status==0){	//在订单未支付的情况下操作
				double amountPayable = ordermapper.getTotalByOrderNo(orderNo);
				if(total_amount!=amountPayable){
					logger.error("应付金额和实际支付金额不相符");
					throw new RuntimeException("应付金额和实际支付金额不相符");
				}
				logger.info("<<<------准备修改数据库状态------>>>");
				ordermapper.UpdatePayResult(orderNo, endtime, tradeStatus, "1");   //1默认为支付宝支付
				logger.info("<<<------数据库状态修改完毕------>>>");
				String couponsCode=ordermapper.getCouponsByOrderNo(orderNo);
				try {
					response.getWriter().println("success");
					logger.info("返回给支付宝状态码: 'success'");
				} catch (IOException e) {
					try {
						response.getWriter().println("success");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if(!"".equals(couponsCode)) {
					updateCouponsUseRecord(couponsCode, orderNo);
				}
			}
		}
		
	}

	@Override
	public void AliReturn(HttpServletRequest request) {
		AliPayUtil alipay = new AliPayUtil();
		Map<String,Object> result = alipay.ReturnResult(request);
		if(result!=null){
			String orderNo = result.get("orderNo").toString();
			String endtime = result.get("endtime").toString();
			String tradeStatus = result.get("trade_status").toString();
			double total_amount = Double.valueOf(result.get("total_amount").toString());
			Integer status = ordermapper.getOrderPayStatusByOrderNo(orderNo);
			if(status==0){	//在订单未支付的情况下操作
				double amountPayable = ordermapper.getTotalByOrderNo(orderNo);
				if(total_amount!=amountPayable){
					throw new RuntimeException("应付金额和实际支付金额不相符");
				}
				ordermapper.UpdatePayResult(orderNo, endtime, tradeStatus, "1");   //1默认为支付宝支付
				String couponsCode=ordermapper.getCouponsByOrderNo(orderNo);
				if(!"".equals(couponsCode)) {
					updateCouponsUseRecord(couponsCode, orderNo);
				}
			}
		}
	}
	//如果订单使用了优惠券，若是普通优惠券，该卷作废；若是员工码，增加使用记录
	private void updateCouponsUseRecord(String couponsCode,String orderNo) {
		String reg="[a-z||A-Z]*95";
		if(couponsCode.matches(reg)) {//员工码
			Double orderMoney=ordermapper.getOrderMoneyByOrderNo(orderNo);
			EmpCodeRecord record=new EmpCodeRecord();
			record.setCode(couponsCode);
			record.setMoney(orderMoney);
			record.setUsedate(new Date());
			couponsService.insertNewEmpCodeRecode(record);
		}else {//修改优惠码状态为过期
			couponsService.updateCouponsCdeStatus(couponsCode);
		}
	}
}
