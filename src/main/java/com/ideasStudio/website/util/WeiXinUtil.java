 package com.ideasStudio.website.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

/**
 * 微信登录工具类
 * @author 赵志斌
 *
 */
public class WeiXinUtil {
	public static final String APPID="wxcb47b22a3a9b69a6";
	public static final String M_APPID="wx16d33dcf69f899ae";
	public static final String APPSECRET="1057500832f46ab00565222f69019e73";
	public static final String M_APPSECRET="c5ca01901cfb1a6a1496986916bf3b91";
	
	/**
	 * 发送请求
	 * @param url 请求链接
	 * @return 返回响应内容
	 */
	public  static JSONObject doGetJson(String url) {
		JSONObject json=null;
		DefaultHttpClient client=new DefaultHttpClient();
		HttpGet get=new HttpGet(url);//get请求
		try {
			HttpResponse response=client.execute(get);//发起get请求
			HttpEntity entity=response.getEntity();//获取响应内容
			if(entity!=null) {
				String result=EntityUtils.toString(entity,"utf-8");
				json=JSONObject.fromObject(result);//把响应内容转换为json对象
			}
			get.releaseConnection();//释放链接
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}
