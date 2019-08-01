package com.ideasStudio.website.entity;

import java.util.Date;
/**
 * 员工码使用记录表
 * @author Administrator
 *
 */
public class EmpCodeRecord extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String code;//员工码
	private Date usedate;//使用日期
	private Double money;//消费金额
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getUsedate() {
		return usedate;
	}
	public void setUsedate(Date usedate) {
		this.usedate = usedate;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	@Override
	public String toString() {
		return "EmpCodeRecord [id=" + id + ", code=" + code + ", usedate=" + usedate + ", money=" + money + "]";
	}
	
}
