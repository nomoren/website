package com.ideasStudio.website.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ideasStudio.website.entity.Coupons;
import com.ideasStudio.website.entity.EmpCode;
import com.ideasStudio.website.entity.EmpCodeRecord;
import com.ideasStudio.website.mapper.CouponsMapper;
import com.ideasStudio.website.service.CouponsService;
import com.ideasStudio.website.service.ex.ServiceException;
import com.ideasStudio.website.vo.EmpCodeVo;
//优惠券实现类
@Service
public class CouponsServiceImpl implements CouponsService{

	@Autowired
	private CouponsMapper couponsMapper;
	/**
	 * 添加优惠券
	 */
	@Override
	public void addCoupons(Coupons coupons) {
		Integer row=couponsMapper.addCoupons(coupons);
		if(row!=1) {
			throw new ServiceException("添加失败，请稍后再试");
		}
	}
	//查询所有优惠券
	@Override
	public List<Coupons> getCoupons(Integer page) {
		Integer num=5;//每页5条数据
		Integer jump=(page-1)*5;//跳过了多少条
		return couponsMapper.getCoupons(jump,num);
	}
	//查询总数
	@Override
	public Integer getCount() {
		return couponsMapper.getCount();
	}
	//删除一个优惠券
	@Override
	public void deleteCoupons(Integer id) {
		Integer row=couponsMapper.deleteCoupons(id);
		if(row!=1) {
			throw new ServiceException("删除失败，请稍后再试");
		}
	}
	//新增一个员工码
	@Override
	public void insertNewEmpCode(EmpCode emp) {
		EmpCode data=couponsMapper.findEmpCodeByCode(emp.getCode());
		if(data!=null) {
			throw new ServiceException("该员工码已存在");
		}
		Integer row=couponsMapper.insertNewEmpCode(emp);
		if(row!=1) {
			throw new ServiceException("添加失败，请稍后再试");
		}
	}
	//新增员工码使用记录
	@Override
	public void insertNewEmpCodeRecode(EmpCodeRecord record) {
		int row=couponsMapper.insertNewEmpCodeRecode(record);
		if(row!=1) {
			throw new ServiceException("add empCodeRecord failed");
		}
	}
	//分页查询员工码
	@Override
	public List<EmpCodeVo> getEmpCodeVoList(Integer page) {
		Integer num=5;//每页5条数据
		Integer jump=(page-1)*5;//跳过了多少条
		return couponsMapper.getEmpCodeVoList(jump, num);
	}
	//获取员工码数量
	@Override
	public Integer getEmpCodeCount() {
		return couponsMapper.getEmpCodeCount();
	}
	//删除员工码和对应的使用记录
	@Override
	@Transactional
	public void deleteEmpCode(String code) {
		Integer row=couponsMapper.deleteEmpCode(code);
		if(row!=1) {
			throw new ServiceException("删除失败，请稍后再试");
		}
		couponsMapper.deleteEmpCodeRecord(code);
		
	}
	//根据姓名或员工码查询对应的使用记录
	@Override
	public List<EmpCodeVo> getOneEmpCodeUseRecord(Integer page, String name, String code) {
		Integer num=5;//每页5条数据
		Integer jump=(page-1)*5;//跳过了多少条
		return couponsMapper.getOneEmpCodeRecordList(jump, num, name, code);
	}
	//根据姓名或者员工码查询其记录条数
	@Override
	public Integer getOneEmpCodeRecordCount(String name, String code) {
		return couponsMapper.getOneEmpCodeRecordCount(name, code);
	}
	//查询员工码
	@Override
	public EmpCode findEmpCodeByCode(String code) {
		EmpCode emp=couponsMapper.findEmpCodeByCode(code);
		return emp;
	}
	//查询优惠卷
	@Override
	public Coupons findCouponsCodeByCode(String code) {
		Coupons cou=couponsMapper.findCouponsCodeByCode(code);
		return cou;
	}
	//修改优惠券状态
	@Override
	public void updateCouponsCdeStatus(String code) {
		couponsMapper.updateCouponsCdeStatus(code);		
	}
	
	
	
	
	
	
}
