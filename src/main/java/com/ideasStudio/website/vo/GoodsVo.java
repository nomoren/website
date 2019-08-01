package com.ideasStudio.website.vo;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ideasStudio.website.entity.BaseEntity;
import com.ideasStudio.website.entity.GoodsSize;

/**
 * 商品的vo类
 * @author 赵志斌
 *
 */
public class GoodsVo extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String number;//编号
	private String material;//材质
	private String title;//产品名称
	private String path;//图片路径
	private String introduce;//商品介绍
	private Integer status;//状态
	@JsonSerialize(using=ToStringSerializer.class)
	private Double price;//最小价格
	private Integer bigtype;//类型
	private Integer smalltype;//分类
 	private List<GoodsSize> sizeList;//每个商品的所有尺寸
 	
	public Integer getBigtype() {
		return bigtype;
	}
	public void setBigtype(Integer bigtype) {
		this.bigtype = bigtype;
	}
	public Integer getSmalltype() {
		return smalltype;
	}
	public void setSmalltype(Integer smalltype) {
		this.smalltype = smalltype;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public List<GoodsSize> getSizeList() {
		return sizeList;
	}
	public void setSizeList(List<GoodsSize> sizeList) {
		this.sizeList = sizeList;
	}
	@Override
	public String toString() {
		return "GoodsVo [id=" + id + ", number=" + number + ", material=" + material + ", title=" + title + ", path="
				+ path + ", introduce=" + introduce + ", status=" + status + ", price=" + price + ", bigtype=" + bigtype
				+ ", smalltype=" + smalltype + ", sizeList=" + sizeList + "]";
	}
	
}
