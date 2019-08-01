package com.ideasStudio.website.entity;

/**
 * 矩形方块的裁剪数据，每个矩形块的坐标以及裁剪的宽度和高度
 * @author Administrator
 */
public class RectShape extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Integer	rectId;		//'该矩形块id',
	private Integer rectShape;	//'该矩形块所属图形',
	private Integer shapeNum;	//'该矩形块属于图形中的第几块',
	private Float xPoint;		//'该矩形块的X坐标',
	private Float yPoint;		//'该矩形块的Y坐标',
	private Float width;		//'矩形块的宽度',
	private Float height;		//'矩形块的高度',
	public Integer getRectId() {
		return rectId;
	}
	public void setRectId(Integer rectId) {
		this.rectId = rectId;
	}
	public Integer getRectShape() {
		return rectShape;
	}
	public void setRectShape(Integer rectShape) {
		this.rectShape = rectShape;
	}
	public Integer getShapeNum() {
		return shapeNum;
	}
	public void setShapeNum(Integer shapeNum) {
		this.shapeNum = shapeNum;
	}
	public Float getxPoint() {
		return xPoint;
	}
	public void setxPoint(Float xPoint) {
		this.xPoint = xPoint;
	}
	public Float getyPoint() {
		return yPoint;
	}
	public void setyPoint(Float yPoint) {
		this.yPoint = yPoint;
	}
	public Float getWidth() {
		return width;
	}
	public void setWidth(Float width) {
		this.width = width;
	}
	public Float getHeight() {
		return height;
	}
	public void setHeight(Float height) {
		this.height = height;
	}
	@Override
	public String toString() {
		return "RectShape [rectId=" + rectId + ", rectShape=" + rectShape
				+ ", shapeNum=" + shapeNum + ", xPoint=" + xPoint + ", yPoint="
				+ yPoint + ", width=" + width + ", height=" + height + "]";
	}
	
	
}
