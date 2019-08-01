package com.ideasStudio.website.util;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import com.swetake.util.Qrcode;

/**生成收钱码*/
public class Generatecode {
	static int v=6;   //设置二维码的版本号
	static int width = 514;    //设置显示的宽度  
	static int height = 514;   //设置显示的高度
	static byte[] code ={};
	/**获取要显示的内容*/

	public static BufferedImage Content(String str){
		if(str==null || str.length()==0){
			return null;
		}
			Qrcode x = new Qrcode();     //生成一个二维码对象
			/**
			 * 纠错等级分为
			 * level L : 最大 7% 的错误能够被纠正；
			 * level M : 最大 15% 的错误能够被纠正；
			 * level Q : 最大 25% 的错误能够被纠正；
			 * level H : 最大 30% 的错误能够被纠正；
			 */
			x.setQrcodeErrorCorrect('Q');
			x.setQrcodeEncodeMode('B');//注意版本信息 N代表数字 、A代表 a-z,A-Z、B代表 其他)
			x.setQrcodeVersion(v);//版本号  1-40
			int pixoff =32;   //设置偏移量
			int Pixelsize =11; //设置像素大小
			try{
				code =str.getBytes("utf-8");    //把内容装换byte类型。注意要进行异常捕获
			}catch(Exception e) {
				throw new RuntimeException("获取byte数组异常");
			}
			BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);    //缓冲区
			Graphics2D gs = image.createGraphics();   //绘图
			
			gs.setBackground(Color.white);     //背景色为白色
			gs.setColor(Color.BLACK);    //内容色为黑色
			gs.clearRect(0, 0, width, height);   //设置宽度高度
			if(code.length>0&&code.length<120) {
				boolean[][] num = x.calQrcode(code);   //将内容装换成二位数组
				 for (int i = 0; i < num.length; i++) {
		                for (int j = 0; j < num.length; j++) {
		                    if (num[j][i]) {
		                        gs.fillRect(j * Pixelsize + pixoff, i * Pixelsize + pixoff, Pixelsize, Pixelsize);
		                    }
		                }
		            }
			}
			gs.dispose();
			image.flush();   
			return image;
	}	
}
