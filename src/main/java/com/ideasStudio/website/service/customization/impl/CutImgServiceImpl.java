package com.ideasStudio.website.service.customization.impl;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.ideasStudio.website.entity.CustPiece;
import com.ideasStudio.website.entity.CutImgArg;
import com.ideasStudio.website.entity.RectangularCut;
import com.ideasStudio.website.entity.ResponseResult;
import com.ideasStudio.website.service.customization.CutImgService;
import com.ideasStudio.website.util.CreateOrderNumber;
import com.ideasStudio.website.util.CutImg;
import com.ideasStudio.website.util.Optimumjpeg;

/**
 * 矩形切图方法的业务层实现
 * @author Administrator
 */
@Service("cutimgservice")
public class CutImgServiceImpl extends Container implements CutImgService {
	
	static{
		System.setProperty("java.awt.headless", "true");
	}
	
	private static final long serialVersionUID = 1L;
	/**
	 * 存储矩形XY和width和height的集合
	 */
	private List<RectangularCut> Rects = null;
	/**
	 * 存储裁剪好的图片的集合
	 */
	private List<BufferedImage> images = new ArrayList<BufferedImage>();
	
	
	@Override
	public ResponseResult<CustPiece> CutImages(Image image, CutImgArg arg,Integer piece,Integer shape,HttpServletRequest request) {
		System.setProperty("java. awt.headless", "true");
		CutImg cutimg = new CutImg();
		Image imgRect = null;
		Image circle = null;
		imgRect = cutimg.cutRectImage(image, arg.getDataX(), arg.getDataY(), arg.getDataWidth(), arg.getDataHeight());
		if(shape==7){
			circle = cutimg.cutCircleImage(ImageTOBufferedImage(imgRect,arg.getDataWidth(), arg.getDataHeight()), .0, .0);
		}
		/*** 首先根据传入的参数把矩形框确定出来*/
		Rects = InitCutArg(arg,shape);		//初始化下参数
		/*** 这里循环将每张图切出来 切出来之后放到一个集合当中*/
		BufferedImage thumbnail = new BufferedImage(arg.getDataWidth(),arg.getDataHeight(),BufferedImage.TYPE_INT_RGB);    //缓冲区
		Graphics2D gs = thumbnail.createGraphics();
		thumbnail = gs.getDeviceConfiguration().createCompatibleImage(arg.getDataWidth(),arg.getDataHeight(), Transparency.TRANSLUCENT);
		gs.dispose();
		gs = thumbnail.createGraphics();
		for (RectangularCut rr : Rects) {
			Image img = 
					cutimg.cutRectImage(imgRect, Math.round(rr.getX()),Math.round(rr.getY()),Math.round(rr.getWidth()),Math.round(rr.getHeight()));
			images.add(ImageTOBufferedImage(img,Math.round(rr.getWidth()),Math.round(rr.getHeight())));
			gs.drawImage(img, Math.round(rr.getX()), Math.round(rr.getY()),null);
		}
		if(shape==7){
			gs.drawImage(circle, 0, 0,null);
		}
		String ordernumber = CreateOrderNumber.GenerateOrderNumber();
		SaveImgs(images,ordernumber,request);/***保存剪切好的其他的图片的方法*/
		String path = getPath(SaveImg(thumbnail,ordernumber,piece,request));
		CustPiece cust = new CustPiece(piece,path);
		ResponseResult<CustPiece> custpiece  = new ResponseResult<CustPiece>();
		cust.setOrdernumber(ordernumber);
		cust.setPath(path);
		custpiece.setData(cust);
		return custpiece;
	}
	
	/**
	 * 将图片从Image转换为BufferedImage的方法
	 * @param image java.awt.Image类型的图片
	 * @param width 转换后的宽度
	 * @param height 转换后的高度
	 * @return 返回转换好的BufferedImage 图片
	 */
	private BufferedImage ImageTOBufferedImage(Image image,Integer width,Integer height){
		BufferedImage bufimg = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
		 Graphics g = bufimg.getGraphics(); 
		MediaTracker t = new MediaTracker(this); 
		t.addImage(image, 0); 
		try {
			t.waitForAll();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} 
		g.drawImage(image, 0, 0, null); 
		return bufimg;
	}
	
