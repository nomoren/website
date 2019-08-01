package com.ideasStudio.website.service.customization.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideasStudio.website.entity.CustCart;
import com.ideasStudio.website.mapper.CustMapper;
import com.ideasStudio.website.service.customization.AddCustCartService;

/**
 * 加入私人订制购物车  业务层实现
 * @author Administrator
 *
 */
@Service("custcart")
public class AddCustCartServiceImpl implements AddCustCartService {

	/**
	 * 自动装配加入购物车持久层对象
	 */
	@Autowired
	private CustMapper custcart;
	
	@Override
	public CustCart InsertCustCart(CustCart cart) {
		CustCart result = custcart.findRecord(cart.getOrdernum());
		if(result==null){    //先判断这条记录是否已经提交过了，用户刷新造成的
			Integer row = custcart.InsertCustCart(cart);
			if(row!=-1){
				return cart;
			}else{
				throw new RuntimeException("加入失败，请稍后重试");
			}
		}else{
			cart.setId(result.getId());
			return cart;
		}
	}
}
