package com.ideasStudio.website.mapper;

import java.util.List;

import com.ideasStudio.website.entity.Address;

/**
 * 收获地址mapper
 * @author admin
 *
 */
public interface AddressMapper {
	/**
	 * 保存并设为默认收获地址
	 * @param address
	 * @return
	 */
	Integer addAndDefault(Address address);
	
	/**
	 * 设置用户所有地址为非默认
	 * @param uid
	 * @return
	 */
	Integer setNotDefault(String uid);
	/**
	 * 查询用户的收获地址数量
	 * @param uid
	 * @return
	 */
	Integer getAddressNum(String uid);
	/**
	 * 根据id查询收获地址信息
	 * @param id
	 * @return
	 */
	Address getAddressById(Integer id);
	
	/**
	 * 查询用户的收货地址列表
	 * @param uid
	 * @return
	 */
	List<Address> getAddressList(String uid);
	
	/**
	 * 根据地址id查询uid，判断归属。
	 * @param id
	 * @return
	 */
	String getUidByAddressId(Integer id);
	
	/**
	 * 根据id删除地址信息
	 * @param id
	 * @return
	 */
	Integer deleteAddressById(Integer id);
	/**
	 * 获取用户的所有收货地址中id最大的
	 * @param uid
	 * @return
	 */
	Integer getMaxAddressId(String uid);
	/**
	 * 设置默认收货地址
	 * @param id
	 * @return
	 */
	Integer setDefaultById(Integer id);
	/**
	 * 修改地址
	 * @param id
	 * @return
	 */
	Integer updateAddress(Address address);
	/**
	 * 保存新的收货地址
	 * @param address
	 * @return
	 */
	Integer insertAddress(Address address); 
}
