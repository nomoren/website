package com.ideasStudio.website.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ideasStudio.website.entity.Address;
import com.ideasStudio.website.entity.District;
import com.ideasStudio.website.mapper.AddressMapper;
import com.ideasStudio.website.mapper.OrderMapper;
import com.ideasStudio.website.service.AddressService;
import com.ideasStudio.website.service.DistrictService;
import com.ideasStudio.website.service.ex.ServiceException;

/**
 * 收获地址实现类
 * @author 赵志斌
 *
 */
@Service
public class AddressServiceImpl implements AddressService{
	@Autowired
	private AddressMapper addressMapper;
	@Autowired 
	private DistrictService districtService;
	/**
	 * 添加并设为默认地址
	 */
	@Override
	@Transactional
	public Address addAndDefault(Address address) {
		String recvDistrict=getRecvDistrictByCode(address.getRecvProvince(), address.getRecvCity(), address.getRecvArea());
		address.setRecvDistrict(recvDistrict);
		Integer num=addressMapper.getAddressNum(address.getUid());
		if(num!=0) {//如果用户已经添加过地址，则全部设为非默认
			addressMapper.setNotDefault(address.getUid());
		}
		address.setIsDefault(1);//把新添加的地址设为默认
		Integer row=addressMapper.addAndDefault(address);
		if(row!=1) {
			throw new ServiceException("保存失败，请稍后再试");
		}
		return address;
	}
	
	/**
	 * 获取收获地址省市区
	 * @param provinceCode 省的代号
	 * @param cityCode 市的代号
	 * @param areaCode 区的代号
	 * @return 如：广东省，东莞市，虎门镇
	 */
	private String getRecvDistrictByCode(Integer provinceCode,Integer cityCode,Integer areaCode) {
		District province=districtService.getDistrict(provinceCode);
		District city=districtService.getDistrict(cityCode);
		District area=districtService.getDistrict(areaCode);
		StringBuffer sb=new StringBuffer();
		sb.append(province==null?null:province.getName());
		sb.append(",");
		sb.append(city==null?null:city.getName());
		sb.append(",");
		sb.append(area==null?null:area.getName());
		return sb.toString();
	}

	//通过id查询收货地址信息
	@Override
	public Address getAddressById(Integer id) {
		return addressMapper.getAddressById(id);
	}

	//查询用户的收货地址数量
	@Override
	public Integer getAddressNum(String uid) {
		return addressMapper.getAddressNum(uid);
	}
	//根据用户id查询收货地址列表
	@Override
	public List<Address> getAddressList(String uid) {
		return addressMapper.getAddressList(uid);
	}

	//通过地址id查询用户的id
	@Override
	public String getUidByAddressId(Integer id) {
		return addressMapper.getUidByAddressId(id);
	}
	
	//根据id删除收货地址
	@Override
	@Transactional
	public void deleteAddressById(Integer id,String uid) {
		Address address=addressMapper.getAddressById(id);
		if(address!=null) {
			Integer num=addressMapper.deleteAddressById(id);
			if(num!=1) {
				throw new ServiceException("删除失败，请稍后再试");
			}
			if(address.getIsDefault()==1) {//如果删除的市默认地址，则把最大id的地址设为默认
				Integer count=addressMapper.getAddressNum(uid);
				if(count!=0) {
					Integer maxId=addressMapper.getMaxAddressId(uid);
					Integer row=addressMapper.setDefaultById(maxId);
					if(row!=1) {
						throw new ServiceException("删除失败，请稍后再试");
					}
				}
			}
		}else {
			throw new ServiceException("无此地址");
		}
	}
	
	//设置默认收货地址
	@Override
	@Transactional
	public void setDefaultAddress(Integer id,String uid) {
		Address address=addressMapper.getAddressById(id);
		if(address!=null) {
			addressMapper.setNotDefault(uid);
			Integer row=addressMapper.setDefaultById(id);
			if(row!=1) {
				throw new ServiceException("设置失败，请稍后再试");
			}
		}else {
			throw new ServiceException("无此地址");
		}
		
	}
	
	/**
	 * 跟新用户收货地址
	 */
	@Override
	public void updateAddress(Address address) {
		String recvDistrict=getRecvDistrictByCode(address.getRecvProvince(), address.getRecvCity(), address.getRecvArea());
		address.setRecvDistrict(recvDistrict);
		Integer row=addressMapper.updateAddress(address);
		if(row!=1) {
			throw new ServiceException("修改失败，请稍后再试");
		}
		
	}

	//新增用户地址
	@Override
	public void insertAddress(Address address) {
		String recvDistrict=getRecvDistrictByCode(address.getRecvProvince(), address.getRecvCity(), address.getRecvArea());
		address.setRecvDistrict(recvDistrict);
		Integer count=addressMapper.getAddressNum(address.getUid());
		if(count==0) {//还没有地址，则设为默认
			address.setIsDefault(1);
		}else {
			address.setIsDefault(0);
		}
		Integer row=addressMapper.insertAddress(address);
		if(row!=1) {
			throw new ServiceException("保存失败，请稍后再试");
		}
	}
	
	
	
}
