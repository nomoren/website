package com.ideasStudio.website.service.customization.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideasStudio.website.entity.Shape;
import com.ideasStudio.website.mapper.CustMapper;
import com.ideasStudio.website.service.customization.ShapeService;

@Service
public class ShapeServcieImpl implements ShapeService {

	@Autowired
	private CustMapper custmapper;
	@Override
	public List<Shape> getShape() {
		return custmapper.getShape();
	}
}
