package com.ideasStudio.website.util;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Transparency;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 切图工具类，提供了两种切图的方法，一种矩形切图，一种圆形切图
 * @author Administrator
 *
 */
public class CutImg extends Panel{
	
	private static final long serialVersionUID = 1L;

	/**
     * 分割矩形图像
     * 
     * @param image
     *            传入的图片对象
     * @param x
     *            开始裁剪位置的X坐标
     * @param y
     *            开始裁剪位置的Y坐标
     * @param width
     *            每次裁剪的图片宽度
     * @param height
     *            每次裁剪的图片高度
     * @param component
     *            容器对象，目的是用来创建裁剪后的每个图片对象
     * @return 返回裁剪之后的图片
     */
    public Image cutRectImage(Image image, int x,
            int y, int width, int height) {
            ImageFilter filter = new CropImageFilter(x, y, width, height);
            Image cutimg = this.createImage(new FilteredImageSource(
                    image.getSource(), filter));
            return cutimg;     
    }
    
    /**
     * 分割圆形图像
     * @param bufimage 要切割的图像
     * @param x			切割X坐标
     * @param y			切割Y坐标
     * @return			返回切割好的图像	
     */
    public static BufferedImage cutCircleImage(BufferedImage bufimage,Double x,Double y){
 
        /**
         * 取一张和原图一样大小画布
         */
        BufferedImage image = new BufferedImage(bufimage.getWidth(), bufimage.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        /**
         * 以画图的左上角为顶点X,Y 画图宽高为参数画一个椭圆
         */
        Ellipse2D.Double shape = new Ellipse2D.Double(x, y, bufimage.getWidth(), bufimage.getHeight());
        
        /**
         * 设置背景图层透明
         */
        Graphics2D g2 = image.createGraphics();
        image = g2.getDeviceConfiguration().createCompatibleImage(bufimage.getWidth(), bufimage.getHeight(), Transparency.TRANSLUCENT);
        g2.dispose();
        g2 = image.createGraphics();
        /**
         * 画椭圆的形状
         */
        g2.setClip(shape);
        g2.drawImage(bufimage, 0, 0, null);
        return image;
    }
    
    public static void main(String[] args) throws IOException {
		BufferedImage image = ImageIO.read(new File("d:/1.jpg"));
		BufferedImage result = cutCircleImage(image,.0,.0);
		ImageIO.write(result, "png", new File("d:/3.png"));
	}
}
