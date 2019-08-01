package com.ideasStudio.website.service;

import java.util.List;

import com.ideasStudio.website.entity.Address;

/**
 * 收获地址服务层
 * @author Administrator赵志斌
 *
 */
public interface AddressService {

	/**
	 * 添加并设为默认收获地址
	 * @param address
	 */
	Address addAndDefault(Address address);
	
	/**
	 * 通过id查询收获地址信息
	 * @param id
	 * @return
	 */
	Address getAddressById(Integer id);
	
	/**
	 * 查询用户的收货地址数量
	 * @param uid
	 * @return
	 */
	Integer getAddressNum(String uid);
	
	/**
	 * 根据用户id查询收获地址列表
	 * @param uid
	 * @return
	 */
	List<Address> getAddressList(String uid);
	
	/**
	 * 根据地址id查询用户id，用来判断归属
	 * @param id
	 * @return
	 */
	String getUidByAddressId(Integer id);
	
	/**
	 * 根据地址id删除收货地址
	 * @param id
	 */
	void deleteAddressById(Integer id,String uid);
	/**
	 * 设置默认收货地址
	 * @param id
	 */
	void setDefaultAddress(Integer id,String uid);
	/**
	 * 跟新用户的收货地址
	 * @param id
	 */
	void updateAddress(Address address);
	/**
	 * 新增收货地址
	 * @param address
	 */
	void insertAddress(Address address);
}
