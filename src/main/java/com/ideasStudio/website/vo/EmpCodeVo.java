package com.ideasStudio.website.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ideasStudio.website.entity.BaseEntity;

/**
 * 员工码vo类
 * @author Administrator
 *
 */
public class EmpCodeVo extends BaseEntity{
	private static final long serialVersionUID = 1L;

	private Integer empId;
	private String empName;
	private String empCode;
	@JsonSerialize(using=ToStringSerializer.class)
	private Double singleMoney;//单次消费金额
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date usedate;//每次使用的时间
	private Integer useCount;//使用次数
	@JsonSerialize(using=ToStringSerializer.class)
	private Double money;//累计消费金额
	
	
	public Double getSingleMoney() {
		return singleMoney;
	}
	public void setSingleMoney(Double singleMoney) {
		this.singleMoney = singleMoney;
	}
	public Date getUseDate() {
		return usedate;
	}
	public void setUseDate(Date useDate) {
		this.usedate = useDate;
	}
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public Integer getUseCount() {
		return useCount;
	}
	public void setUseCount(Integer useCount) {
		this.useCount = useCount;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	@Override
	public String toString() {
		return "EmpCodeVo [empId=" + empId + ", empName=" + empName + ", empCode=" + empCode + ", singleMoney="
				+ singleMoney + ", useDate=" + usedate + ", useCount=" + useCount + ", money=" + money + "]";
	}
	
}
