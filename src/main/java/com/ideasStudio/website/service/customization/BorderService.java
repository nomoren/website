package com.ideasStudio.website.service.customization;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ideasStudio.website.entity.Border;

public interface BorderService {

	/**
	 * 根据关键字和材质查询边框
	 * @param keyword
	 * @return
	 */
	List<Border> findBorderByKeyWord(String keyword,String material);
	
	/**
	 * 根据材质查询所有的边框
	 * @return
	 */
	List<Border> findBorderAll(String material);
}
