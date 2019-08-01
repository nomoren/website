package com.ideasStudio.website.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ideasStudio.website.entity.Coupons;
import com.ideasStudio.website.entity.EmpCode;
import com.ideasStudio.website.entity.EmpCodeRecord;
import com.ideasStudio.website.vo.EmpCodeVo;
//优惠券服务层接口
public interface CouponsService {

	/**
	 * 新增优惠券
	 * @param coupons
	 * @return
	 */
	void addCoupons(Coupons coupons);
	
	/**
	 * 分页查询所有优惠券
	 * @return
	 */
	List<Coupons> getCoupons(Integer page);

	/**
	 * 查询总数
	 * @return
	 */
	Integer getCount();
	
	/**
	 * 删除一个优惠券
	 * @param id
	 * @return
	 */
	void deleteCoupons(Integer id);
	/**
	 * 修改优惠券状态
	 * @param code
	 * @return
	 */
	void updateCouponsCdeStatus(String code);
	/**
	 * 新增一个员工码
	 * @param emp
	 * @return
	 */
	void insertNewEmpCode(EmpCode emp);
	
	/**
	 * 新增员工码使用记录
	 * @param record
	 * @return
	 */
	void insertNewEmpCodeRecode(EmpCodeRecord record);
	/**
	 * 分页查询员工码
	 * @param page
	 * @return
	 */
	List<EmpCodeVo> getEmpCodeVoList(Integer page);
	/**
	 * 获取员工码数量
	 * @return
	 */
	Integer getEmpCodeCount();
	/**
	 * 删除员工码和对应的使用记录
	 * @param code
	 */
	void deleteEmpCode(String code);
	/**
	 * 根据姓名或员工码查询对应的使用记录
	 * @param page
	 * @param name
	 * @param code
	 * @return
	 */
	List<EmpCodeVo> getOneEmpCodeUseRecord(Integer page,String name,String code);
	/**
	 * 根据姓名或者员工码查询其记录条数
	 * @param name
	 * @param code
	 * @return
	 */
	Integer getOneEmpCodeRecordCount(String name,String code);
	/**
	 * 查询员工码
	 * @param code
	 * @return
	 */
	EmpCode findEmpCodeByCode(String code);
	
	/**
	 * 查询优惠卷
	 * @param code
	 * @return
	 */
	Coupons findCouponsCodeByCode(String code);
}
