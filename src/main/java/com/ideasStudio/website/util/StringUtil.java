package com.ideasStudio.website.util;

import java.util.Random;

public class StringUtil {
	
	/**
	 * 生成随机字符串
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length){
		Random rand = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i <length; i++) {
			int number = rand.nextInt(2);
			long result = 0;
			switch(number){
			case 0:
					result = Math.round(Math.random() * 25 +65);
					sb.append(String.valueOf((char)result));
					break;
			case 1:
				sb.append(String.valueOf(new Random().nextInt(10)));
				break;
			}
		}
		return sb.toString();
	}
	
	/**
	 * 生成随机5位密码，加前缀
	 * @param length
	 * @return
	 */
	public static String getRandomPassword(){
		Random rand = new Random();
		StringBuffer sb = new StringBuffer();
		sb.append("IDEAS");
		for (int i = 0; i <5; i++) {
			int number = rand.nextInt(2);
			long result = 0;
			switch(number){
			case 0:
					result = Math.round(Math.random() * 25 +65);
					sb.append(String.valueOf((char)result));
					break;
			case 1:
				sb.append(String.valueOf(new Random().nextInt(10)));
				break;
			}
		}
		return sb.toString();
	}
}
