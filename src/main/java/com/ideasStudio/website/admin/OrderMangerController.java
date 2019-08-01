package com.ideasStudio.website.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ideasStudio.website.controller.BaseController;
import com.ideasStudio.website.entity.Order;
import com.ideasStudio.website.entity.ResponseResult;
import com.ideasStudio.website.service.OrderService;
import com.ideasStudio.website.service.ex.ServiceException;

@Controller
@RequestMapping("/admin")
public class OrderMangerController extends BaseController{
	@Autowired
	private OrderService orderService;
	/**
	 * 根据订单状态分页查询订单
	 * @param payStatus
	 * @param outStatus
	 * @param takeStatus
	 * @param page 第几页
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getOrderByStatus.do",method=RequestMethod.POST)
	public ResponseResult<Map<Object, Object>> getOrderListByStatus(Integer payStatus,Integer outStatus,Integer takeStatus,Integer page){
		Map<Object, Object> map=new HashMap<>();
		Integer number=orderService.getOrderCountByStatus(payStatus, outStatus, takeStatus);
		Integer allPage=1;//总页，当数据不够分页，默认一页
		if(number>5) {
			allPage=number%5==0?number/5:number/5+1;//可以分页时能分几页
		}
		if(page>allPage) {
			throw new ServiceException("没有数据了");
		}
		List<Order> list=orderService.getOrderByStatus(payStatus, outStatus, takeStatus, page);
		map.put("list", list);
		map.put("num", number);//总数
		map.put("allPage", allPage);//共几页
		ResponseResult<Map<Object, Object>> rr=new ResponseResult<>();
		rr.setData(map);
		return rr;
	}
	/**
	 * 根据id查询订单详情，不包含商品
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getOrderInfo.do",method=RequestMethod.POST)
	public ResponseResult<Order> getOrderInfo(String id){
		Order order=orderService.getOrderInfo(id);
		ResponseResult<Order> rr=new ResponseResult<>();
		rr.setData(order);
		return rr;
	}
	/**
	 * 改变订单的状态
	 * @param status
	 * @param statusNo
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateOrderStatus.do",method=RequestMethod.POST)
	public ResponseResult<Void> updateOrderStatus(String status,Integer statusNo,String id){
		orderService.updateOrder(status, statusNo, id);
		return new ResponseResult<>();
	}
	@ResponseBody
	@RequestMapping(value="/getOrderByOrderNo.do",method=RequestMethod.POST)
	public ResponseResult<List<Order>> getOrderByOrderNo(String orderNo){
		Order order=orderService.getOrderByOrderNo(orderNo);
		List<Order> list=new ArrayList<>();
		list.add(order);
		ResponseResult<List<Order>> rr=new ResponseResult<>();
		rr.setData(list);
		return rr;
	}
}
