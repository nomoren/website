package com.ideasStudio.website.service.customization.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideasStudio.website.entity.CustCart;
import com.ideasStudio.website.entity.ShapeSizeStandard;
import com.ideasStudio.website.entity.ShapeUrl;
import com.ideasStudio.website.mapper.CustMapper;
import com.ideasStudio.website.service.customization.CustService;
import com.ideasStudio.website.service.ex.ServiceException;
import com.ideasStudio.website.vo.CartVo;
import com.ideasStudio.website.vo.ShapeSize;

@Service("custservice")
public class CustServiceImpl implements CustService {

	/**
	 * 自动转配mapper
	 */
	@Autowired
	private CustMapper custmapper;
	
	@Override
	public String getUrl(Integer shape) {
		return custmapper.getUrl(shape).getUrl();
	}

	@Override
	public String getSizeUrl(Integer shapeId) {
		return custmapper.findsizepic(shapeId);
	}
	
	@Override
	public ShapeSize getSizeDetails(Integer shapeId) {
		ShapeSize shapesize = new ShapeSize();
		ShapeUrl shape = custmapper.findAdjustable(shapeId);
		if(shape.getAdjustable()==0){   //如果是不可调尺寸的话
			shapesize.setAdjustable(true);
			shapesize.setPrice(shape.getPrice());
			List<ShapeSizeStandard> ShapeSizeStandards=custmapper.findStandardSize(shapeId);
			shapesize.setSSSs(ShapeSizeStandards);
		}else{				//如果是可调尺寸的话
			shapesize.setAdjustable(false);
			List<Integer> widths = custmapper.findNonStandardSize(shapeId,"Width");
			shapesize.setWidths(widths);
			List<Integer> heights = custmapper.findNonStandardSize(shapeId, "Height");
			if(heights.size()>=1){			//如果是一号图形或者二号图形就直接没有这个属性
				shapesize.setHeights(heights);
			}
		}
		return shapesize;
	}

	@Override
	public Integer getAdjustable(Integer shapeId) {
		return custmapper.findAdjustable(shapeId).getAdjustable();
	}

	@Override
	public List<Integer> findStandardSize(Integer shapeId, String field) {
		return custmapper.findNonStandardSize(shapeId, field);
	}

	@Override
	public List<Integer> findNonStandardSize(Integer shapeId, String field) {
		return custmapper.findNonStandardSize(shapeId, field);
	}

	@Override
	public List<CartVo> getCartList(String uid) {
		return custmapper.getCartList(uid);
	}

	@Override
	public String getCartMoney(String uid) {
		if(custmapper.getCartMoney(uid)==null) {
			return "0.0";
		}
		return String.valueOf(custmapper.getCartMoney(uid));
	}

	@Override
	public Map<Object, Object> addOneOrSubOne(Integer cartId, String type, String uid,Integer cust) {
		Map<Object, Object> map=new HashMap<>();
		if(cust==1){
			CustCart cart = custmapper.getCartPriceByRecordId(cartId);
			if("add".equals(type)){
				cart.setCustornum(cart.getCustornum()+1);
			}else{
				cart.setCustornum(cart.getCustornum()-1);
			}
			Double total = cart.getPrice() * cart.getCustornum();  //重新计算总价
			cart.setId(cartId);
			cart.setTotal(total);
			Integer rows =custmapper.UpdateCartOrNumByRecordId(cart);
			if(rows!=1){
				throw new RuntimeException("服务器繁忙，请稍后再试");
			}
			map.put("goodsMoney", total);
		}
		map.put("allMoney", getCartMoney(uid)=="null"?"0.00":getCartMoney(uid));
		return map;
	}

	@Override
	public String deleteCartById(Integer cartId, String uid,Integer cust) {
		if(cust==1){
			Integer rows = custmapper.DeleteCustRecordById(cartId);
			if(rows!=1){
				throw new RuntimeException("删除失败，请稍后重试");
			}
		}
		return getCartMoney(uid)=="null"?"0.00":getCartMoney(uid);
	}

	@Override
	public Integer getCartCount(String uid) {
		return custmapper.getCartCount(uid);
	}

	@Override
	public void deleteAllCart(String uid) {
		Integer num=custmapper.getCartCount(uid);
		if(num!=0) {
			Integer row=custmapper.deleteAllCart(uid);
			if(row==0) {
				throw new ServiceException("发生未知错误，请稍后再试");
			}
		}
		
	}
}
