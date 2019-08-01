package com.ideasStudio.website.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ideasStudio.website.entity.Cart;
import com.ideasStudio.website.entity.ResponseResult;
import com.ideasStudio.website.service.CartService;
import com.ideasStudio.website.service.customization.CustService;
import com.ideasStudio.website.vo.CartVo;

/**
 * 购物车Controller
 * @author 赵志斌
 *
 */
@RequestMapping("/cart")
@Controller
public class CartController extends BaseController{
	@Autowired
	private CartService cartService;
	
	@Autowired
	private CustService custservice;
	
	private Double cartmoney;		//套装购物车
	private Double custmoney;		//私人订制购物车
	/**
	 * 加入购物车
	 * @param session
	 * @param goodsId 商品id
	 * @param goodsNum 数量
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/add.do",method=RequestMethod.POST)
	public ResponseResult<Void> addCart(HttpSession session,Integer goodsId,Integer goodsNum,Integer sizeId){
		String uid=session.getAttribute("uid").toString();
		Cart cart=new Cart();
		cart.setUid(uid);
		cart.setGoodsId(goodsId);
		cart.setGoodsNum(goodsNum);
		cart.setSizeId(sizeId);
		cartService.addToCart(cart);
		return new ResponseResult<>();
	}
	/**
	 * 根据uid查询购物车列表
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/list.do",method=RequestMethod.POST)
	public ResponseResult<Map<Object, Object>> getCartList(HttpSession session){
		String uid=session.getAttribute("uid").toString();
		List<CartVo> list1=cartService.getCartList(uid);
		List<CartVo> list2 = custservice.getCartList(uid);
		list2.forEach(cartvo->cartvo.setCust(1));
		list2.forEach(goods->System.out.println(goods));
		String money1 = custservice.getCartMoney(uid);
		String money2=cartService.getCartMoney(uid);
		String money = String.valueOf(Double.parseDouble((money1=="null"?"0.00":money1))+Double.parseDouble((money2=="null"?"0.00":money2)));
		list1.addAll(list2);
		Map<Object, Object> map=new HashMap<>();
		map.put("list", list1);
		map.put("money", money);
		ResponseResult<Map<Object, Object>> rr=new ResponseResult<>();
		rr.setData(map);
		return rr;
	}
	/**
	 * 购物车商品数量加一或减一
	 * @param cardId 购物车id
	 * @param type 值为“+”，“-”
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/addSubOne.do",method=RequestMethod.POST)
	public ResponseResult<Map<Object, Object>> addOneOrSubOne(Integer cartId,String type,Integer sizeId,Integer cust,HttpSession session){
		Map<Object, Object> map = new HashMap<Object, Object>();
		String uid=session.getAttribute("uid").toString();
		Set<Entry<Object, Object>> set1= cartService.addOneOrSubOne(cartId,type,uid,cust).entrySet();
		set1.forEach(entry->{
			if(entry.getKey()=="allMoney"){
				this.cartmoney  = Double.valueOf(entry.getValue().toString());
				return;
			}
			map.put(entry.getKey(), entry.getValue());
		});
		Set<Entry<Object, Object>> set2 =  custservice.addOneOrSubOne(cartId,type,uid,cust).entrySet();
		set2.forEach(entry->{
			if(entry.getKey()=="allMoney"){
				this.custmoney  = Double.valueOf(entry.getValue().toString());
				return;
			}
			map.put(entry.getKey(), entry.getValue());
		});
		Double total = cartmoney + custmoney;
		map.put("allMoney", total);
		ResponseResult<Map<Object, Object>> rr=new ResponseResult<>();
		rr.setData(map);
		return rr;
	}
	/**
	 * 根据cartId删除购物车
	 * @param cartId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteCart.do",method=RequestMethod.POST)
	public ResponseResult<String> deleteCartById(Integer cartId,HttpSession session,Integer cust){
		String uid=session.getAttribute("uid").toString();
		String allMoney = "0.00";
		System.out.println(cust);
		double cartmoney = Double.valueOf(cartService.deleteCartById(cartId,uid,cust));
		double custmoney = Double.valueOf(custservice.deleteCartById(cartId,uid,cust));
		ResponseResult<String> rr=new ResponseResult<>();
		Double total = cartmoney + custmoney;
		rr.setData(String.valueOf(total));
		return rr;
	}
	
	/**
	 * 查询用户的购物车总金额
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getCartMoney.do",method=RequestMethod.POST)
	public ResponseResult<Map<Object, Object>> getCartMoney(HttpSession session){
			String uid=session.getAttribute("uid").toString();
			Map<Object, Object> map=new HashMap<>();
			Double pay=Double.parseDouble(cartService.getCartMoney(uid)==null?"0.0":cartService.getCartMoney(uid));//获取套装购物车总金额
			Double cpay=Double.parseDouble(custservice.getCartMoney(uid)==null?"0.0":custservice.getCartMoney(uid));//私人订制金额
			Double payMoney=null;
			if(session.getAttribute("couponsCode")!=null) {//该用户使用了优惠券
				String couponsCode=session.getAttribute("couponsCode").toString();
				map.put("couponsCode", couponsCode);
				String reg="[a-z||A-Z]*95";
				if(couponsCode.matches(reg)) {
					payMoney=getPayMoney(pay, cpay, 0);
				}else {
					payMoney=getPayMoney(pay, cpay, 1);
				}
			}else {//没有使用优惠券
				BigDecimal cartMoney=new BigDecimal(Double.toString(pay));
				BigDecimal custMoney=new BigDecimal(Double.toString(cpay));
				payMoney=cartMoney.add(custMoney).doubleValue();
			}
			map.put("payMoney", Double.toString(payMoney));
			ResponseResult<Map<Object, Object>> rr=new ResponseResult<>();
			rr.setData(map);
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
