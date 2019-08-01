package com.ideasStudio.website.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 支付宝支付配置类
 * @author Administrator
 *
 */
@Component
public class AlipayConfig {
	
	public static String app_id;					//支付宝appid
	public static String merchant_private_key;		//商户私钥
	public static String alipay_public_key;		//商户公钥 
	public static String notify_url;				//异步回调地址
	public static String return_url;				//同步回调地址
	public static String request_data_type;		//请求数据类型
	public static String sign_type;				//秘钥类型
	public static String charset;					//字符集
	public static String gatewayUrl;				//支付宝网关
	public static String log_path;					//日志位置
	public static String title = "爱得司精品挂画";
	public static String product_code = "FAST_INSTANT_TRADE_PAY"; //到账方式
	public static String product_code_mobile = "QUICK_WAP_WAY"; //手机网站支付带唤醒
	
	
	public static String getApp_id() {
		return app_id;
	}
	@Value("${aliapp_id}")
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	
	
	public static String getMerchant_private_key() {
		return merchant_private_key;
	}
	@Value("${alimerchant_private_key}")
	public void setMerchant_private_key(String merchant_private_key) {
		this.merchant_private_key = merchant_private_key;
	}
	
	
	public static String getAlipay_public_key() {
		return alipay_public_key;
	}
	@Value("${alialipay_public_key}")
	public void setAlipay_public_key(String alipay_public_key) {
		this.alipay_public_key = alipay_public_key;
	}
	
	
	public static String getNotify_url() {
		return notify_url;
	}
	@Value("${alinotify_url}")
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	
	
	
	public static String getReturn_url() {
		return return_url;
	}
	@Value("${alireturn_url}")
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	
	
	
	public static String getRequest_data_type() {
		return request_data_type;
	}
	@Value("${alirequest_data_type}")
	public void setRequest_data_type(String request_data_type) {
		this.request_data_type = request_data_type;
	}
	
	
	public static String getSign_type() {
		return sign_type;
	}
	@Value("${alisign_type}")
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	
	public static String getCharset() {
		return charset;
	}
	@Value("${alicharset}")	
	public void setCharset(String charset) {
		this.charset = charset;
	}
	
	
	public static String getGatewayUrl() {
		return gatewayUrl;
	}
	@Value("${aligatewayUrl}")
	public void setGatewayUrl(String gatewayUrl) {
		this.gatewayUrl = gatewayUrl;
	}
	
	
	public static String getLog_path() {
		return log_path;
	}
	@Value("${alilog_path}")
	public void setLog_path(String log_path) {
		this.log_path = log_path;
	}
	
	
	public static String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
}

