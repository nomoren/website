package com.ideasStudio.website.util;

import java.security.MessageDigest;

import org.springframework.util.DigestUtils;

public class MD5util {
	
	/**
	 * 获取加密后的密码
	 * 
	 * @param src
	 *            原始密码
	 * @param salt
	 *            盐
	 * @return 加密后的密码
	 * @see #md5(String)
	 */
	public String getEncrpytedRecordIdByUID(String uid, Integer recordId) {
		String ciphertest = uid + recordId;
		String result = this.md5(ciphertest);
		for (int i = 0; i < 5; i++) {
			result = this.md5(result);
		}
		return result;
	}
	
	public String getEncrpytedRecordIdByUUID(String uuid, Integer recordId) {
		String ciphertest = uuid + recordId;
		String result = this.md5(ciphertest);
		for (int i = 0; i < 5; i++) {
			result = this.md5(result);
		}
		return result;
	}

	/**
	 * 使用MD5算法对数据进行加密
	 * 
	 * @param arg
	 *            原文
	 * @return 密文
	 */
	private String md5(String arg) {
		return DigestUtils.md5DigestAsHex(arg.getBytes()).toUpperCase();
	}
	
	
	private static char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

    //生成MD5
    public static String getMD5(String message){
    	if(message==null || message.length()==0){
    		return null;
    	}
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");  // 创建一个md5算法对象
            md.update(message.getBytes("utf-8"));
            byte[] bit = md.digest();
            return byteArrayToHexString(bit);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    
    public static String getMD5(String message,String charsetname){
    	if(message==null || message.length()==0){
    		return null;
    	}
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");  // 创建一个md5算法对象
            if(charsetname==null || "".equals(charsetname)){
            	md.update(message.getBytes());
            }else{
            	md.update(message.getBytes(charsetname));
            }
            byte[] bit = md.digest();
            return byteArrayToHexString(bit);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    
    private static String byteArrayToHexString(byte bit[]){
    	char buf[] = new char[bit.length * 2];
    	int num =0;
    	for (int i = 0; i < bit.length; i++) {
	           byte byte0 = bit[i];
	           buf[num++] = hexDigits[byte0 >>> 4 & 0xf];
	           buf[num++] = hexDigits[byte0 & 0xf];
	        }
	        return new String(buf).toUpperCase();
    }
    
}
