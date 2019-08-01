package com.ideasStudio.website.service.customization.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideasStudio.website.config.BackupsPaths;
import com.ideasStudio.website.entity.CustCart;
import com.ideasStudio.website.entity.CustCartArg;
import com.ideasStudio.website.entity.Shape;
import com.ideasStudio.website.entity.ShapeSizeStandard;
import com.ideasStudio.website.entity.ShapeUrl;
import com.ideasStudio.website.mapper.CustMapper;
import com.ideasStudio.website.service.customization.JoinCustCartService;
import com.ideasStudio.website.util.MD5util;
import com.ideasStudio.website.util.ZIPCompress;

@Service
public class JoinCustCartServiceImpl extends BaseServiceImpl implements JoinCustCartService {

	@Autowired
	private CustMapper custmapper;
	
	@Autowired
	private BackupsPaths backupsPaths;
	
	Logger logger = Logger.getLogger(BaseServiceImpl.class);
	
	@Override
	public void UpdataInfo(CustCartArg arg,HttpSession session,HttpServletRequest request) {
		CustCart record = new CustCart();
		DecodeRecordId(session,arg.getId(),arg.getPublicKey());		//如果这里没有抛出异常，则证明没有被篡改
		Integer status = custmapper.selectOrderStatus(arg.getId());
		if(status==1){		//如果订单订单状态为1 则说明这个订单的数据已经完整了，用户执行重复提交
			throw new RuntimeException("此订单已经加入购物车，请勿重复提交");
		}
		Double shapePrice = CountPaintingPrice(arg.getAdjustable(),arg.getShape(),arg.getSizeWidth(),arg.getSizeHeight());
		Double borderTotal = CountBorderPrice(arg.getAdjustable(), arg.getBorderId(), arg.getShape(),arg.getSizeWidth(),arg.getSizeHeight());
		Double price = shapePrice + borderTotal;	//计算总价格  画的价格 + 边框的价格
		Double total =  price * 1.0;				//默认加入购物车的时候只有一份
		record.setId(arg.getId());					//设置记录ID
		record.setBorderId(arg.getBorderId()); 		//设置边框的id
		record.setBorderprice(borderTotal);  		//设置边框的总价格
		record.setPaintingPrice(shapePrice);		//设置画的价格
		record.setCustornum(1);						//设置画的数量  添加数据库默认只添加一个，在购物车可以修改购买数量
		record.setPrice(price);						//设置单价（画价格 + 边框价格）
		record.setTotal(total);						//设置总价  单价 * 数量
		record.setWidth(arg.getSizeWidth());		//设置制作宽度
		record.setHeight(arg.getSizeHeight());		//设置制作高度
		record.setCreateuid(getUIDforSession(session));	//设置记录uid
		record.setUid(getUIDforSession(session));		//设置创建uid
		record.setCreateuuid(getUUIDforSession(session));	//设置记录uuid
		Integer row = custmapper.UpdataInfo(record);		//获取操作受影响的行数  正常情况 =1 
		if(row==1){											//正常情况下
			custmapper.UpdateStatus(1, arg.getId());		//所有数据添加完毕，修改次订单的状态
			Backups(request,arg.getOrdernumber());
		}else{
			throw new RuntimeException("加入购物车失败，请稍后重试!");  //操作失败  
		}
	}
	
	
	/**
	 * 这个因为第二部的操作和第三部的操作分成了两部分，第二部就在数据库生成了一条记录，第三部去补全，而在这个过程中防止用户恶意篡改，
	 * 就需要使用用户的uid或者uuid联合生成的订单id生成一个MD5秘钥，在第三步骤提交数据的时候再去验证一次 ，没有被改变则说明正常操作
	 * 解码记录iD    判断前端传输过来的id是否被篡改
	 * @return  
	 */
	private Boolean DecodeRecordId(HttpSession session,Integer recordId,String publicKey){
		MD5util md5 = new MD5util();
		String uid = getUIDforSession(session);
		String uuid = getUUIDforSession(session);
		String privateUIDKey = md5.getEncrpytedRecordIdByUID(uid, recordId);
		String privateUUIDKey = md5.getEncrpytedRecordIdByUUID(uuid, recordId);
		/**
		 * 判断传输到前端的记录id-recordId生成的MD5码和后面提交的recordId生成的MD5是否匹配，如果不匹配则说明被篡改
		 */
		if(publicKey.equals(privateUIDKey)||publicKey.equals(privateUUIDKey)){
			return true;
		}else{/**如果发现被篡改则直接抛出异常，这个异常在父Controller中没有标记，默认返回的状态码为400 则前端则会提示参数错误，稍后重试*/
			throw new RuntimeException("参数错误，请稍后重试");
		}
	}
	
