package com.ideasStudio.website.entity;

/**
 * 图形url和其他参数实体类
 * @author Administrator
 *
 */
public class ShapeUrl {
	private Integer id;			//数据库表shapeurl Id
	private Integer shapeId;   //图形id
	private String url;			//图形的页面返回路径
	private Integer  adjustable; //图形是标准尺寸还是可调尺寸
	private Double area;          //图形面积
	private Double price;         //图形价格
	private Double unit ;		 //计算单价
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getShapeId() {
		return shapeId;
	}
	public void setShapeId(Integer shapeId) {
		this.shapeId = shapeId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getAdjustable() {
		return adjustable;
	}
	public void setAdjustable(Integer adjustable) {
		this.adjustable = adjustable;
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
	public Double getUnit() {
		return unit;
	}
	public void setUnit(Double unit) {
		this.unit = unit;
	}
	@Override
	public String toString() {
		return "ShapeUrl [id=" + id + ", shapeId=" + shapeId + ", url=" + url + ", adjustable=" + adjustable + ", area="
				+ area + ", price=" + price + ", unit=" + unit + "]";
	}
	
	
	
}
