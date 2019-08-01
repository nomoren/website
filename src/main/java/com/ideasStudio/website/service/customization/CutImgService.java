package com.ideasStudio.website.service.customization;

import java.awt.Image;

import javax.servlet.http.HttpServletRequest;

import com.ideasStudio.website.entity.CustPiece;
import com.ideasStudio.website.entity.CutImgArg;
import com.ideasStudio.website.entity.ResponseResult;

/**
 * 切图的业务层接口
 * @author Administrator
 *
 */
public interface CutImgService {
	/**
	 * 切图的方法(一次性切一张图的)
	 * @param image		传图
	 * @param arg		切图参数
	 * @return			返回切图的状态
	 */
	ResponseResult<CustPiece> CutImages(Image image,CutImgArg arg,Integer piece,Integer shape,HttpServletRequest request);
	
	/**
	 * 切图的方法(一次性切多张图的)
	 * @param image		传图
	 * @param arg		切图参数
	 * @return			返回切图的状态
	 */
	ResponseResult<CustPiece> CutImage(Image image,CutImgArg arg,String ordernumber,Integer piece,HttpServletRequest request);
	
}
