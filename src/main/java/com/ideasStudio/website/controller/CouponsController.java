package com.ideasStudio.website.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ideasStudio.website.entity.Coupons;
import com.ideasStudio.website.entity.EmpCode;
import com.ideasStudio.website.entity.Order;
import com.ideasStudio.website.entity.ResponseResult;
import com.ideasStudio.website.service.CartService;
import com.ideasStudio.website.service.CouponsService;
import com.ideasStudio.website.service.OrderService;
import com.ideasStudio.website.service.customization.CustService;
import com.ideasStudio.website.service.ex.ServiceException;

/**
* 优惠券
* @author Administrator
*
*/
@Controller
@RequestMapping("/coupons")
public class CouponsController extends BaseController{

	@Autowired
	private CouponsService couponsService;
	@Autowired
	private CartService cartService;
	@Autowired
	private CustService custService;
	@Autowired
	private OrderService orderService;
	
	/**
	 * 使用员工码前查询员工码是否存在,可用就重新计算金额
	 * @param empCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findEmpCode.do",method=RequestMethod.POST)
	public ResponseResult<String> findEmpCode(String empCode,HttpSession session){
		EmpCode emp=couponsService.findEmpCodeByCode(empCode);
		if(emp==null) {
			throw new ServiceException("抱歉，无此优惠券");
		}
		String uid=session.getAttribute("uid").toString();
		Double pay=Double.parseDouble(cartService.getCartMoney(uid)==null?"0.0":cartService.getCartMoney(uid));//获取套装购物车总金额
		Double cpay=Double.parseDouble(custService.getCartMoney(uid)==null?"0.0":custService.getCartMoney(uid));//私人订制金额
		Double payMoney=getPayMoney(pay, cpay, 0);
		ResponseResult<String> rr=new ResponseResult<>();
		rr.setData(payMoney.toString());
		session.setAttribute("couponsCode", empCode);
		return rr;
	}
	
	/**
	 * 使用优惠码前查询该码可不可用，可用就重新计算金额
	 * @param couponsCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findCouponsCode.do",method=RequestMethod.POST)
	public ResponseResult<String> findCouponsCode(String couponsCode,HttpSession session){
		Coupons coupons=couponsService.findCouponsCodeByCode(couponsCode);
		if(coupons==null||coupons.getStatus()==1) {
			throw new ServiceException("此优惠码不可用");
		}
		Order order=orderService.getOrderByCouponsCode(couponsCode);
		if(order!=null) {
			throw new ServiceException("抱歉，此优惠码已被其他订单占用");
		}
		String uid=session.getAttribute("uid").toString();
		Double pay=Double.parseDouble(cartService.getCartMoney(uid)==null?"0.0":cartService.getCartMoney(uid));//获取套装购物车总金额
		Double cpay=Double.parseDouble(custService.getCartMoney(uid)==null?"0.0":custService.getCartMoney(uid));//私人订制金额
		Double payMoney=getPayMoney(pay, cpay, 1);
		ResponseResult<String> rr=new ResponseResult<>();
		rr.setData(payMoney.toString());
		session.setAttribute("couponsCode", couponsCode);
		return rr;
	}
	//获取优惠后的金额
	private double getPayMoney(Double pay,Double cpay,Integer type) {
		BigDecimal cartMoney=new BigDecimal(Double.toString(pay));
		BigDecimal custMoney=new BigDecimal(Double.toString(cpay));
		Double allMoney=cartMoney.add(custMoney).doubleValue();
		BigDecimal all=new BigDecimal(Double.toString(allMoney));
		BigDecimal empCode=new BigDecimal(Double.toString(0.95));//员工码打九五折
		BigDecimal coupons=new BigDecimal(Double.toString(50));//优惠券-50
		Double payMoney=null;
		if(type==0) {//使用的是员工码
			payMoney=all.multiply(empCode).doubleValue();
		}else {//使用的是优惠券
			payMoney=all.subtract(coupons).doubleValue();
		}
		payMoney=new BigDecimal(Double.valueOf(payMoney)).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
		return payMoney;
	}
}
