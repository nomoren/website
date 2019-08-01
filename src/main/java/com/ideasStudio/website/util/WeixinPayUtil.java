package com.ideasStudio.website.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSONObject;
import com.ideasStudio.website.config.WeixinConfig;
/**
 * 微信支付工具类
 * @author Administrator
 *
 */
public class WeixinPayUtil{

	/**
	 * 发起微信支付请求
	 * @param req	请求对象
	 * @param orderNo	订单号
	 * @param body	订单描述
	 * @param money	金额  double
	 * @return	返回生成的二维码图片
	 * @throws UnsupportedOperationException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public BufferedImage UnifiedOrder(
			HttpServletRequest req,
			String orderNo,String body,
			String money) 
					throws UnsupportedOperationException, ClientProtocolException, IOException{
		Map<String,Object> map = new HashMap<String,Object>();		
		map.put("appid", WeixinConfig.appid);						//公众账号ID
		map.put("mch_id",WeixinConfig.mch_id);						//商户号ID
		map.put("device_info", WeixinConfig.device_info);			//设备号
		map.put("notify_url", WeixinConfig.notify_url);				//异步通知地址
		map.put("trade_type", WeixinConfig.trade_type);				//交易类型
		map.put("out_trade_no", orderNo);							//商户订单号
		map.put("body", body);										//商品描述
		map.put("total_fee", String.valueOf(Math.round(Double.valueOf(money) * 100)));								//金额  单位分
		map.put("spbill_create_ip",OtherUtil.getRemortIp(req));		//终端IP
		map.put("nonce_str", StringUtil.getRandomString(30));		//随机字符串
		map.put("sign", getSign(map));								//数字签名
		String xml = XmlUtil.genXml(map);
		InputStream input = HttpClientUtil.SendXMLDataByPost(WeixinConfig.UnifiedOrder_url, xml).getEntity().getContent();	//发送xml消息
		String code_url = getElementValue(input, "code_url");
		return Generatecode.Content(code_url);
	}
	
	/**
	 * 发起微信支付请求
	 * @param req	请求对象
	 * @param orderNo	订单号
	 * @param body	订单描述
	 * @param money	金额  double
	 * @return	返回生成跳转链接
	 * @throws UnsupportedOperationException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String UnifiedOrderMobile(
			HttpServletRequest req,
			String orderNo,String body,
			String money) 
					throws UnsupportedOperationException, ClientProtocolException, IOException{
		Map<String,Object> map = new HashMap<String,Object>();		
		map.put("appid", WeixinConfig.appid);						//公众账号ID
		map.put("mch_id",WeixinConfig.mch_id);						//商户号ID
		map.put("device_info", WeixinConfig.device_info);			//设备号
		map.put("notify_url", WeixinConfig.notify_url);				//异步通知地址
		map.put("trade_type", WeixinConfig.trade_type_mobile);		//交易类型
		map.put("out_trade_no", orderNo);							//商户订单号
		map.put("body", body);										//商品描述
		map.put("total_fee", String.valueOf(Math.round(Double.valueOf(money) * 100)));								//金额  单位分
		map.put("spbill_create_ip",OtherUtil.getRemortIp(req));		//终端IP
		map.put("nonce_str", StringUtil.getRandomString(30));		//随机字符串
		map.put("scene_info", getScene_info());						//加入scene_info参数
		map.put("sign", getSign(map));								//数字签名
		String xml = XmlUtil.genXml(map);
		InputStream input = HttpClientUtil.SendXMLDataByPost(WeixinConfig.UnifiedOrder_url, xml).getEntity().getContent();	//发送xml消息
		String code_url = getElementValue(input, "mweb_url");
		return code_url;
	}
	
	/**
	 * 查询订单状态 通过微信的订单号查询
	 * @param transactionId  微信的订单号
	 * @return	返回查询到的结果
	 * @throws UnsupportedOperationException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public boolean QueryOrderStatusBytransactionId(String transactionId) throws UnsupportedOperationException, ClientProtocolException, IOException{
		Map<String,Object> map = new HashMap<String,Object>();		
		map.put("appid", WeixinConfig.appid);						//公众账号ID
		map.put("mch_id",WeixinConfig.mch_id);
		map.put("transaction_id",transactionId);
		map.put("nonce_str", StringUtil.getRandomString(30));		//随机字符串
		map.put("sign", getSign(map));
		String xml = XmlUtil.genXml(map);
		InputStream input = HttpClientUtil.SendXMLDataByPost(WeixinConfig.QueryOrder_url, xml).getEntity().getContent();	//发送xml消息
		String resultCode = getElementValue(input, "trade_state");
		if("SUCCESS".equals(resultCode)){
			return true;	//支付成功
		}else {
			return false;	//支付失败
		}
	}
	
	/**
	 * 查询订单状态 通过商户的订单号查询
	 * @param transactionId  微信的订单号
	 * @return	返回查询到的结果
	 * @throws UnsupportedOperationException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public boolean QueryOrderStatusByOrderNo(String orderNo) throws UnsupportedOperationException, ClientProtocolException, IOException{
		Map<String,Object> map = new HashMap<String,Object>();		
		map.put("appid", WeixinConfig.appid);						//公众账号ID
		map.put("mch_id",WeixinConfig.mch_id);
		map.put("out_trade_no",orderNo);
		map.put("nonce_str", StringUtil.getRandomString(30));		//随机字符串
		map.put("sign", getSign(map));
		String xml = XmlUtil.genXml(map);
		InputStream input = HttpClientUtil.SendXMLDataByPost(WeixinConfig.QueryOrder_url, xml).getEntity().getContent();	//发送xml消息
		String resultCode = getElementValue(input, "trade_state");
		if("SUCCESS".equals(resultCode)){
			return true;	//支付成功
		}else {
			return false;	//支付失败
		}
	}
	
	/**
	 * 通过商户的订单号关闭一比交易
	 * @param orderNo 商户的订单号
	 * @return 返回关闭的结果	
	 * @throws UnsupportedOperationException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public boolean CloseOrderByOrderNo(String orderNo) throws UnsupportedOperationException, ClientProtocolException, IOException{
		Map<String,Object> map = new HashMap<String,Object>();		
		map.put("appid", WeixinConfig.appid);						//公众账号ID
		map.put("mch_id",WeixinConfig.mch_id);
		map.put("out_trade_no",orderNo);
		map.put("nonce_str", StringUtil.getRandomString(30));		//随机字符串
		map.put("sign", getSign(map));
		String xml = XmlUtil.genXml(map);
		InputStream input = HttpClientUtil.SendXMLDataByPost(WeixinConfig.CloseOrder_url, xml).getEntity().getContent();	//发送xml消息
		String resultCode = getElementValue(input, "result_code");
		if("SUCCESS".equals(resultCode)){
			return true;	//支付成功
		}else {
			return false;	//支付失败
		}
	}
	
	/**
	 * 申请退款
	 * @param orderNo		商户订单号
	 * @param total			该笔交易总金额
	 * @param refund		此次退款金额
	 * @return				返回退款结果（有可能已经退款完成，这里没有详细分解）
	 * @throws UnsupportedOperationException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public boolean AppForRefund(String orderNo,String total,String refund) throws UnsupportedOperationException, ClientProtocolException, IOException{
		Map<String,Object> map = new HashMap<String,Object>();		
		map.put("appid", WeixinConfig.appid);						//公众账号ID
		map.put("mch_id",WeixinConfig.mch_id);
		map.put("nonce_str", StringUtil.getRandomString(30));		//随机字符串
		map.put("out_trade_no",orderNo);
		map.put("out_refund_no", StringUtil.getRandomString(30));
		map.put("total_fee", total);
		map.put("refund_fee", refund);
		map.put("sign", getSign(map));
		String xml = XmlUtil.genXml(map);
		String result = HttpClientUtil.sendXMLDataHttpsPost(WeixinConfig.Refund_url, xml, WeixinConfig.mch_id, "D:\\1528784251_20190401_cert\\apiclient_cert.p12");	//发送xml消息
		String resultCode = getElementValueByStr(result, "result_code");
		if("SUCCESS".equals(resultCode)){
			return true;	//支付成功
		}else {
			return false;	//支付失败
		}
	}
	
	/**
	 * 通过商户的订单号关闭一比交易
	 * @param orderNo 商户的订单号
	 * @return 返回关闭的结果	
	 * @throws UnsupportedOperationException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String AppForRefundQuery(String orderNo) throws UnsupportedOperationException, ClientProtocolException, IOException{
		Map<String,Object> map = new HashMap<String,Object>();		
		map.put("appid", WeixinConfig.appid);						//公众账号ID
		map.put("mch_id",WeixinConfig.mch_id);
		map.put("out_trade_no",orderNo);
		map.put("nonce_str", StringUtil.getRandomString(30));		//随机字符串
		map.put("sign", getSign(map));
		String xml = XmlUtil.genXml(map);
		InputStream input = HttpClientUtil.SendXMLDataByPost(WeixinConfig.RefundQuery, xml).getEntity().getContent();	//发送xml消息
		String resultCode = getElementValue(input, "refund_status_0");
		if("SUCCESS".equals(resultCode)){
			return "退款成功";	
		}else if("REFUNDCLOSE".equals(resultCode)){
			return "退款关闭";	
		}else if("PROCESSING".equals(resultCode)){
			return "退款处理中";
		}else {
			return "退款异常";
		}
	}
	
	/**
	 * 异步获取支付结果 
	 * @param req 请求对象
	 * @return 返回支付结果
	 */
	public SortedMap<Object,Object> NotityResult(HttpServletRequest req){
		String args = RequestArgs.getRequestArgs(req);		//获取请求参数  装换为map
		SortedMap<Object,Object> m = NotifyDispose(args);
		//微信支付的API秘钥
		String key = WeixinConfig.key;
		if(isTenpaySign("UTF-8",m,key)){
			if("SUCCESS".equals((String)m.get("result_code"))){
				return m;		//异步返回支付成功 返回true
			}else{
				return null;		//异步返回支付失败 返回false
			}
		}else{
			throw new RuntimeException("验证签名未通过");
		}
	}
	
