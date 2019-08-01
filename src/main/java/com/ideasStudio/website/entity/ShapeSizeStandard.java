package com.ideasStudio.website.entity;

/**
 * 标准型可选尺寸实体类 宽度高度等尺寸单位厘米
 * @author Administrator
 *
 */
public class ShapeSizeStandard extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private Integer id;//'表id',
	private Integer shapeId;//'图形Id',
	private Integer defaultWidth;		//'图形标准宽度 单位厘米',
	private Integer defaultHeight;		//'图形标准高度 单位厘米',
	private Integer perimeter;			//'图形标准周长 单位米
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
	public Integer getDefaultWidth() {
		return defaultWidth;
	}
	public void setDefaultWidth(Integer defaultWidth) {
		this.defaultWidth = defaultWidth;
	}
	public Integer getDefaultHeight() {
		return defaultHeight;
	}
	public void setDefaultHeight(Integer defaultHeight) {
		this.defaultHeight = defaultHeight;
	}
	public Integer getPerimeter() {
		return perimeter;
	}
	public void setPerimeter(Integer perimeter) {
		this.perimeter = perimeter;
	}
	@Override
	public String toString() {
		return "ShapeSizeStandard [id=" + id + ", shapeId=" + shapeId + ", defaultWidth=" + defaultWidth
				+ ", defaultHeight=" + defaultHeight + ", perimeter=" + perimeter + "]";
	}
	
}
