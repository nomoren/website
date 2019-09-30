package com.ideasStudio.website.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ideasStudio.website.controller.BaseController;
import com.ideasStudio.website.entity.Coupons;
import com.ideasStudio.website.entity.EmpCode;
import com.ideasStudio.website.entity.ResponseResult;
import com.ideasStudio.website.service.CouponsService;
import com.ideasStudio.website.service.ex.ServiceException;
import com.ideasStudio.website.vo.EmpCodeVo;
/**
 * 优惠券
 * @author Administrator
 * git git2 git3
 */
@Controller
@RequestMapping("/admin")
public class CouponsMangerController extends BaseController{
	
	@Autowired
	private CouponsService couponsService;
	/**
	 * 添加优惠券
	 * @param coupons
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/addCoupons.do",method=RequestMethod.POST)
	public ResponseResult<Void> addCouponse(Coupons coupons){
		coupons.setStatus(0);
		System.out.println(coupons);
		couponsService.addCoupons(coupons);
		return new ResponseResult<>();
	}
	/**
	 * 获取所有优惠券
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getCouponsList.do",method=RequestMethod.POST)
	public ResponseResult<Map<Object, Object>> getCouponsList(Integer page){
		Map<Object, Object> map=new HashMap<>();
		Integer number=couponsService.getCount();
		Integer allPage=1;//总页，当数据不够分页，默认一页
		if(number>5) {
			allPage=number%5==0?number/5:number/5+1;//可以分页时能分几页
		}
		if(page>allPage) {
			throw new ServiceException("没有数据了");
		}
		map.put("num", number);//总数
		map.put("allPage", allPage);//共几页
		List<Coupons> list=couponsService.getCoupons(page);
		map.put("list", list);
		ResponseResult<Map<Object, Object>> rr=new ResponseResult<>();
		rr.setData(map);
		return rr;
	}
	/**
	 * 删除已使用过的优惠券
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteCoupons.do",method=RequestMethod.POST)
	public ResponseResult<Void> deleteCoupons(Integer id){
		couponsService.deleteCoupons(id);
		return new ResponseResult<>();
	}
	/**
	 * 新增一个员工码
	 * @param emp
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/addEmpCode.do",method=RequestMethod.POST)
	public ResponseResult<Void> addEmpCode(EmpCode emp){
		couponsService.insertNewEmpCode(emp);
		return new ResponseResult<>();
	}
	/**
	 * 分页查询员工码
	 * @param page 第几页
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getEmpCodeList.do",method=RequestMethod.POST)
	public ResponseResult<Map<Object, Object>> getEmpCodeList(Integer page){
		Map<Object, Object> map=new HashMap<>();
		Integer number=couponsService.getEmpCodeCount();//总数
		Integer allPage=1;//总页，当数据不够分页，默认一页
		if(number>5) {
			allPage=number%5==0?number/5:number/5+1;//可以分页时能分几页
		}
		if(page>allPage) {
			throw new ServiceException("没有数据了");
		}
		map.put("num", number);//总数
		map.put("allPage", allPage);//共几页
		List<EmpCodeVo> list=couponsService.getEmpCodeVoList(page);
		map.put("list", list);
		ResponseResult<Map<Object, Object>> rr=new ResponseResult<>();
		rr.setData(map);
		return rr;
	}
	/**
	 * 删除员工码
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteEmpCode.do",method=RequestMethod.POST)
	public ResponseResult<Void> deleteEmpCode(String code){
		couponsService.deleteEmpCode(code);
		return new ResponseResult<>();
	}
	
	@ResponseBody
	@RequestMapping(value="/getEmpCodeUseRecordList.do",method=RequestMethod.POST)
	public ResponseResult<Map<Object, Object>> getRecordList(Integer page,
					@RequestParam(value="name",required=false,defaultValue="")String name,
					@RequestParam(value="code",required=false,defaultValue="")String code){
		Map<Object, Object> map=new HashMap<>();
		Integer number=couponsService.getOneEmpCodeRecordCount(name, code);
		Integer allPage=1;//总页，当数据不够分页，默认一页
		if(number>5) {
			allPage=number%5==0?number/5:number/5+1;//可以分页时能分几页
		}
		if(page>allPage) {
			throw new ServiceException("没有数据了");
		}
		map.put("num", number);//总数
		map.put("allPage", allPage);//共几页
		List<EmpCodeVo> list=couponsService.getOneEmpCodeUseRecord(page, name, code);
		map.put("list", list);
		ResponseResult<Map<Object, Object>> rr=new ResponseResult<>();
		rr.setData(map);
		return rr;
	}
	
}
