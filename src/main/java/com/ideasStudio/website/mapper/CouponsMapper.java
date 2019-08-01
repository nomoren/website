package com.ideasStudio.website.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ideasStudio.website.entity.Coupons;
import com.ideasStudio.website.entity.EmpCode;
import com.ideasStudio.website.entity.EmpCodeRecord;
import com.ideasStudio.website.vo.EmpCodeVo;

/**
 * 优惠券mappers
 * @author Administrator
 *
 */
public interface CouponsMapper {
	/**
	 * 新增优惠券
	 * @param coupons
	 * @return
	 */
	Integer addCoupons(Coupons coupons);
	
	/**
	 * 分页查询所有优惠券
	 * @return
	 */
	List<Coupons> getCoupons(@Param("jump")Integer jump,
			 @Param("num")Integer num);
	
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
	Integer deleteCoupons(Integer id);
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
	Integer insertNewEmpCode(EmpCode emp);
	
	/**
	 * 查询员工码
	 * @param code
	 * @return
	 */
	EmpCode findEmpCodeByCode(String code);
	/**
	 * 新增员工码使用记录
	 * @param record
	 * @return
	 */
	Integer insertNewEmpCodeRecode(EmpCodeRecord record);
	/**
	 * 分页查询所有员工码
	 * @param jump
	 * @param num
	 * @return
	 */
	List<EmpCodeVo> getEmpCodeVoList(@Param("jump")Integer jump,@Param("num")Integer num);
	/**
	 * 获取员工码数量
	 * @return
	 */
	Integer getEmpCodeCount();
	/**
	 * 删除一个员工码
	 * @param code
	 * @return
	 */
	Integer deleteEmpCode(String code);
	/**
	 * 删除员工码记录
	 * @param code
	 * @return
	 */
	Integer deleteEmpCodeRecord(String code);
	/**
	 * 根据姓名或者员工码分页查询一个员工的员工码使用记录
	 * @param jump
	 * @param num
	 * @return
	 */
	List<EmpCodeVo> getOneEmpCodeRecordList(@Param("jump")Integer jump,
											@Param("num")Integer num,
											@Param("name")String name,
											@Param("code")String code);
	
	/**
	 * 根据姓名或者员工码查询其记录条数
	 * @param name
	 * @param code
	 * @return
	 */
	Integer getOneEmpCodeRecordCount(@Param("name")String name,
									@Param("code")String code);

	Coupons findCouponsCodeByCode(String code);
}
