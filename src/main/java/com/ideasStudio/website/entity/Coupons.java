package com.ideasStudio.website.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
/**
 * 优惠卷实体类
 * @author Administrator
 *
 */
public class Coupons extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String code;//优惠码
	@JsonSerialize(using=ToStringSerializer.class)
	private Double money;//优惠金额
	private Integer status;//状态 0未使用 1已使用
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
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "coupons [id=" + id + ", code=" + code + ", money=" + money + ", status=" + status + "]";
	}
	

}
