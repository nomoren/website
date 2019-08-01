package com.ideasStudio.website.controller;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ideasStudio.website.config.BackupsPaths;
import com.ideasStudio.website.entity.Border;
import com.ideasStudio.website.entity.CustCart;
import com.ideasStudio.website.entity.CustCartArg;
import com.ideasStudio.website.entity.CustPiece;
import com.ideasStudio.website.entity.CutImgArg;
import com.ideasStudio.website.entity.ResponseResult;
import com.ideasStudio.website.entity.Shape;
import com.ideasStudio.website.service.customization.AddCustCartService;
import com.ideasStudio.website.service.customization.BorderService;
import com.ideasStudio.website.service.customization.CustService;
import com.ideasStudio.website.service.customization.CutImgService;
import com.ideasStudio.website.service.customization.JoinCustCartService;
import com.ideasStudio.website.service.customization.ShapeService;
import com.ideasStudio.website.service.customization.ThumbnailService;
import com.ideasStudio.website.util.MD5util;
import com.ideasStudio.website.vo.ShapeSize;

@Controller
@RequestMapping("/cust") 
public class CustomizationController extends BaseController {
	
	Logger logger = Logger.getLogger(CustomizationController.class);
	
	 //切图实现类
	@Autowired
	private CutImgService cutimgservice;
	//获得缩略图实现类
	@Autowired
	private ThumbnailService thumbnailservice;
	//获取定制图的url 
	@Autowired
	private CustService custservice;
	//加入购物车
	@Autowired
	private AddCustCartService custcart;
	//边框操作
	@Autowired
	private BorderService borderservice;
	//加入购物车操作
	@Autowired
	private JoinCustCartService joinCustCartService;
	//图形操作
	@Autowired
	private ShapeService shapeservice;
	
	/**
	 * 获取备份路径
	 */
	@Autowired
	private BackupsPaths backups;
	
	
	/**
	 * 私人订制第一个页面将图形数据查询出来方法
	 * @return 返回list
	 */
	@ResponseBody
	@RequestMapping(value="/getShape.do",method=RequestMethod.POST)
	public ResponseResult<List<Shape>> getShape(){
		ResponseResult<List<Shape>> shapes = new ResponseResult<List<Shape>>();
		shapes.setData(shapeservice.getShape());
		return shapes;
	}
	
	/**
	 * Thymeleaf模板引擎返回页面图形页面
	 * @param shape  图形id
	 * @param material	材质
	 * @param session 用户session
	 * @param mm 返回参数封装map
	 * @return 返回返回的页面
	 */
	@RequestMapping("/chooseshape.do")
	public String ChooseShape(
			@RequestParam(value="shape",required=false,defaultValue="-1")Integer shape,
			@RequestParam(value="material",required=false)String material,
			HttpSession session,
			ModelMap mm){
		   if(shape!=-1){    //如果用户又写shapeid  就是判断用户是不是直接在地址栏输入
				Object uid = session.getAttribute("uid");
				String uuid = null;
				if(uid==null){  //如果 用户没有登录的话，初始化一个uuid当做用的临时session
					String value = UUID.randomUUID().toString();
					mm.addAttribute("uuid", value);
					session.setAttribute("uuid", value);
				}else{
					mm.addAttribute("uid",uid.toString());
				}
				mm.addAttribute("material",material);
				try {
					return custservice.getUrl(shape);
				} catch (Exception e) {
					mm.addAttribute("message","系统繁忙，请稍后重试！");
					return "cust/selectshape";
				}
			}else{
				return "cust/selectshape";
			}
	}
	
