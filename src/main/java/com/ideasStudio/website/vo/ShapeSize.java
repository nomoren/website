package com.ideasStudio.website.vo;

import java.io.Serializable;
import java.util.List;

import com.ideasStudio.website.entity.ShapeSizeStandard;

public class ShapeSize implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Boolean adjustable; 		//是否可调
	private Integer shapeId;			//'图形Id',
	private List<Integer> widths;
	private List<Integer> heights;
	private List<ShapeSizeStandard> SSSs;		//'图形标准宽度 单位厘米',
	private Double area;          //图形面积
	private Double price;         //图形价格
	public Boolean getAdjustable() {
		return adjustable;
	}
	public void setAdjustable(Boolean adjustable) {
		this.adjustable = adjustable;
	}
	public Integer getShapeId() {
		return shapeId;
	}
	public void setShapeId(Integer shapeId) {
		this.shapeId = shapeId;
	}
	public List<Integer> getWidths() {
		return widths;
	}
	public void setWidths(List<Integer> widths) {
		this.widths = widths;
	}
	public List<Integer> getHeights() {
		return heights;
	}
	public void setHeights(List<Integer> heights) {
		this.heights = heights;
	}
	public Double getArea() {
		return area;
	}
	public void setArea(Double area) {
		this.area = area;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public List<ShapeSizeStandard> getSSSs() {
		return SSSs;
	}
	public void setSSSs(List<ShapeSizeStandard> sSSs) {
		this.SSSs = sSSs;
	}
	@Override
	public String toString() {
		return "ShapeSize [adjustable=" + adjustable + ", shapeId=" + shapeId + ", widths=" + widths + ", heights="
				+ heights + ", SSSs=" + SSSs + ", area=" + area + ", price=" + price + "]";
	}
	
	
	
	
	
	
}
