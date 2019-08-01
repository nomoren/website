package com.ideasStudio.website.mapper;

import java.util.List;

import com.ideasStudio.website.entity.District;

public interface DistrictMapper {
	/**
	 * 获取省的列表/省的市列表/市的区、镇、县列表
	 * @param parentId 父级id，省的父级id是0
	 * @return 列表
	 */
	List<District> getDistrictList(Integer parentId);

	/**
	 * 根据id查询地址信息
	 * @param id
	 * @return
	 */
	District getDistrict(Integer id);
}