	/**
	 * Thymeleaf模板引擎返回页面详情页面
	 * @param shapeId  图形id
	 * @param ordernumber 订单号
	 * @param uuid 用户uuid
	 * @param uid 用户uid
	 * @param material 材质
	 * @param session 用户session 
	 * @param mm 返回数据封装map
	 * @param request 获取tomcat根目录下文件夹  获取路劲
	 * @return 返回页面
	 */
	@RequestMapping("/choosesize.do")
	public String ChooseShape(
			@RequestParam(value="shape",required=false)Integer shapeId,
			@RequestParam(value="ordernumber",required=false)String ordernumber,
			@RequestParam(value="uuid",required=false)String uuid,
			@RequestParam(value="uid",required=false)String uid,
			@RequestParam(value="material",required=false)String material,
			HttpSession session, ModelMap mm,
			HttpServletRequest request){
			if(shapeId==null||ordernumber==null||material==null){
				return "cust/selectshape";	//如果参数未提交 直接返回初始页
			}
			MD5util md5 = new MD5util();
			String path= request.getServletContext().getRealPath("customization/"+ordernumber+"/0_thumbnail.png");
			path  = path.substring(path.indexOf("customization"),path.length());
			logger.info("PATH路径--------:"+path);
			CustCart cart = new CustCart();
			if(uid!=null){cart.setCreateuuid(uid);}
			else if(uuid!=null){cart.setUid(uuid);}
			cart.setUid(uid);
			cart.setUuid(uuid);
			cart.setOrdernum(ordernumber);
			cart.setShapeid(shapeId);
			cart.setCreatetime(new Date());
			cart.setPath("\\"+path);
			cart.setMaterial(material);
			custcart.InsertCustCart(cart);
			mm.addAttribute("id",cart.getId());
			mm.addAttribute("ordernumber",cart.getOrdernum());
			mm.addAttribute("shape",cart.getShapeid());
			mm.addAttribute("material",cart.getMaterial());
			String publicKey  = null; 
			if(session.getAttribute("uid")==null){
				publicKey = md5.getEncrpytedRecordIdByUUID(session.getAttribute("uuid").toString(), cart.getId());
			}else{
				publicKey = md5.getEncrpytedRecordIdByUUID(session.getAttribute("uid").toString(), cart.getId());
			}
			mm.addAttribute("publicKey",publicKey);
			return "cust/selectsize";
	}
	
	/**
	 * 上传选中的图片
	 * @param request 获取tomcat根目录下文件夹  获取路劲
	 * @param CustomImage 用户上传的图片
	 * @return 返回操作结果  200正常返回
	 */
	@RequestMapping(value="/upload.do")
	@ResponseBody
	public ResponseResult<Void> uploadImage(
			HttpServletRequest request,
			@RequestParam(value ="CustomImage",required = true)
			CommonsMultipartFile CustomImage){
		// 确定头像保存到的文件夹的路径：项目根目录下的upload文件夹
				String uploadDirPath= request.getServletContext().getRealPath("upload");
				// 确定头像保存到的文件夹
				File uploadDir = new File(uploadDirPath);// 确保文件夹存在
				if (!uploadDir.exists()) {
					uploadDir.mkdirs();
				}
				// 确定头像文件的扩展名，例如：aaa.bbb.ccc.jpg，所需的是.jpg
				int beginIndex = CustomImage.getOriginalFilename()
						.lastIndexOf(".");
				String suffix = CustomImage.getOriginalFilename()
						.substring(beginIndex);
				//给文件取一个随机的名字
				String fileName = UUID.randomUUID().toString() + suffix;
				File dest = new File(uploadDir, fileName);
				try {
					CustomImage.transferTo(dest);
				} catch (IOException e) {
				}
				ResponseResult<Void> result = new ResponseResult<Void>();
				result.setMessage(fileName);
		return result;
	}
		
