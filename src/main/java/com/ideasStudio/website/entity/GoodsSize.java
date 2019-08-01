package com.ideasStudio.website.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 商品的尺寸和对应价格实体类
 * @author 赵志斌
 *
 */
public class GoodsSize extends BaseEntity implements Comparable<GoodsSize>{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer goodsId;//对应的商品id
	private Double length;//长
	private Double width;//宽
	@JsonSerialize(using=ToStringSerializer.class)
	private Double price;//价格
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public Double getLength() {
		return length;
	}
	public void setLength(Double length) {
		this.length = length;
	}
	public Double getWidth() {
		return width;
	}
	public void setWidth(Double width) {
		this.width = width;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "goodssize [id=" + id + ", goodsId=" + goodsId + ", length=" + length + ", width=" + width + ", price="
				+ price + "]";
	}
	@Override
	public int compareTo(GoodsSize o) {
		if(o.getLength()>this.getLength()&&o.getWidth()>this.getWidth()) {
			return -1;
		}else if(o.getLength()<this.getLength()&&o.getWidth()<this.getWidth()){
			return 1;
		}else {
			return 0;
		}
	}
	

}
