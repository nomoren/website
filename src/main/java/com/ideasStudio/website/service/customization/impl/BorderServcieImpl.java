package com.ideasStudio.website.service.customization.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideasStudio.website.entity.Border;
import com.ideasStudio.website.mapper.CustMapper;
import com.ideasStudio.website.service.customization.BorderService;

@Service
public class BorderServcieImpl implements BorderService {
	
	@Autowired
	private CustMapper custmapper;

	@Override
	public List<Border> findBorderByKeyWord(String keyword, String material) {
		
		try {
			List<Border> borders = custmapper.findBorderByKeyWord(keyword, material);
			return borders;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

	@Override
	public List<Border> findBorderAll(String material) {
		List<Border> borders = custmapper.findBorderAll(material);
		return borders;
	}

}
