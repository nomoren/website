package com.ideasStudio.website.vo;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 购物车vo类
 * @author 赵志斌
 *
 */
public class CartVo implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer cartId;
	private String uid;
	private Integer cust = 0;		//判断是否是私人订制产品  0为套装产品 1 为私人订制产品
	private Integer goodsId;//商品id
	private Integer sizeId;//尺寸id
	private String goodsTitle;//标题
	private String orderNo;
	@JsonSerialize(using=ToStringSerializer.class)
	private Double goodsPrice;//单价
	private String goodsNo;//编号
	private String goodsNum;//数量
	private String goodsPath;//图片路径
	private Double goodsLength;//长
	private Double goodsWidth;//宽
	@JsonSerialize(using=ToStringSerializer.class)
	private Double money;//单个商品总金额
	
	public Integer getSizeId() {
		return sizeId;
	}
	public void setSizeId(Integer sizeId) {
		this.sizeId = sizeId;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	
	public String getGoodsPath() {
		return goodsPath;
	}
	public void setGoodsPath(String goodsPath) {
		this.goodsPath = goodsPath;
	}
	
	
	public Integer getCartId() {
		return cartId;
	}
	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsTitle() {
		return goodsTitle;
	}
	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}
	public Double getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public String getGoodsNo() {
		return goodsNo;
	}
	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}
	public String getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(String goodsNum) {
		this.goodsNum = goodsNum;
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

	
	public Integer getCust() {
		return cust;
	}
	public void setCust(Integer cust) {
		this.cust = cust;
	}
	
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	@Override
	public String toString() {
		return "CartVo [cartId=" + cartId + ", uid=" + uid + ", goodsId=" + goodsId + ", sizeId=" + sizeId
				+ ", goodsTitle=" + goodsTitle + ", goodsPrice=" + goodsPrice + ", goodsNo=" + goodsNo + ", goodsNum="
				+ goodsNum + ", goodsPath=" + goodsPath + ", goodsLength=" + goodsLength + ", goodsWidth=" + goodsWidth
				+ ", money=" + money + "]";
	}
	
	
}
