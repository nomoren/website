package com.ideasStudio.website.util;
import java.util.Random;


import java.security.MessageDigest;



import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.ideasStudio.website.service.ex.ServiceException;

import net.sf.json.JSONObject;

/**
 * 发送验证码工具类
 */
public class SentMessage {
	//阿里云产品名称
    public static final String product = "Dysmsapi";
    //阿里云产品域名
    public static final String domain = "dysmsapi.aliyuncs.com";
    public static final String accessKeyId = "LTAIkYzDdZ8N4Ws4";
    public static final String accessKeySecret = "NYFxXx8TOKCHDLnzpTBctV9eMzeWer";
    public static final String SignName="禾富集团";
    public static final String TemplateCode="SMS_162445553";
	//验证码过期时间，5分钟
	public 	static final long TIMEOUT=5*60*1000L;
	/**
	 * 发送验证码
	 * @param phone 要发送的手机号码
	 * @return 返回发送的验证码
	 * @throws Exception
	 */
	public static String sent(String phone) throws Exception {
		DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        String code=getCode();
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("SignName", SignName);
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("TemplateCode", TemplateCode);
        request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");
        JSONObject json=null;
        try {
            CommonResponse response = client.getCommonResponse(request);
            json=JSONObject.fromObject(response.getData());
            if(!"OK".equals(json.getString("Message"))){
        	   throw new ServiceException();
           }
        } catch (Exception e) {
            throw e;
        } 
        return code;
	}
	
	//生成6位随机数
	private static String getCode() {
		StringBuffer sb=new StringBuffer();
		Random random = new Random();
		for (int i=0;i<6;i++){
			sb.append(random.nextInt(10));
		}
		return "jiguanhua";
	}
	public static void main(String[] args) throws Exception {
		String code=sent("13058354585");
		System.out.println(code);
		
	}
}

