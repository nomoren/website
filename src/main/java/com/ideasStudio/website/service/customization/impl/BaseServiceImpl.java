package com.ideasStudio.website.service.customization.impl;

import javax.servlet.http.HttpSession;

import com.ideasStudio.website.service.customization.BaseService;

public class BaseServiceImpl implements BaseService{
	
	/**
	 * 从session当中读取UUID
	 * @param session 用户session
	 * @return 返回读取大的uuid
	 */
	public  String getUUIDforSession(HttpSession session){
		String uuid = "";
		Object uuidobj = session.getAttribute("uuid");
		if(uuidobj!=null){ uuid = uuidobj.toString(); }
		return uuid;
	}
	/**
	 * 从session当中读取UID
	 * @param session 用户session
	 * @return 返回读取大的uid
	 */
	public String getUIDforSession(HttpSession session){
		String uid = "";
		Object uidobj = session.getAttribute("uid");
		if(uidobj!=null){ uid = uidobj.toString(); }
		return uid;
	}
}