	/**
	 * 查询图形对应的价格，固定尺寸的价格实际是直接存储在数据库中的，我们不是用前端传输过来的价格，我们从数据库查询然后计算价格
	 * @param shapeId
	 * @return
	 */
	private Double selectPaintingPrice(Integer shapeId){
		ShapeUrl shape = custmapper.findAdjustable(shapeId);
		return shape.getPrice();
	}
	
	
	/**
	 * 查询图形对应的价格，固定尺寸的价格实际是直接存储在数据库中的，我们不是用前端传输过来的价格，我们从数据库查询然后计算价格
	 * @param shapeId
	 * @return
	 */
	private Integer selectPaintingPerimeter(Integer shapeId){
		ShapeSizeStandard ssad = custmapper.findShapeSizeStandardById(shapeId);
		return ssad.getPerimeter();
	}
	
	
	/**
	 * 查询边框的价格，根据边框的id查询内容
	 * @param borderId 边框的Id
	 * @return 返回查询到的价格
	 */
	private Double selectBorderPrice(Integer borderId){
		if(borderId==null){
			return null;
		}
		return custmapper.findBorderById(borderId).getPrice();
	}
	
	/**
	 * 计算画的价格
	 * @param adjustable	判断是否可调
	 * @param shapeId	图形的id
	 * @param width	 前端用户调节的宽度
	 * @param height	前端用户调节的高度
	 * @return 返回计算的结果
	 */
	private Double CountPaintingPrice(boolean adjustable,Integer shapeId,Integer width,Integer height){
		if(adjustable==true){//判断是不是可调尺寸
			return selectPaintingPrice(shapeId);
		}else{
			Double unit = custmapper.findAdjustable(shapeId).getUnit();//查询改图形的每平方米的定制价格  这里如果这样的，切记数据库中shapeurl中的每个图形都需要标上unit 成本价格计算，否则会出异常
			Double m_width = width*.01;			//先把cm转成m
			Double m_height = height*.01;
			Double price = (m_width*m_height)*unit;
			BigDecimal b = new BigDecimal(price);
			return b.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();   //四舍五入 保留0位小数
		}
	}
	
