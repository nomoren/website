package com.ideasStudio.website.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * @author Administrator
 *
 */
public class DateUtil {
	
	/**
	 * 生成时间串
	 * @return
	 */
	public static String getCurrentDateStr(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		return sdf.format(date);
	}
	
	
	/**
	 * 生成时间串
	 * @return
	 */
	public static String getDateStr(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddhhmmssSSS");
		return sdf.format(date);
	}
	
	
	public static String reformat(String time){
		try {
			SimpleDateFormat sdf_old = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf_old.parse(time);	
			SimpleDateFormat sdf_new = new SimpleDateFormat("yyyyMMddHHmmss");
			return sdf_new.format(date);
		} catch (ParseException e) {
			throw new RuntimeException("时间格式装换异常");
		}
	}
	
	
	
	
}
