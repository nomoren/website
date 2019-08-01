package com.ideasStudio.website.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ideasStudio.website.entity.OrderItem;
import com.ideasStudio.website.entity.ResponseResult;
import com.ideasStudio.website.service.OrderService;
import com.ideasStudio.website.service.ex.ServiceException;
import com.ideasStudio.website.vo.OrderVo;

/**
 * 订单控制器层
 * @author admin
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController{
	@Autowired
	private OrderService orderService;
	
	
	/**
	 * 创建一个订单
	 * @param session
	 * @param addressId 收货地址id
	 * @param giftCard 礼品卡内容
	 * @return 订单的id,后续可通过这个id查询订单详情
	 */
	@ResponseBody
	@RequestMapping(value="/createOrder.do",method=RequestMethod.POST)
	public ResponseResult<String> createOrder(HttpSession session,Integer addressId,
			String giftCard,String useCoupons){
		String uid=session.getAttribute("uid").toString();
		String oid=orderService.createOrder(uid, addressId, giftCard,useCoupons);
		ResponseResult<String> rr=new ResponseResult<>();
		rr.setData(oid);
		if(session.getAttribute("couponsCode")!=null) {
			session.removeAttribute("couponsCode");
		}
		return rr;
	}
	/**
	 * 查询订单详情
	 * @param session
	 * @param orderId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getOrderInfo.do",method=RequestMethod.POST)
	public ResponseResult<Map<Object, Object>> getOrderInfo(HttpSession session,String orderId){
		if(orderService.getOrderPayStatus(orderId)==1) {
			throw new ServiceException("该订单已支付");
		};
		String uid=session.getAttribute("uid").toString();
		OrderVo orderVo=orderService.getOrderById(orderId, uid);
		String money=orderService.getMonayByOrderId(orderId);
		Map<Object, Object> map=new HashMap<>();
		map.put("money", money);//订单金额
		map.put("orderVo",orderVo);//订单详情
		ResponseResult<Map<Object, Object>> rr=new ResponseResult<>();
		rr.setData(map);
		return rr;
	}
	/**
	 * 查询时间范围内用户的所用订单
	 * @param session
	 * @param range 如1 1个月内
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getOrderByDate.do",method=RequestMethod.POST)
	public ResponseResult<List<OrderVo>> getOrderByDate(HttpSession session,Integer range){
		String uid=session.getAttribute("uid").toString();
		List<OrderVo> list=orderService.getOrderByDate(uid, range);
		ResponseResult<List<OrderVo>> rr=new ResponseResult<>();
		rr.setData(list);
		return rr;
	}
	//下载用户的订单
	@ResponseBody
	//@RequestMapping(value="/download.do",method=RequestMethod.POST)
	public void download(HttpServletRequest request,HttpServletResponse response,String orderId) {
		OrderVo ov=orderService.getOrderVoByOrderId(orderId);
		if(ov!=null) {
			HSSFWorkbook wb=new HSSFWorkbook();
			HSSFSheet sheet=wb.createSheet("用户订单商品表");
			HSSFRow row1=sheet.createRow(0);
			HSSFCell cell=row1.createCell(0);
			cell.setCellValue(ov.getRecvName()+"订单商品详情("+ov.getOrderNo()+")");
			
			HSSFRow row2=sheet.createRow(1);
			row2.createCell(0).setCellValue("商品标题");
			row2.createCell(1).setCellValue("商品编号");
			row2.createCell(2).setCellValue("长");
			row2.createCell(3).setCellValue("宽");
			row2.createCell(4).setCellValue("数量");
			
			List<OrderItem> list=ov.getList();
			for(int i=0;i<list.size();i++) {
				HSSFRow row=sheet.createRow(i+2);
				row.createCell(0).setCellValue(list.get(i).getGoodsTitle());
				row.createCell(1).setCellValue(list.get(i).getGoodsNo());
				row.createCell(2).setCellValue(list.get(i).getGoodsLength());
				row.createCell(3).setCellValue(list.get(i).getGoodsWidth());
				row.createCell(4).setCellValue(list.get(i).getGoodsNum());
			}
		
			response.setHeader ("Content-type", "application/vnd.ms.excel");
            response.setCharacterEncoding ("utf-8");//设置编码集,文件名不会发生中文乱码
            response.setHeader ("Content-Disposition", "attachment;filename=" + ov.getOrderNo()+".xls");
            String tomcatPath=request.getServletContext().getRealPath("download");
            File dis=new File(tomcatPath);
            if(!dis.exists()) {
            	dis.mkdirs();
            }
			File file=new File(tomcatPath+"/"+ov.getOrderNo()+".xls");
			InputStream in=null;
			OutputStream out=null;
			try {
				if(!file.exists()) {
					file.createNewFile();
				}
				wb.write(file);
				wb.close();
				in=new FileInputStream(file);
				out =response.getOutputStream();
				byte[] buffer = new byte[1024];
				int len=-1;
				while((len=in.read(buffer))!=-1) {
					out.write(buffer, 0, len);
				}
				out.flush();
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("下载失败，请稍后再试");
			}finally {
				try {
					if(in!=null) {
						in.close();
					}
					if(out!=null) {
						out.close();
					}
				} catch (Exception e) {
				}
			}
		}
	}
	
	
	/* public void download */
}
