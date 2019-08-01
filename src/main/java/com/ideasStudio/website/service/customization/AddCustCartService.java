package com.ideasStudio.website.service.customization;

import com.ideasStudio.website.entity.CustCart;
import com.ideasStudio.website.entity.ResponseResult;

/**
 * 加入私人订制购物车相关属性
 * @author Administrator
 *
 */
public interface AddCustCartService {
	
	/**
	 * 往私人订制购物车中插入一条记录
	 * @param cart  插入的记录
	 * @return 返回插入的id  然后绑定到页面上，下次用户提交补全信息
	 */
	CustCart InsertCustCart(CustCart cart);
}
