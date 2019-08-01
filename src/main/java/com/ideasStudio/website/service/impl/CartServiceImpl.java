package com.ideasStudio.website.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideasStudio.website.entity.Cart;
import com.ideasStudio.website.mapper.CartMapper;
import com.ideasStudio.website.mapper.GoodsInfoMapper;
import com.ideasStudio.website.service.CartService;
import com.ideasStudio.website.service.ex.InsertException;
import com.ideasStudio.website.service.ex.ServiceException;
import com.ideasStudio.website.vo.CartVo;

/**
 * 购物车实现类
 * @author 赵志斌
 *
 */
@Service
public class CartServiceImpl implements CartService{
	@Autowired
	private GoodsInfoMapper goodsMapper;
	@Autowired
	private CartMapper cartMapper;
	/**
	 * 加入购物车
	 */
	@Override
	public void addToCart(Cart cart) {
		Cart old=cartMapper.findCartByUidAndGoodsId(cart.getUid(), cart.getGoodsId(),cart.getSizeId());
		Double price=goodsMapper.getGoodsSizeMoney(cart.getSizeId()).getPrice();
		if(old!=null) {//先查询数据存不存在，存在则加数量
			Integer goodsNum=old.getGoodsNum();
			Integer num=goodsNum+cart.getGoodsNum();
			Double allMoney=price*num;
			cartMapper.updateCartNum(old.getId(), num,allMoney);
		}else {//没有数据，加入购物车
			cart.setAllMoney(price);
			Integer row=cartMapper.addToCart(cart);
			if(row!=1) {
				throw new InsertException("服务器繁忙，请稍后再试");
			}
		}
	}
	/**
	 * 根据uid查询购物车列表
	 */
	@Override
	public List<CartVo> getCartList(String uid) {
		List<CartVo> list=cartMapper.getCartList(uid);
		return list;
	}
	/**
	 * 查询用户的购物车总金额
	 */
	public String getCartMoney(String uid) {
		Double money=cartMapper.getCartMoney(uid);
		if(money==null) {//购物车为空的情况
			return "0.0";
		}
		return money.toString();
	}
	/**
	 * 购物车商品数量+1或-1
	 */
	@Override
	public Map<Object, Object> addOneOrSubOne(Integer cartId,String type,String uid,Integer cust) {
		Map<Object, Object> map=new HashMap<>();
		if(cust!=1){
			Cart cart=cartMapper.findCartByCartId(cartId);
			Double price=goodsMapper.getGoodsSizeMoney(cart.getSizeId()).getPrice();
			Integer num=cart.getGoodsNum();
			if("add".equals(type)) {//数量加一
				num++;
			}else {//数量减一
				num--;
			}
			Double goodsMoney=price*num;//单项商品总金额
			Integer row=cartMapper.updateCartNum(cart.getId(),num,goodsMoney);
			if(row!=1) {
				throw new ServiceException("服务器繁忙，请稍后再试");
			}
			map.put("goodsMoney", goodsMoney.toString());
		}
		Double allMoney=cartMapper.getCartMoney(uid);//该用户购物车总金额
		map.put("allMoney", allMoney==null?"0.00":allMoney.toString());
		return map;
	}
	/**
	 * 根据购物车id删除购物车
	 */
	public String deleteCartById(Integer cartId,String uid,Integer cust) {
		if(cust!=1){
			Integer row=cartMapper.deleteCartById(cartId);
			if(row!=1) {
				throw new ServiceException("删除失败，请稍后再试...");
			}
		}
		Double allMoney=cartMapper.getCartMoney(uid);//该用户购物车总金额
		if(allMoney==null) {//当删除完最后一条，allMoney查出来为null,返回0
			return "0.0";
		}
		return allMoney.toString();
	}
	/*查询用户购物车数量*/
	public Integer getCartCount(String uid) {
		return cartMapper.getCartCount(uid);
	}
	//清空用户购物车
	@Override
	public void deleteAllCart(String uid) {
		Integer num=cartMapper.getCartCount(uid);
		if(num!=0) {
			Integer row=cartMapper.deleteAllCart(uid);
			if(row==0) {
				throw new ServiceException("发生未知错误，请稍后再试");
			}
		}
		
	}
}
