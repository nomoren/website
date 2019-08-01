package com.ideasStudio.website.service.customization.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.springframework.stereotype.Service;

import com.ideasStudio.website.service.customization.ThumbnailService;
import com.ideasStudio.website.util.Base64;
import com.ideasStudio.website.util.Optimumjpeg;

@Service("thumbnail")
public class ThumbnailServiceImpl implements ThumbnailService {

	@Override
	public void getThumbnail(HttpServletResponse response,HttpServletRequest request,
			Integer piece,String ordernumber) {
		/**
		 * 先获取到切图的文件夹路径
		 */
		String CutDirPath= request.getServletContext().getRealPath("customization");
		/**
		 * 在源文件名称的基础上添加缩略图后缀
		 */
		String thumbnail_suffix = "thumbnail";
		
		/**
		 * 获取压缩器
		 */
		Optimumjpeg Compress = new Optimumjpeg();
		
		/**
		 * 利用二进制写出的那张图片
		 */
		
		BufferedImage thumbnail = null;
		
		/**
		 * 获取到需要的那种图片
		 */
		try {
			File oldfile = null;
			if(piece==0){
				oldfile = new File(CutDirPath+"/"+ordernumber+"/index.png");
			}else{
				oldfile = new File(CutDirPath+"/"+ordernumber+"/"+piece+".png");
			}
			if(oldfile.exists()){
				if(piece!=0) {
					File newfile = new File(CutDirPath+"/"+ordernumber+"/"+piece+"_"+thumbnail_suffix+".png");
					if(!newfile.exists()){
						newfile.createNewFile();
					}
					thumbnail = Compress.CompressImage(oldfile, 0.5f, 0.25f, newfile);
				}else {
					File newfile = new File(CutDirPath+"/"+ordernumber+"/0_"+thumbnail_suffix+".png");
					if(!newfile.exists()){
						newfile.createNewFile();
					}
					try {
						ImageInfo imageInfo = Sanselan.getImageInfo(oldfile);
						float compRatio = 0.3f;
						if(imageInfo.getWidth()<=1000){  //判断图的宽度小于1000 就是用0.5的压缩比
							compRatio = 0.5f;
						}
						if(imageInfo.getWidth()>2500&&5000>imageInfo.getWidth()){   //判断图的宽度大于2500就是用0.1的压缩比
							compRatio = 0.20f;
						}
						if(imageInfo.getWidth()>5000){   //判断图的宽度大于2500就是用0.1的压缩比
							compRatio = 0.1f;
						}
						thumbnail = Compress.CompressImage(oldfile, compRatio, 0.15f, newfile);
					} catch (ImageReadException e) {
						e.printStackTrace();
					}
				}
				response.setContentType("image/png");
				ImageIO.write(thumbnail, "png", response.getOutputStream());
				response.flushBuffer();
			}
		} catch (IOException e) {
			/**
			 * 把IOException异常改成RuntimeException异常
			 */
			throw new RuntimeException();
		}
	}

	@Override
	public void generateThumbnail(HttpServletRequest request, String imgStr,
			String ordernumber) {
		String path =request.getServletContext().getRealPath("customization/"+ordernumber+"/index.png");
		Base64 base64 = new Base64();
		base64.generateImage(imgStr, path);
	}
}
