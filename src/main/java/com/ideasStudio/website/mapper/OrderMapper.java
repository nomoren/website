package com.ideasStudio.website.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ideasStudio.website.entity.Order;
import com.ideasStudio.website.entity.OrderItem;
import com.ideasStudio.website.vo.OrderVo;

/**
 * 订单Mapper
 * @author admin
 *
 */
@Mapper
public interface OrderMapper {

	/**
	 * 创建一个订单
	 * @param order
	 * @return
	 */
	Integer createOrder(Order order);
	/**
	 * 创建订单的商品
	 * @param orderItem
	 * @return
	 */
	Integer createOrderItem(OrderItem orderItem);
	/**
	 * 根据订单id查询订单详情
	 * @param id
	 * @return
	 */
	OrderVo getOrderById(String id);
	/**
	 * 通过订单id查询用户id
	 * @return
	 */
	String getUidByOrderId(String id);
	/**
	 * 根据起始日期查询用户该段时间的所有订单
	 * @param uid
	 * @param startDate 开始日期
	 * @param endDate  结束日期
	 * @return
	 */
	List<OrderVo> getOrderByDate(@Param("uid")String uid,
			@Param("start")String startDate,@Param("end")String endDate);
	
	/**
	 * 根据id查询订单号码
	 * @param id	表id
	 * @return 返回查询到的订单号码
	 */
	String getOrderNo(String id);
	
	/**
	 * 通过订单id查询订单总金额
	 * @param id
	 * @return
	 */
	Double getOrderMoneyByOrderId(String id);
	
	/**
	 * 通过orderno查询订单总金额
	 * @param id
	 * @return
	 */
	Double getOrderMoneyByOrderNo(String orderNo);
	/**
	 * 查询用户的订单数量
	 * @param uid
	 * @return
	 */
	Integer getUserOrderCount(String uid);
	/**
	 * 根据状态分页查询订单信息
	 * @param 
	 * @return
	 */
	List<Order> getOrderByStatus(
								 @Param("payStatus")Integer payStatus,
								 @Param("outStatus")Integer outStatus,
								 @Param("takeStatus")Integer takeStatus,
								 @Param("jump")Integer jump,
								 @Param("num")Integer num);
	
	/**
	 * 根据状态查询订单数量
	 * @param payStatus
	 * @param outStatus
	 * @param takeStatus
	 * @return
	 */
	Integer getOrderCountByStatus( @Param("payStatus")Integer payStatus,
			 @Param("outStatus")Integer outStatus,
			 @Param("takeStatus")Integer takeStatus);
	
	/**
	 * 根据订单id查询订单详情，不包括商品列表
	 * @param id
	 * @return
	 */
	Order getOrderInfo(String id);
	/**
	 * 根据订单id改变订单某个状态
	 * @param status 状态
	 * @param statusNo 状态代码
	 * @return
	 */
	Integer updateOrderStatus(@Param("status")String status,
							  @Param("statusNo")Integer statusNo,
							  @Param("id")String id);
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
	 * 查询订单是否用了优惠券
	 * @param id
	 * @return
	 */
    String getCouponsByOrderNo(String orderNo);
    /**
     * 根据优惠券查询订单
     * @param couponsCode
     * @return
     */
    Order getOrderByCouponsCode(String couponsCode);
	/**
	 * 查询时间范围内所有已支付订单总额
	 * @return
	 */
    Double getAllPayOrderMoney(@Param("start")String start,@Param("end")String end);
    /**
     * 查询今日已支付订单数
     * @return
     */
    Integer getTodayOrderNum(@Param("start")String start,@Param("end")String end);
	/**
	 * 首先查询订单的支付状态  避免同步和异步的时间差
	 * @param orderNo	订单号
	 * @return  返回支付状态
	 */
	@Select("SELECT payStatus FROM ordertable where orderNo = #{orderNo}")
	Integer getOrderPayStatusByOrderNo(String orderNo);
	/**
	 * 查询订单的金额，和支付完的金额对比
	 * @param orderNo	订单号
	 * @return 返回传到的金额
	 */
	@Select("SELECT pay FROM ordertable where orderNo = #{orderNo}")
	Double getTotalByOrderNo(String orderNo);
	
	
	/**
	 * 更新此订单的支付结果
	 * @param orderNo	订单号
	 * @param endtime	支付时间
	 * @param tradeStatus	支付状态
	 * @return 返回受影响的行数
	 */
	@Update("UPDATE ordertable SET payTime = #{paytime},payStatus = #{payStatus},payWay = #{payWay} WHERE orderNo = #{orderNo}")
	Integer UpdatePayResult(@Param("orderNo")String orderNo,@Param("paytime")String endtime,@Param("payStatus")String tradeStatus,@Param("payWay")String payWay);
	
	
	/**
	 * 查询订单是否存在
	 * @param OrderNo
	 * @return
	 */
	@Select("SELECT id from ordertable where id = #{orderNo}")
	String queryOrderNo(String OrderNo);
	
}
