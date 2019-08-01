package com.ideasStudio.website.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 订单商品表
 * @author 赵志斌
 *
 */
public class OrderItem extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String orderId;//订单号
	private Integer goodsId;//商品id
	private String goodsImage;//图片路径
	private String goodsTitle;//标题
	@JsonSerialize(using=ToStringSerializer.class)
	private Double goodsLength;//长
	@JsonSerialize(using=ToStringSerializer.class)
	private Double goodsWidth;//宽
	@JsonSerialize(using=ToStringSerializer.class)
	private Double goodsPrice;//价格
	private Integer goodsNum;//数量
	private String goodsNo;//商品编号
	@JsonSerialize(using=ToStringSerializer.class)
	private Double allMoney;//单价*数量
	
	public Double getAllMoney() {
		return allMoney;
	}
	public void setAllMoney(Double allMoney) {
		this.allMoney = allMoney;
	}
	public String getGoodsNo() {
		return goodsNo;
	}
	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsImage() {
		return goodsImage;
	}
	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}
	public String getGoodsTitle() {
		return goodsTitle;
	}
	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}
	public Double getGoodsLength() {
		return goodsLength;
	}
	public void setGoodsLength(Double goodsLength) {
		this.goodsLength = goodsLength;
	}
	public Double getGoodsWidth() {
		return goodsWidth;
	}
	public void setGoodsWidth(Double goodsWidth) {
		this.goodsWidth = goodsWidth;
	}
	public Double getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public Integer getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}
	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", orderId=" + orderId + ", goodsId=" + goodsId + ", goodsImage=" + goodsImage
				+ ", goodsTitle=" + goodsTitle + ", goodsLength=" + goodsLength + ", goodsWidth=" + goodsWidth
				+ ", goodsPrice=" + goodsPrice + ", goodsNum=" + goodsNum + ", goodsNo=" + goodsNo + ", allMoney="
				+ allMoney + "]";
	}
	

}
