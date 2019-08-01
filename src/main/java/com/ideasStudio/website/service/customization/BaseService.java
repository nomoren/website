package com.ideasStudio.website.service.customization;

import javax.servlet.http.HttpSession;

public interface BaseService {

	/**
	 * 从session当中读取UUID
	 * @param session 用户session
	 * @return 返回读取大的uuid
	 */
	String getUUIDforSession(HttpSession session);
	
	/**
	 * 从session当中读取UID
	 * @param session 用户session
	 * @return 返回读取大的uid
	 */
	String getUIDforSession(HttpSession session);
}
