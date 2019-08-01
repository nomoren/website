package com.ideasStudio.website.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ideasStudio.website.entity.Cart;
import com.ideasStudio.website.vo.CartVo;

/**
 * 购物车service
 * @author 赵志斌
 *
 */
public interface CartService {
	/**
	 * 加入购物车
	 * @param cart 购物车实例
	 */
	void addToCart(Cart cart);
	
	/**
	 * 根据用户id查询购物车列表
	 * @param uid
	 * @return
	 */
	List<CartVo> getCartList(String uid);
	
	/**
	 * 根据cartId修改购物车商品数量+1或-1
	 * @param cartId
	 * @param type 值为"+"或者"-"
	 */
	Map<Object, Object> addOneOrSubOne(Integer cartId,String type,String uid,Integer cust);
	/**
	 * 根据uid查询购物车总金额,返回购物车总金额
	 * @param uid
	 * @return
	 */
	String getCartMoney(String uid);
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
