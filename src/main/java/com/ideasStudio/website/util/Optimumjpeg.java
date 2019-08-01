package com.ideasStudio.website.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 图片压缩工具类
 * @author Administrator
 *
 */
public class Optimumjpeg {
	
	/**
	 * 图片压缩方法
	 * @param oldfile  源文件
	 * @param size		压缩之后的大小 .0f~1f 1f为原比例
	 * @param quality	压缩质量 .0f~1f 1f最高质量
	 * @param newfile	压缩之后的新文件
	 * @return	返回压缩之后图片
	 * @throws IOException 抛出IO读取异常
	 */
	public BufferedImage CompressImage(File oldfile,Float size,Float quality,File newfile) throws IOException{
		try {
			Thumbnails.of(oldfile).scale(size).outputQuality(quality).toFile(newfile);
			BufferedImage image = ImageIO.read(newfile);
			return image;
		} catch (IOException e) {
			/**
			 * 读取文件是发生异常，失败
			 */
			throw e;
		}
	}

}
