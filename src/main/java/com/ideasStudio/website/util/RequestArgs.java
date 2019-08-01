package com.ideasStudio.website.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

public class RequestArgs {
	
	/**
	 * 充请求对象中获取请求参数
	 * @param request
	 * @return
	 */
	public static String getRequestArgs(HttpServletRequest request){
		StringBuffer sb = new StringBuffer();
		try (InputStream input = request.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(input,"utf-8"))){
			String s;
			while((s=in.readLine())!=null){
				sb.append(s);
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return sb.toString();
	}
}
