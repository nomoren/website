package com.ideasStudio.website.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ideasStudio.website.entity.Address;
import com.ideasStudio.website.entity.ResponseResult;
import com.ideasStudio.website.service.AddressService;

/**
 * 收获地址控制层
 * @author admin
 *
 */
@Controller
@RequestMapping("/address")
public class AddressController extends BaseController{
	@Autowired
	private AddressService addressService;
	
	/**
	 * 添加收货地址并设为默认
	 * @param session
	 * @return 地址id
	 */
	@ResponseBody
	@RequestMapping(value="/addAndSetDefault.do",method=RequestMethod.POST)
	public ResponseResult<Integer> addAndDefault(HttpSession session,Address address){
		String uid=session.getAttribute("uid").toString();
		address.setUid(uid);
		addressService.addAndDefault(address);
		ResponseResult<Integer> rr=new ResponseResult<>();
		rr.setData(address.getId());
		return rr;
	}
	/**
	 * 查询用户的收货地址数量
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getAddressNum.do",method=RequestMethod.POST)
	public ResponseResult<Map<Object, Object>> getAddressNmu(HttpSession session){
		String uid=session.getAttribute("uid").toString();
		Map<Object, Object> map=new HashMap<>();
		Integer num=addressService.getAddressNum(uid);
		if(num!=0) {//若用户的收货地址数量不为0，则把地址列表查出，在前端显示
			List<Address> list=addressService.getAddressList(uid);
			map.put("list", list);
		}
		map.put("num", num);
		ResponseResult<Map<Object, Object>> rr=new ResponseResult<>();
		rr.setData(map);
		return rr;
	}
	/**
	 * 删除收货地址
	 * @param addressId 收货地址id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteAddress.do",method=RequestMethod.POST)
	public ResponseResult<Void> deleteAddress(Integer addressId,HttpSession session){
		String uid=session.getAttribute("uid").toString();
		addressService.deleteAddressById(addressId, uid);;
		return new ResponseResult<>();
	}
	/**
	 * 设置默认收货地址
	 * @param addressId
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/setDefault.do",method=RequestMethod.POST)
	public ResponseResult<Void> setDefault(Integer addressId,HttpSession session){
		String uid=session.getAttribute("uid").toString();
		addressService.setDefaultAddress(addressId, uid);
		return new ResponseResult<>();
	}
	/**
	 * 根据id修改收货地址
	 * @param address
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateAddress.do",method=RequestMethod.POST)
	public ResponseResult<Void> updateAddress(Address address){
		addressService.updateAddress(address);
		return new ResponseResult<>();
	}
	/**
	 * 新增收货地址
	 * @param session
	 * @param address
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/insertAddress.do",method=RequestMethod.POST)
	public ResponseResult<Void> insertAddress(HttpSession session,Address address){
		String uid=session.getAttribute("uid").toString();
		address.setUid(uid);
		addressService.insertAddress(address);
		return new ResponseResult<>();
	}
}
