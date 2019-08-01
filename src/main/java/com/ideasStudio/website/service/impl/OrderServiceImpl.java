package com.ideasStudio.website.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ideasStudio.website.entity.Address;
import com.ideasStudio.website.entity.Order;
import com.ideasStudio.website.entity.OrderItem;
import com.ideasStudio.website.mapper.OrderMapper;
import com.ideasStudio.website.service.AddressService;
import com.ideasStudio.website.service.CartService;
import com.ideasStudio.website.service.OrderService;
import com.ideasStudio.website.service.customization.CustService;
import com.ideasStudio.website.service.ex.ServiceException;
import com.ideasStudio.website.util.CreateOrderNumber;
import com.ideasStudio.website.vo.CartVo;
import com.ideasStudio.website.vo.OrderVo;
/**
 * order服务层
 * @author admin
 *
 */
@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private CartService cartService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private CustService custservice;

	/**
	 *创建订单
	 */
	@Override
	@Transactional
	public String createOrder(String uid, Integer addressId,String giftCard,String couponsCode) {
		Address address=addressService.getAddressById(addressId);//下单的收货地址
		if(address==null) {
			throw new ServiceException("请完善并保存收货信息");
		}
		Date now=new Date();//下单日期
		Double pay=Double.parseDouble(cartService.getCartMoney(uid)==null?"0.0":cartService.getCartMoney(uid));//获取套装购物车总金额
		Double cpay=Double.parseDouble(custservice.getCartMoney(uid)==null?"0.0":custservice.getCartMoney(uid));//私人订制金额
		Double allMoney=null;
		if(couponsCode!="") {
			String reg="[a-z||A-Z]*95";
			if(couponsCode.matches(reg)) {
				allMoney=getPayMoney(pay, cpay, 0);
			}else {
				allMoney=getPayMoney(pay, cpay, 1);
			}
		}else {
			BigDecimal b1=new BigDecimal(Double.toString(pay));
			BigDecimal b2=new BigDecimal(Double.toString(cpay));
			allMoney=b1.add(b2).doubleValue();
			allMoney=new BigDecimal(Double.valueOf(allMoney)).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
		}
		List<CartVo> list=cartService.getCartList(uid);//订单中的所有商品
		List<CartVo> custlist =custservice.getCartList(uid);
		list.addAll(custlist);
		Order order=new Order();//创建一个订单
		String id=UUID.randomUUID().toString();
		order.setId(id);
		order.setUid(uid);
		order.setRecvName(address.getRecvName());
		order.setRecvPhone(address.getRecvPhone());
		order.setRecvTel(address.getRecvTel());
		order.setRecvDistrict(address.getRecvDistrict());
		order.setRecvZip(address.getRecvZip());
		order.setRecvAddress(address.getRecvAddress());
		order.setPay(allMoney);
		order.setOrderTime(now);
		order.setPayStatus(0);
		order.setOutStatus(0);
		order.setTakeStatus(0);
		order.setPayTime(null);
		order.setGiftCard(giftCard);
		order.setUseCoupons(couponsCode);
		Integer row = null;
		for(CartVo cartVo:list) {//创建订单的商品
			OrderItem orderItem=new OrderItem();
			orderItem.setOrderId(order.getId());
			order.setOrderNo(cartVo.getOrderNo()==null?CreateOrderNumber.GenerateOrderNumber():cartVo.getOrderNo());
			orderItem.setGoodsId(cartVo.getGoodsId());
			if(cartVo.getGoodsPath().indexOf(".")!=-1){
				orderItem.setGoodsImage(cartVo.getGoodsPath());
			}else{
				orderItem.setGoodsImage(cartVo.getGoodsPath()+"/img0.png");
			}
			orderItem.setGoodsTitle(cartVo.getGoodsTitle());
			orderItem.setGoodsPrice(cartVo.getGoodsPrice());
			orderItem.setGoodsNo(cartVo.getGoodsNo());
			orderItem.setGoodsLength(cartVo.getGoodsLength());
			orderItem.setGoodsWidth(cartVo.getGoodsWidth());
			orderItem.setGoodsNum(Integer.parseInt(cartVo.getGoodsNum()));
			orderItem.setAllMoney(cartVo.getMoney());
			row=orderMapper.createOrderItem(orderItem);
		}
		Integer num=orderMapper.createOrder(order);
		if(num!=1||row!=1) {
			throw new ServiceException("订单创建失败，请稍后再试");
		}
		cartService.deleteAllCart(uid);
		custservice.deleteAllCart(uid);
		return order.getId();
	}

	/**
	 * 根据订单id查询订单详情
	 */
	@Override
	public OrderVo getOrderById(String id,String uid) {
		String userId=orderMapper.getUidByOrderId(id);
		if(!uid.equals(userId)) {
			throw new ServiceException("订单信息归属错误");
		}
		OrderVo orderVo=orderMapper.getOrderById(id);
		return orderVo;
	}

	/**
	 * 根据订单id查询订单总额
	 */
	@Override
	public String getMonayByOrderId(String id) {
		return orderMapper.getOrderMoneyByOrderId(id).toString();
	}
	/**
	 * 查询时间范围内用户的所有订单
	 */
	@Override
	public List<OrderVo> getOrderByDate(String uid, Integer range) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String end=sdf.format(new Date());//截止至今日
		Calendar c=Calendar.getInstance();
		c.add(Calendar.MONTH, range);//rang个月前
		c.set(Calendar.HOUR_OF_DAY, 0);//00:00:00
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		String start=sdf.format(c.getTime());//range个月前时间点
		return orderMapper.getOrderByDate(uid, start, end);
	}
	//查询用户的订单数量
	@Override
	public Integer getUserOrderCount(String uid) {
		return orderMapper.getUserOrderCount(uid);
	}
	//根据状态查询订单详情
	@Override
	public List<Order> getOrderByStatus(Integer payStatus, Integer outStatus, 
										Integer takeStatus,Integer page) {
		Integer num=5;//每页5条数据
		Integer jump=(page-1)*5;//跳过了多少条
		List<Order> list=orderMapper.getOrderByStatus(payStatus, outStatus, takeStatus, jump, num);
		return list;
	}
	//根据订单状态查询订单数
	@Override
	public Integer getOrderCountByStatus(Integer payStatus, Integer outStatus, Integer takeStatus) {
		return orderMapper.getOrderCountByStatus(payStatus, outStatus, takeStatus);
	}
	//根据订单id查询订单详情，不包括商品列表
	@Override
	public Order getOrderInfo(String id) {
		return orderMapper.getOrderInfo(id);
	}
	//改变订单的状态
	@Override
	public void updateOrder(String status, Integer statusNo, String id) {
		Integer row=orderMapper.updateOrderStatus(status, statusNo, id);
		if(row!=1) {
			throw new ServiceException("修改失败，请稍后再试");
		}
	}
	//根据订单id查询订单的支付状态
	@Override
	public Integer getOrderPayStatus(String id) {
		return orderMapper.getOrderPayStatus(id);
	}
	//根据订单查询订单
	@Override
	public Order getOrderByOrderNo(String orderNo) {
		return orderMapper.getOrderByOrderNo(orderNo);
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
	//根据优惠券查询订单,判断优惠券是否被某个订单占用a
	@Override
	public Order getOrderByCouponsCode(String couponsCode) {
		return orderMapper.getOrderByCouponsCode(couponsCode);
	}
	//查询已经支付的订单总额
	@Override
	public Double getAllPayOrderMoney() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String end=sdf.format(new Date());//截止至当前时间
		Calendar c=Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);//00:00:00
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		String start=sdf.format(c.getTime());
		return orderMapper.getAllPayOrderMoney(start, end);
	}

	@Override
	public Integer getTodayOrderNum() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String end=sdf.format(new Date());//截止至当前时间
		Calendar c=Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);//00:00:00
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		String start=sdf.format(c.getTime());
		return orderMapper.getTodayOrderNum(start, end);
	}

	@Override
	public OrderVo getOrderVoByOrderId(String id) {
		return orderMapper.getOrderById(id);
	}
	
}
