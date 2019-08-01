package com.ideasStudio.website.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ideasStudio.website.controller.BaseController;
import com.ideasStudio.website.entity.ResponseResult;
import com.ideasStudio.website.service.GoodsInfoService;
import com.ideasStudio.website.service.OrderService;
import com.ideasStudio.website.service.UserService;

@Controller
@RequestMapping("/data")
public class DataMangerController extends BaseController{
	@Autowired
	private UserService userService;
	@Autowired
	private GoodsInfoService goodsService;
	@Autowired
	private OrderService orderService;
	//查询首页的数据，订单数，人数等
	@ResponseBody
	@RequestMapping(value="/getIndexData.do",method=RequestMethod.POST)
	public ResponseResult<Map<Object, Object>> getIndexData(){
		Integer allUser=userService.getCountUser(1);
		Integer goodsNum=goodsService.getGoodCount();
		Integer orderNum=orderService.getOrderCountByStatus(1, 1, 1);
		String todayOrderMoney=String.valueOf(orderService.getAllPayOrderMoney()==null?"0.0":orderService.getAllPayOrderMoney());
		Integer todayOrderNum=orderService.getTodayOrderNum();
		Integer todayUserNum=userService.getDayUserCount();
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("allUser", allUser);
		map.put("goodsNum", goodsNum);
		map.put("orderNum", orderNum);
		map.put("todayOrderMoney", todayOrderMoney);
		map.put("todayOrderNum", todayOrderNum);
		map.put("todayUserNum", todayUserNum);
		map.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		ResponseResult<Map<Object, Object>> rr=new ResponseResult<>();
		rr.setData(map);
		return rr;
	}
}
