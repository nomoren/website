package com.ideasStudio.website.entity;

import java.util.List;

/**
 * 非标准型可调拖动尺寸实体类  宽度高度等尺寸单位厘米
 * @author Administrator
 *
 */
public class ShapeSizeNonStandard extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private Integer id;//'表id',
	private Integer shapeId;//'图形Id',
	private List<Integer> widths;
	private List<Integer> heights;
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
	@Override
	public String toString() {
		return "ShapeSizeNonStandard [id=" + id + ", shapeId=" + shapeId + ", widths=" + widths + ", heights=" + heights
				+ "]";
	}
	
	
}
