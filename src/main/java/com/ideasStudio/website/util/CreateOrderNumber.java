package com.ideasStudio.website.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 生成不可重复的订单号码
 * @author Administrator
 *
 */
public class CreateOrderNumber {
	/**
	 * 生成不可重复的订单号码
	 * @return 返回生成的不重复订单号码
	 */
	public static String GenerateOrderNumber(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");
		int hashCodeV=UUID.randomUUID().toString().hashCode();
		if(hashCodeV<0) {hashCodeV =-hashCodeV;}
		String randomnum = String.format("%d", hashCodeV);
		return sdf.format(new Date())+randomnum;
	}
}