	@Override
	public ResponseResult<CustPiece> CutImage(Image image, CutImgArg arg,String ordernumber,Integer piece,HttpServletRequest request) {
			CutImg cutimg = new CutImg();
			ResponseResult<CustPiece> custpiece  = new ResponseResult<CustPiece>();
			/**
			 * 首先根据传入的参数把矩形框确定出来
			 */
			Image imgRect = cutimg.cutRectImage(image, arg.getDataX(), arg.getDataY(), arg.getDataWidth(), arg.getDataHeight());
			/**
			 * 这里循环将每张图切出来 切出来之后放到一个集合当中
			 */
			BufferedImage result = new BufferedImage(arg.getDataWidth(),arg.getDataHeight(),BufferedImage.TYPE_INT_RGB);    //缓冲区
			Graphics  gs = result.getGraphics();
			MediaTracker t = new MediaTracker(this); 
			t.addImage(imgRect, 0); 
			try {
				t.waitForAll();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			gs.drawImage(imgRect, 0, 0, null);
			if(ordernumber==""||ordernumber.length()<=0){
				ordernumber = CreateOrderNumber.GenerateOrderNumber();
			}
			String pathurls = getPath(SaveImg(result,ordernumber,piece,request));
			CustPiece cust = new CustPiece(piece,pathurls);
			cust.setOrdernumber(ordernumber);
			custpiece.setData(cust);
		return custpiece;
	}
	
	/**
	 * 通过绝对路径取出相对路径
	 * @param path  绝对路径
	 * @return	相对路径
	 */
	public String getPath(String path){
		if(path.contains("\\website-v2")){
			return path.substring(path.indexOf("\\website-v2"));
		}else{
			return path.substring(path.indexOf("/cust"));   
		}
	}
	
	/**
	 * 保存单张图的方法
	 * @param image	 单张图
	 * @param ordernumber 订单文件夹号
	 * @param piece 张数
	 * @return	返回保存的绝对路径
	 * @throws IOException 
	 */
	private String SaveImg(BufferedImage image,String ordernumber,Integer piece,HttpServletRequest request){
			String custfolder= request.getServletContext().getRealPath("customization");
			// 创建cust文件夹
			File cust = new File(custfolder);// 确保文件夹存在
			if (!cust.exists()) {
				cust.mkdirs();
			}
			//根据订单号创建对应的文件夹
			File orderfolder = new File(custfolder+"/"+ordernumber);
			if(!orderfolder.exists()){
				orderfolder.mkdirs();
			}
			File img = null;
			if(piece==0){
				img= new File(custfolder+"/"+ordernumber+"/index.png");
			}else{
				img= new File(custfolder+"/"+ordernumber+"/"+piece+".png");
			}
			if(!img.exists()){
				try {
					img.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				ImageIO.write(image, "png", img);
			} catch (IOException e) {
				throw new RuntimeException("写出图片出错");
			}
		return img.getAbsolutePath();
	}
	
	
	/**
	 * 保存一组剪切好的图片	
	 * @param images 一组剪切好的图片	
	 * @param ordernumber 订单文件夹好
	 * @return	用集合返回所有保存好的图片的路径
	 */
	private List<String> SaveImgs(List<BufferedImage> images,String ordernumber,HttpServletRequest request){
		if(images.size()<=0)return null;		//如果为空则不执行
		List<String> paths = new ArrayList<String>();
			String custfolder= request.getServletContext().getRealPath("customization");
			// 创建cust文件夹
			File cust = new File(custfolder);// 确保文件夹存在
			if (!cust.exists()) {
				cust.mkdirs();
			}
			//根据订单号创建对应的文件夹
			File orderfolder = new File(custfolder+"/"+ordernumber);
			if(!orderfolder.exists()){
				orderfolder.mkdirs();
			}
		for (int i=0;i<images.size(); i++) {
			File img = new File(custfolder+"/"+ordernumber+"/"+(i+1)+".png");
			if(!img.exists()){
				try {
					img.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				ImageIO.write(images.get(i), "png", img);
				paths.add(img.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//这一次的裁剪完毕清空集合中存储的图片
		
		images.clear();
		return paths;
	}
	/**
	 * 根据传入的裁剪参数计算裁剪的矩形框的坐标和大小
	 * @param arg 裁剪参数
	 * @return	返回对应的矩形框的大小
	 */
	private List<RectangularCut> InitCutArg(CutImgArg arg,Integer shape){
		List<RectangularCut> Rects=new ArrayList<RectangularCut>();
		if(shape==2||shape==1||shape==8){Rects.add(new RectangularCut(.0f,.0f,1.0f,1.0f));}
		if(shape==3){
			Rects.add(new RectangularCut(.0f,0.2f,0.13513f,0.6f));
			Rects.add(new RectangularCut(0.14865f,0.1f,0.2027f,0.8f));
			Rects.add(new RectangularCut(0.36486f,.0f,0.27027f,1.0f));
			Rects.add(new RectangularCut(0.64865f,0.1f,0.2027f,0.8f));
			Rects.add(new RectangularCut(0.86486f,0.2f,0.13513f,0.6f));
		}
		if(shape==4){
			Rects.add(new RectangularCut(.0f,.0f,0.15385f,0.23256f));
			Rects.add(new RectangularCut(0.16923f,.0f,0.15385f,0.23256f));
			Rects.add(new RectangularCut(0.33846f,.0f,0.15385f,0.23256f));
			Rects.add(new RectangularCut(0.50769f,.0f,0.15385f,0.23256f));
			Rects.add(new RectangularCut(0.67692f,.0f,0.15385f,0.23256f));
			Rects.add(new RectangularCut(0.84615f,.0f,0.15385f,0.23256f));
			Rects.add(new RectangularCut(.0f,0.25581f,0.15385f,0.23256f));
			Rects.add(new RectangularCut(0.16923f,0.25581f,0.15385f,0.23256f));
			Rects.add(new RectangularCut(0.33846f,0.25581f,0.15385f,0.23256f));
			Rects.add(new RectangularCut(0.50769f,0.25581f,0.15385f,0.23256f));
			Rects.add(new RectangularCut(0.67692f,0.25581f,0.15385f,0.23256f));
			Rects.add(new RectangularCut(0.84615f,0.25581f,0.15385f,0.23256f));
			Rects.add(new RectangularCut(.0f,0.51163f,0.15385f,0.23256f));
			Rects.add(new RectangularCut(0.16923f,0.51163f,0.15385f,0.23256f));
			Rects.add(new RectangularCut(0.33846f,0.51163f,0.15385f,0.23256f));
			Rects.add(new RectangularCut(0.50769f,0.51163f,0.15385f,0.23256f));
			Rects.add(new RectangularCut(0.67692f,0.51163f,0.15385f,0.23256f));
			Rects.add(new RectangularCut(0.84615f,0.51163f,0.15385f,0.23256f));
			Rects.add(new RectangularCut(.0f,0.76744f,0.15385f,0.23256f));
			Rects.add(new RectangularCut(0.16923f,0.76744f,0.15385f,0.23256f));
			Rects.add(new RectangularCut(0.33846f,0.76744f,0.15385f,0.23256f));
			Rects.add(new RectangularCut(0.50769f,0.76744f,0.15385f,0.23256f));
			Rects.add(new RectangularCut(0.67692f,0.76744f,0.15385f,0.23256f));
			Rects.add(new RectangularCut(0.84615f,0.76744f,0.15385f,0.23256f));
		}
		if(shape==5){
			Rects.add(new RectangularCut(.0f,.0f,0.32258f,1.0f));
			Rects.add(new RectangularCut(0.33871f,.0f,0.32258f,1.0f));
			Rects.add(new RectangularCut(0.67742f,.0f,0.32258f,1.0f));
		}
		if(shape==6){
			Rects.add(new RectangularCut(.0f,.0f,0.32467f,1.0f));
			Rects.add(new RectangularCut(0.33766f,.0f,0.32467f,1.0f));
			Rects.add(new RectangularCut(0.67534f,.0f,0.32467f,1.0f));
		}
		
		/**
		 * 根据矩形所占的比例算出它占的像素
		 */
		for (int i = 0; i < Rects.size(); i++) {
			RectangularCut rect = Rects.get(i);
			RectangularCut rr = new RectangularCut();
			rr.setX(arg.getDataWidth()*rect.getX());
			rr.setY(arg.getDataHeight()*rect.getY());
			rr.setWidth(arg.getDataWidth()*rect.getWidth());
			rr.setHeight(arg.getDataHeight()*rect.getHeight());
			Rects.set(i, rr);
		}
		return Rects;
	}
	
	/**
	 * 生成不可重复的订单号码
	 * @return 返回生成的不重复订单号码
	 */
	private String createordernumber(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhh");
		int hashCodeV=UUID.randomUUID().toString().hashCode();
		if(hashCodeV<0) {hashCodeV =-hashCodeV;}
		String randomnum = String.format("%d", hashCodeV);
		return sdf.format(new Date())+randomnum;//因为还没有订单号，预留了一个位置，后期传参就好了
	}
}