	/**
	 * 计算边框的价格
	 * @param adjustable	图形是否可调
	 * @param borderId		边框的id
	 * @param shapeId		图形的id
	 * @param width	 		前端用户选择的宽度
	 * @param height		前端用户选择的高度
	 * @return
	 */
	private Double CountBorderPrice(boolean adjustable,Integer borderId,Integer shapeId,Integer width,Integer height){
		Double borderPrice = selectBorderPrice(borderId);
		Double borderTotal = 0.00;
		if(borderPrice!=null){	//用查询出来的边框的价格和查询出来的周长计算出来边框的总价格  borderprice ==null 则说明在前端页面上用户没有选择边框
			if(adjustable==true){ 
				borderTotal = borderPrice *  selectPaintingPerimeter(shapeId);
			}else{
				borderTotal = borderPrice *  CountPerimeter(width,height,shapeId);
			}
			BigDecimal b = new BigDecimal(borderTotal);
			borderTotal = b.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return borderTotal;
	}
	
	/**
	 * 计算可调图形的周长 
	 * @param width		矩形的宽度
	 * @param height	矩形的高度
	 * @param shapeId	图形的id	
	 * @return	返回计算的结果
	 */
	private Integer CountPerimeter(Integer width,Integer height,Integer shapeId){
		Double perimeter = 0.00;
		if(shapeId==7){
			Double m_width = (width/2)*.01;
			perimeter = Math.ceil(2 * (Math.PI*m_width));
		}else{
			double m_width = width*.01;			//先把cm转成m
			double m_height = height*.01;
			/*
			 * 判断正方形的图形的长宽是否相等，如果不相等，则说明被用户篡改过，直接抛出异常
			 */
			if(shapeId==2&&(m_width!=m_height)){
				throw new RuntimeException("参数错误，请稍后重试");
			}
			perimeter = Math.ceil((m_width*2)+(m_height*2));	//矩形的周长计算使用宽高*2
		}	
		BigDecimal b = new BigDecimal(perimeter);
		return b.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
	}

	/**
	 * 这里的操作和上面你的操作其实差不错，只是一次性添加完所有数据 然后判断修改订单状态
	 */
	@Override
	public void InsertInfo(CustCartArg arg, HttpSession session,HttpServletRequest request) {
		Integer status = custmapper.selectOrderStatusByOrdernum(arg.getOrdernumber());
		if(status==null){		//只有这个订单没有创建的情况下创建
			String path= request.getServletContext().getRealPath("customization/"+arg.getOrdernumber()+"/0_thumbnail.png");
			path  = path.substring(path.indexOf("customization"),path.length());
			CustCart record = new CustCart();
			Double shapePrice = CountPaintingPrice(arg.getAdjustable(),arg.getShape(),arg.getSizeWidth(),arg.getSizeHeight());
			Double borderTotal = CountBorderPrice(arg.getAdjustable(), arg.getBorderId(), arg.getShape(),arg.getSizeWidth(),arg.getSizeHeight());
			Double price = shapePrice + borderTotal;
			Double total =  price * 1.0;		//默认加入购物车的时候只有一份
			record.setUid(getUIDforSession(session));
			record.setUuid(getUUIDforSession(session));
			record.setOrdernum(arg.getOrdernumber());
			record.setShapeid(arg.getShape());
			record.setCreatetime(new Date());
			record.setPath("\\"+path);
			record.setMaterial(arg.getMaterial());
			record.setBorderId(arg.getBorderId());
			record.setBorderprice(borderTotal);
			record.setPaintingPrice(shapePrice);
			record.setCustornum(1);
			record.setPrice(price);
			record.setTotal(total);
			record.setWidth(arg.getSizeWidth());
			record.setHeight(arg.getSizeHeight());
			record.setCreateuid(getUIDforSession(session));
			record.setUid(getUIDforSession(session));
			record.setCreateuuid(getUUIDforSession(session));
			int row = custmapper.addCustCart(record);
			if(row==1){
				custmapper.UpdateStatus(1, record.getId());
				Backups(request,arg.getOrdernumber());
			}else{
				throw new RuntimeException("加入购物车失败，请稍后重试!");
			}
		}else{
			throw new RuntimeException("此订单已经加入购物车，请勿重复提交");
		}
	}
	
	private void Backups(HttpServletRequest request,String ordernumber){
		try {
			ZIPCompress zip = new ZIPCompress();
			String tomcatpath = request.getServletContext().getRealPath(backupsPaths.getTomcatPaths());
			File tp = new File(tomcatpath);
			if(!tp.exists()){
				tp.mkdir();
			}
			String backuppath = backupsPaths.getBackupsPaths();
			File bp = new File(backuppath);
			if(!bp.exists()){
				bp.mkdir();
			}
			String filepath= request.getServletContext().getRealPath("customization/"+ordernumber);
			BufferedImage th_img = ImageIO.read(new File(filepath+"/0_thumbnail.png"));
			CustCart order = custmapper.queryOrderByOrdernum(ordernumber);
			Shape shape = custmapper.queryTitleByShape(order.getShapeid());
			Date createtime = order.getCreatetime();
			drawOrderImage(filepath+"/OrderIMG.jpg", ordernumber, th_img, createtime, order.getWidth(), order.getHeight(), order.getUid(),shape.getShapeTitle());
			//0_thumbnail.png
			String password  = backupsPaths.getBackUpPassword();
			logger.info("备份压缩密码是："+password);
			zip.compressedFiles(filepath, tomcatpath+"/"+ordernumber+".zip",password);
			zip.compressedFiles(filepath, backuppath+"/"+ordernumber+".zip",password);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("文件备份失败！");
		}
		
		
		
		
	}
	/**
	 * 画一张订单图
	 * @param location
	 * @param ordernumber
	 */
	public void drawOrderImage(String location,String ordernumber,BufferedImage th_img,Date OrderTime,Integer width,Integer height,String uid,String title){
		BufferedImage image = new BufferedImage(1000,800,BufferedImage.TYPE_INT_RGB);   //设置画布
		Graphics g = image.getGraphics();              //设置画笔
		g.setColor(new Color(255,255,255));            //初始化画笔颜色
		g.fillRect(0, 0, 1000, 800);                      //填充一块矩形画板
		g.setColor(new Color(0,0,0));
		g.setFont(new Font("思源黑体 CN ExtraLight",Font.BOLD,36));
		g.drawString("私人订制下单详情", 356, 40);
		g.setFont(new Font("思源黑体 CN ExtraLight",Font.PLAIN,20));
		g.drawString("用户id: "+uid, 40, 82);//
		g.drawString("宽度: "+width+"厘米"+"    高度: "+height+"厘米", 40, 104);//
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
		g.drawString("定制时间: "+sdf.format(OrderTime), 40, 126);//"宽度: "+width+"厘米"+"    高度: "+height+"厘米"
		g.drawString("订单号码: "+ordernumber, 40, 148);//定制时间: "+sdf.format(OrderTime)
		g.drawString("定制图形: "+title, 40, 170);//订单号码: "+ordernumber
		g.setFont(new Font("思源黑体 CN ExtraLight",Font.BOLD,36));
		g.drawString("定制图形样图", 392, 215);
		g.setColor(new Color(255,2,0));
		g.setFont(new Font("思源黑体 CN ExtraLight",Font.PLAIN,15));
		g.drawString("(请使用本路径下像素最大的图片制作)", 610, 215);
		g.drawImage(th_img, (1000/2)-(th_img.getWidth()/2), 250, null);
		File file = new File(location);
		try {
			ImageIO.write(image, "jpeg", file);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
