package com.ideasStudio.website.entity;

/**
 * 支付宝下单从参数
 * @author Administrator
 *
 */
public class AliPayArgs extends BaseEntity{

	private static final long serialVersionUID = 1L;
	private String out_trade_no;		////商户订单号，商户网站订单系统中唯一订单号，必填
	private String total_amount;		////付款金额，必填
	private String subject;				///订单标题，必填
	private String body;				////订单描述
	private String product_code = "FAST_INSTANT_TRADE_PAY";//销售产品码，与支付宝签约的产品码名称。
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	
	
}