	/**
	 * 接收切图的参数并且交给业务层开始切图
	 * @param request	//该次请求的请求对象
	 * @param filename	//要切图的文件的名称
	 * @param dataX		//矩形的X坐标	
	 * @param dataY		//矩形的Y坐标
	 * @param dataHeight	//矩形的高度
	 * @param dataWidth		//矩形的宽度
	 * @return	返回切图状态
	 */
	@RequestMapping(value="/uploadCutImgArg.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<CustPiece> cutImage(
			HttpServletRequest request,
			@RequestParam(value="filename",required=true) String filename,
			@RequestParam(value="dataX",required=true) Integer dataX,
			@RequestParam(value="dataY",required=true) Integer dataY,
			@RequestParam(value="dataHeight",required=true) Integer dataHeight,
			@RequestParam(value="dataWidth",required=true) Integer dataWidth,
			@RequestParam(value="piece",required=true) Integer piece,
			@RequestParam(value="ordernumber",required=false,defaultValue="")String ordernumber,
			@RequestParam(value="shape",required=false)Integer shape
			){
		ResponseResult<CustPiece> result = null;
			CutImgArg arg  = new CutImgArg(dataX, dataY, dataHeight, dataWidth);
			String uploadDirPath= request.getServletContext().getRealPath("upload");
			Image image = Toolkit.getDefaultToolkit().getImage(uploadDirPath+"/"+filename);

			if(piece==0){
				result = cutimgservice.CutImages(image, arg,piece,shape,request);
			}else{
				result = cutimgservice.CutImage(image, arg, ordernumber,piece,request);
			}
		return result;
		
	}
	
	/**
	 * 获得缩略图
	 * @param piece	 获取第几张缩略图
	 * @param ordernumber 原来还没有缩小时的原图地址，订单文件夹名称
	 * @param response  返回二进制流需要使用response直接写出，
	 * @param request 需要通过request获取到tomcat文件夹下的路径
	 * @return	返回一个空的
	 */
	@RequestMapping(value="/getThumbnnail.do")
	@ResponseBody
	public void getThumbnail(
			@RequestParam(value="piece",required=true)Integer piece,
			@RequestParam(value="ordernumber",required=true)String ordernumber,
			HttpServletResponse response,
			HttpServletRequest request){
			thumbnailservice.getThumbnail(response, request, piece, ordernumber);
	}
	
	/**
	 * 前端提交截取的缩略图
	 * @param urlcode 缩略图的
	 * @param ordernumber
	 * @param request
	 */
	@RequestMapping(value="/uploadthumbnnail.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> uploadThumbnail(
			@RequestParam(value="urlcode") String urlcode,
			@RequestParam(value="ordernumber")String ordernumber,
			HttpServletRequest request){
		thumbnailservice.generateThumbnail(request, urlcode, ordernumber);
		return new ResponseResult<Void>();
	}

	/**
	 * 异步请求获取图形尺寸的图片
	 * @param shapeId 图形的id
	 * @return 返回查询到的图片的url
	 */
	@RequestMapping(value="/getSize.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<String> getSize(
			@RequestParam(value="shape") Integer shapeId){
		ResponseResult<String> result = new ResponseResult<String>();
		String url = custservice.getSizeUrl(shapeId);
		result.setData(url==null?"1":url);
		return result;
	}
	
	/**
	 * 异步请求获取图形尺寸详情
	 * @param shapeId 图形的id
	 * @return 返回查询到的图形的尺寸详情
	 */
	@RequestMapping(value="/getSizeDetails.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<ShapeSize> getSizeDetails(
			@RequestParam(value="shape") Integer shapeId){
		ResponseResult<ShapeSize> result = new ResponseResult<ShapeSize>();
		result.setData(custservice.getSizeDetails(shapeId));
		return result;
	}
	
	/**
	 * 异步请求获取边框
	 * @param keyword	关键字
	 * @param material	材质
	 * @return 返回查询到的边框的集合
	 */
	@RequestMapping(value="/findBorderBykeyword.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<List<Border>> findBorderByKeyWord(
			@RequestParam(value="keyword",required=true) String keyword,
			@RequestParam(value="material",required=true)String material){
		ResponseResult<List<Border>> result = new ResponseResult<List<Border>>();
		result.setData(borderservice.findBorderByKeyWord(keyword, material));
		return result;
	}
	
