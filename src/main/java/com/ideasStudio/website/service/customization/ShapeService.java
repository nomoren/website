package com.ideasStudio.website.service.customization;

import java.util.List;

import com.ideasStudio.website.entity.Shape;

/**
 * 图形处理业务层操作
 * @author Administrator
 *
 */
public interface ShapeService {
	
	/**
	 * 获取图形列表
	 * @return
	 */
	List<Shape> getShape();
}
