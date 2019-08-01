package com.ideasStudio.website.service.customization;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.ideasStudio.website.entity.CustCartArg;
import com.ideasStudio.website.vo.CartVo;
import com.ideasStudio.website.vo.ShapeSize;

/**
 * 通过Shape的id查询对应的返回的视图名称
 * @author Administrator
 *
 */
public interface CustService {
	/**
	 * 根据对应的图形，获取到对应的url路径
	 * @param shape	图形的ID
	 * @return 返回查询到的对应的url路径
	 */
	String getUrl(Integer shape);
	
	/**
	 * 根据对应的图形，获取到对应的尺寸url路径
	 * @param shape	图形的ID
	 * @return 返回查询到的对应的url路径
	 */
	String getSizeUrl(Integer shapeId);
	
	/**
	 * 根据图形ID图形是否是标准图形
	 * @param shapeId 图形的id
	 * @return 返回0则代表是标准图形，它有标准的宽高，返回1则表示是可调的图形
	 */
	Integer getAdjustable(Integer shapeId);
	
	/**
	 * 查找标准尺寸
	 * @param shapeId 查找图形ID
	 * @return 返回查找到的图形的尺寸
	 */
	List<Integer> findStandardSize(Integer shapeId,String field);
	
	/**
	 * 查找非标准尺寸
	 * @param shapeId  查找图形的id
	 * @return 返回超找到的图形的尺寸
	 */
	List<Integer> findNonStandardSize(Integer shapeId,String field);
	
	/**
	 * 返回尺寸详情
	 * @param shapeId
	 * @return
	 */
	ShapeSize getSizeDetails(Integer shapeId);
	
	/**
	 * 根据用户id查询购物车列表
	 * @param uid
	 * @return
	 */
	List<CartVo> getCartList(String uid);
	
	/**
	 * 根据uid查询购物车总金额,返回购物车总金额
	 * @param uid
	 * @return
	 */
	String getCartMoney(String uid);
	
	/**
	 * 根据cartId修改购物车商品数量+1或-1
	 * @param cartId
	 * @param type 值为"+"或者"-"
	 */
	Map<Object, Object> addOneOrSubOne(Integer cartId,String type,String uid,Integer cust);
	
	
	
	/**
	 * 根据购物车id删除购物车,返回购物车总金额
	 * @param cartId
	 */
	String deleteCartById(Integer cartId,String uid,Integer cust);
	
	/**
	 * 查询购物车数量
	 * @param uid
	 * @return
	 */
	Integer getCartCount(String uid);
	
	/**
	 * 清空用户的购物车
	 * @param uid
	 * @return
	 */
	void deleteAllCart(String uid);
}
