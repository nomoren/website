package com.ideasStudio.website.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 购物车实体类
 * @author 赵志斌
 *
 */
public class Cart extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String uid;
	private Integer goodsId;
	private Integer sizeId;
	private Integer goodsNum;
	@JsonSerialize(using=ToStringSerializer.class)
	private Double allMoney;
	
	public Integer getSizeId() {
		return sizeId;
	}
	public void setSizeId(Integer sizeId) {
		this.sizeId = sizeId;
	}
	public Double getAllMoney() {
		return allMoney;
	}
	public void setAllMoney(Double allMoney) {
		this.allMoney = allMoney;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public Integer getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}
	@Override
	public String toString() {
		return "Cart [id=" + id + ", uid=" + uid + ", goodsId=" + goodsId + ", sizeId=" + sizeId + ", goodsNum="
				+ goodsNum + ", allMoney=" + allMoney + "]";
	}
	
	
}
