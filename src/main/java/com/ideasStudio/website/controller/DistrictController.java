package com.ideasStudio.website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ideasStudio.website.entity.District;
import com.ideasStudio.website.entity.ResponseResult;
import com.ideasStudio.website.service.DistrictService;

/**
 * 省市区联动控制层
 * @author 赵志斌
 *
 */
@Controller
@RequestMapping("/district")
public class DistrictController extends BaseController{
	@Autowired
	private DistrictService districtService;
	/**
	 * 获取省市区列表
	 * @param parentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getList.do",method=RequestMethod.POST)
	public ResponseResult<List<District>> getList(Integer parentId){
		List<District> list=districtService.getDistrictList(parentId);
		ResponseResult<List<District>> rr=new ResponseResult<>();
		rr.setData(list);
		return rr;
	}
}
