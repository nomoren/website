package com.ideasStudio.website.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ideasStudio.website.entity.Order;
import com.ideasStudio.website.vo.OrderVo;

/**
 * 订单服务接口
 * @author 赵志斌
 *
 */
public interface OrderService {
	/**
	 * 创建一个订单,返回订单的id
	 * @param uid 用户id
	 * @param addressId 地址id
	 * @param giftCard 礼品卡内容
	 */
	String createOrder(String uid,Integer addressId,String giftCard,String couponsCode);

	/**
	 * 根据订单id查询订单详情
	 * @param id
	 * @param uid
	 * @return
	 */
	OrderVo getOrderById(String id,String uid);
	/**
	 * 根据订单id查询订单详情
	 * @param id
	 * @return
	 */
	OrderVo getOrderVoByOrderId(String id);
	/**
	 * 根据订单id查询订单总额
	 * @param id
	 * @return
	 */
	String getMonayByOrderId(String id);
	/**
	 * 查询时间范围内用户的所有订单
	 * @param uid 用户id
	 * @param range 如：1 表示一个月内 ，  12 表示12个月内
	 * @return
	 */
	List<OrderVo> getOrderByDate(String uid,Integer range);
	/**
	 * 查询用户的订单数量
	 * @param uid
	 * @return
	 */
	Integer getUserOrderCount(String uid);
	
	/**
	 * 根据状态查询订单信息
	 * @param 
	 * @return
	 */
	List<Order> getOrderByStatus(
								 Integer payStatus,
								 Integer outStatus,
								 Integer takeStatus,
								 Integer page);
	/**
	 * 根据状态查询订单数量
	 * @param payStatus
	 * @param outStatus
	 * @param takeStatus
	 * @return
	 */
	Integer getOrderCountByStatus(Integer payStatus,
								  Integer outStatus,
								  Integer takeStatus);
	/**
	 * 根据订单id查询订单详情，不包括商品列表
	 * @param id
	 * @return
	 */
	Order getOrderInfo(String id);
	/**
	 * 改变订单的状态
	 * @param status
	 * @param statusNo
	 * @param id
	 */
	void updateOrder(String status,Integer statusNo,String id);
	/**
	 * 根据订单id查询订单的支付状态
	 * @param id
	 * @return
	 */
	Integer getOrderPayStatus(String id);
	/**
	 * 根据订单编号查询订单
	 * @param orderNo
	 * @return
	 */
	Order getOrderByOrderNo(String orderNo);
	  /**
     * 根据优惠券查询订单,判断优惠券是否被某个订单占用
     * @param couponsCode
     * @return
     */
    Order getOrderByCouponsCode(String couponsCode);
	/**
	 * 查询所有已经支付的订单总额
	 * @return
	 */
    Double getAllPayOrderMoney();
    /**
     * 查询今日已支付订单数
     * @return
     */
    Integer getTodayOrderNum();
}
