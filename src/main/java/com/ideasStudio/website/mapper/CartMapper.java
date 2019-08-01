package com.ideasStudio.website.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ideasStudio.website.entity.Cart;
import com.ideasStudio.website.vo.CartVo;

/**
 * 购物车Mapper
 * @author 赵志斌
 *
 */
public interface CartMapper {
	/**
	 * 加入购物车
	 * @param cart 购物车实例
	 * @return
	 */
	Integer addToCart(Cart cart);
	/**
	 * 根据用户id和商品id,sizeid查询购物车
	 * @param uid
	 * @param goodsId
	 * @return
	 */
	Cart findCartByUidAndGoodsId(@Param("uid")String uid,
								@Param("goodsId")Integer goodsId,
								@Param("sizeId")Integer sizeId);
	
	/**
	 * 根据购物车id查询购物车
	 * @param cartId
	 * @return
	 */
	Cart findCartByCartId(Integer cartId);

	/**
	 * 根据购物车id跟新购物车数量
	 * @param id
	 * @return
	 */
	Integer updateCartNum(@Param("id")Integer id,@Param("goodsNum")Integer goodsNum,@Param("allMoney")Double allMoney);
	
	/**
	 * 根据用户id查询购物车列表
	 * @param uid
	 * @return
	 */
	List<CartVo> getCartList(String uid);
	
	/**
	 * 根据uid查询客户的购物车总金额
	 * @param uid
	 * @return
	 */
	Double getCartMoney(String uid);
	/**
	 * 根据购物车id删除购物车数据
	 * @param cartId
	 * @return
	 */
	Integer deleteCartById(Integer cartId);
	
	/**
	 * 查询用户的购物车数量
	 * @param uid
	 * @return
	 */
	Integer getCartCount(String uid);
	/**
	 * 清空用户的购物车
	 * @param uid
	 * @return
	 */
	Integer deleteAllCart(String uid);
}