	/**
	 * 查找所有的边框
	 * @param material 根据材质
	 * @return 返回查询到的边框
	 */
	@RequestMapping(value="/findBorderAll.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<List<Border>> findBorderAll(
			@RequestParam(value="material",required=true)String material){
		ResponseResult<List<Border>> result = new ResponseResult<List<Border>>();
		result.setData(borderservice.findBorderAll(material));
		return result;
	}
	
	/**
	 * 判断用户是否登录
	 * @param session	用户对应的session
	 * @return	返回用户session中的uid
	 */
	@RequestMapping(value="/judgeLogin.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<String> judgeLogin(HttpSession session){
		ResponseResult<String> result = new ResponseResult<String>();
		result.setData(session.getAttribute("uid").toString());
		return result;
	}
	
	/**
	 * 更新私人订制购物车数据
	 * @param session	用户的session
	 * @param arg	更新的相关数据
	 * @return	返回更新的状态
	 */
	@RequestMapping(value="/updateinfo.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> updateInfo(HttpSession session,CustCartArg arg,HttpServletRequest request){
		joinCustCartService.UpdataInfo(arg,session,request);
		return new ResponseResult<Void>(200,"加入购物车成功，请前往购物车查看!");
	}
	
	/**
	 * 加入购物车操作
	 * @param session		用户的session
	 * @param arg  插入数据库的数据
	 * @param request 获取tomcat下的路径
	 * @return	返回更新的状态
	 */
	@RequestMapping(value="/joincustcart.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> joinCustCart(HttpSession session,CustCartArg arg,HttpServletRequest request){
		joinCustCartService.InsertInfo(arg, session, request);
		return new ResponseResult<Void>(200,"加入购物车成功，请前往购物车查看!");
	}
	

	@RequestMapping ("/download.do")
    @ResponseBody
    public void downloadZIPFile (HttpServletRequest request, HttpServletResponse response){
        InputStream in = null;
        OutputStream out = null;
        try {
            request.setCharacterEncoding ("utf-8");
            String orderNo = request.getParameter ("orderNo");	//获得单号
            String tomcatpath= request.getServletContext().getRealPath(backups.getTomcatPaths());
            String backuppath = backups.getBackupsPaths();
            File tomcatfile = new File(tomcatpath+"/"+orderNo+".zip");
            logger.info("tomcat下的文件路径:"+tomcatfile.getAbsolutePath());
            File backupfile = new File(backuppath+"/"+orderNo+".zip");	
            logger.info("backupfile写的文件路径:"+backupfile.getAbsolutePath());
            if(!tomcatfile.exists()&&!backupfile.exists()){return;}
            if(tomcatfile.exists()){
            	in = new FileInputStream (tomcatfile);
            }else if(backupfile.exists()){
            	in = new FileInputStream (backupfile);
            }
            response.setHeader ("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding ("utf-8");//设置编码集,文件名不会发生中文乱码
            response.setHeader ("Content-Disposition", "attachment;filename=" + orderNo+".zip");
            out = response.getOutputStream ();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read (buffer)) > 0) {
                out.write (buffer, 0, len);
            }
        }
        catch (Exception e) {
            if (e instanceof IOException) {
                logger.error ("文件下载发生异常!", e);
            }
            else if (e instanceof UnsupportedEncodingException) {
                logger.error ("设置编码格式发生异常!", e);
            }
        }
        finally {
            try {
            	if(out!=null){
            		out.flush ();
            		out.close ();
            	}
            	if(in!=null){
            		in.close ();
            	}else{
            		RuntimeException run = new RuntimeException("文件不存在");
                	super.HanderException(run);
                	throw run;
            	}
            } catch (IOException e) {
                e.printStackTrace ();
            }
        }
    }
}
