package com.ideasStudio.website.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideasStudio.website.entity.District;
import com.ideasStudio.website.mapper.DistrictMapper;
import com.ideasStudio.website.service.DistrictService;

@Service
public class DistrictServiceImpl implements DistrictService{
	@Autowired
	private DistrictMapper districtMapper;
	
	@Override
	public List<District> getDistrictList(Integer parentId) {
		List<District> list=districtMapper.getDistrictList(parentId);
		return list;
	}

	@Override
	public District getDistrict(Integer id) {
		District district=districtMapper.getDistrict(id);
		return district;
	}

}
