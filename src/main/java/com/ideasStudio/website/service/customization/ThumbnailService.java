package com.ideasStudio.website.service.customization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取图片的缩略图业务层处理方法
 * @author Administrator
 *
 */
public interface ThumbnailService {
	
	/**
	 * 
	 * @param response	响应对象，直接写出二进制流	
	 * @param request 请求对象 获取文件路径
	 * @param piece 请求的第几张缩略图
	 * @param ordernumber 这次请求的订单号
	 */
	void getThumbnail(
			HttpServletResponse response,
			HttpServletRequest request,
			Integer piece,
			String ordernumber);
	
	/**
	 * 通过前端生成的BASE64字符串生成对应的缩略图
	 * @param request  	请求对象，用于获取路径
	 * @param imgStr	前端截图的缩略图，生成的base字符串
	 * @param ordernumber	订单文件夹号码
	 */
	void generateThumbnail(HttpServletRequest request,
			String imgStr,
			String ordernumber);
}