	/**
	 * 将读取到的内容转换为一个map
	 * @param str  读取到的内容的字符串
	 * @return	返回一个排序map
	 */
	private SortedMap<Object,Object> NotifyDispose(String str){
		Map<String,String> m = new HashMap<String,String>();
		try {
			m=XmlUtil.doXMLParse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
		Iterator<String> it = m.keySet().iterator();
		while(it.hasNext()){
			String parameter = it.next();
			String parameterValue = m.get(parameter);
			String v = "";
			if(null!=parameterValue){
				v = parameterValue.trim();
			}
			packageParams.put(parameter, v);
		}
		return packageParams;
	}
	
	/**
	 * 微信支付签名算法sign
	 */
	private String getSign(Map<String,Object> map){
		StringBuffer sb  = new StringBuffer();
		String[] keyArr = (String[])map.keySet().toArray(new String[map.keySet().size()]);
		Arrays.sort(keyArr);
		for (int i = 0 , size=keyArr.length ; i<size ; ++i) {
			if("sign".equals(keyArr[i])){
				continue;
			}
			sb.append(keyArr[i] + "="+map.get(keyArr[i]) + "&");
		}
		sb.append("key=" + WeixinConfig.key);
		String sign =MD5util.getMD5(sb.toString());
		return sign;
	}
	
	/**
	 * 通过返回IO流获取支付地址
	 * @param input	
	 * @param key
	 * @return
	 */
	private String getElementValue(InputStream input,String key){
		SAXReader reader  = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Element root = doc.getRootElement();
		List<Element> childElements = root.elements();
		for (Element child : childElements) {
			System.out.println("支付返回状态信息:"+child.getName()+":"+child.getStringValue());
			if(key.equals(child.getName())){
				return child.getStringValue();
			}
		}
		return null;
	}
	
	/**
	 * 通过返回字符串获取支付地址
	 * @param input	
	 * @param key
	 * @return
	 */
	private String getElementValueByStr(String str,String key){
		SAXReader reader  = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(new ByteArrayInputStream(str.getBytes("UTF-8")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Element root = doc.getRootElement();
		List<Element> childElements = root.elements();
		for (Element child : childElements) {
			if(key.equals(child.getName())){
				return child.getStringValue();
			}
		}
		return null;
	}
	
	/**
	 * 是否签名正确 规则是按照参数名称a-z排序，遇到空值的参数不参加签名
	 */
	private boolean isTenpaySign(String characterEncoding,SortedMap<Object,Object> packageParams,String API_KEY){
		StringBuffer sb =new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()){
			Map.Entry entry =(Map.Entry) it.next();
			String k =(String) entry.getKey();
			String v = (String) entry.getValue();
			if(!"sign".equals(k) && null!= v && !"".equals(v)){
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key="+API_KEY);
		//算出摘要
		String mysign = MD5util.getMD5(sb.toString(),characterEncoding).toLowerCase();
		String tenPaySign =((String) packageParams.get("sign")).toLowerCase();
		return tenPaySign.equals(mysign);
	}
	
	/**
	 * 获取H5支付Scene_info
	 * @return
	 */
	public static String getScene_info() {
//	{"h5_info": {"type":"IOS","app_name": "王者荣耀","package_name": "com.tencent.tmgp.sgame"}}	
		Map<String,String> h5_info = new LinkedHashMap<>();
		h5_info.put("type", "Wap");
		h5_info.put("wap_url", "http://www.ideas-tudio.com");
		h5_info.put("wap_name", "爱得司精品挂画");
		
		String h5_info_str =JSONObject.toJSON(h5_info).toString();
		Map<String,String> scene_info = new HashMap<>();
		scene_info.put("h5_info", h5_info_str);
		String scene_info_str =JSONObject.toJSON(scene_info).toString();
		return scene_info_str;
	}
}
