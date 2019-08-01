package com.ideasStudio.website.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 微信支付配置类
 * @author Administrator
 *
 */
@Component
public class WeixinConfig {
	
	public static String appid;  			//公众号ID
	public static String mch_id;			//商户号ID
	public static String device_info;		//设备号
	public static String UnifiedOrder_url; //统一下单url
	public static String QueryOrder_url;  	//查询订单状态url
	public static String CloseOrder_url;	//关闭订单url
	public static String Refund_url;		//申请退款url
	public static String RefundQuery;		//退款查询url
	public static String notify_url;	    //通知回调地址
	public static String key;	 		    //商户的key【API秘钥】
	public static String trade_type = "NATIVE";  //MWEB	   //支付方式  ‘NATIVE’  扫码支付
	public static String trade_type_mobile="MWEB";
	public static String title = "爱得司精品挂画";		   
	
	public static String getAppid() {
		return appid;
	}
	public static String getMch_id() {
		return mch_id;
	}
	public static String getDevice_info() {
		return device_info;
	}
	public static String getUnifiedOrder_url() {
		return UnifiedOrder_url;
	}
	public static String getQueryOrder_url() {
		return QueryOrder_url;
	}
	public static String getCloseOrder_url() {
		return CloseOrder_url;
	}
	public static String getRefund_url() {
		return Refund_url;
	}
	public static String getRefundQuery() {
		return RefundQuery;
	}
	public static String getNotify_url() {
		return notify_url;
	}
	public static String getKey() {
		return key;
	}
	
	@Value("${wxappid}")
	public void setAppid(String appid) {
		this.appid = appid;
	}
	
	@Value("${wxmch_id}")
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	
	@Value("${wxdevice_info}")
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}
	
	@Value("${wxUnifiedOrder_url}")
	public void setUnifiedOrder_url(String unifiedOrder_url) {
		this.UnifiedOrder_url = unifiedOrder_url;
	}
	
	@Value("${wxQueryOrder_url}")
	public void setQueryOrder_url(String queryOrder_url) {
		this.QueryOrder_url = queryOrder_url;
	}
	
	@Value("${wxCloseOrder_url}")
	public void setCloseOrder_url(String closeOrder_url) {
		this.CloseOrder_url = closeOrder_url;
	}
	@Value("${wxRefund_url}")
	public void setRefund_url(String refund_url) {
		this.Refund_url = refund_url;
	}
	
	@Value("${wxRefundQuery}")
	public void setRefundQuery(String refundQuery) {
		this.RefundQuery = refundQuery;
	}
	
	@Value("${wxnotify_url}")
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	
	@Value("${wxkey}")
	public void setKey(String key) {
		this.key = key;
	}
	
	
	public static String toStr() {
		return "WeixinConfig2 [appid=" + appid + ", mch_id=" + mch_id + ", device_info=" + device_info
				+ ", UnifiedOrder_url=" + UnifiedOrder_url + ", QueryOrder_url=" + QueryOrder_url + ", CloseOrder_url="
				+ CloseOrder_url + ", Refund_url=" + Refund_url + ", RefundQuery=" + RefundQuery + ", notify_url="
				+ notify_url + ", key=" + key + ", trade_type=" + trade_type + "]";
	}
	
	
}
