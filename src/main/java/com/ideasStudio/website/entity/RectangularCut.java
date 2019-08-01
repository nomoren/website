package com.ideasStudio.website.entity;

/**
 * 矩形的裁剪框
 * @author Administrator
 *
 */
public class RectangularCut {

	private Float X;
	private Float Y;
	private Float Width;
	private Float Height;
	
	public RectangularCut() {
		// TODO Auto-generated constructor stub
	}
	public RectangularCut(Float X,Float Y,Float Width,Float Height) {
		this.X = X;
		this.Y = Y;
		this.Width =Width;
		this.Height = Height;
	}
	public Float getX() {
		return X;
	}
	public void setX(Float x) {
		X = x;
	}
	public Float getY() {
		return Y;
	}
	public void setY(Float y) {
		Y = y;
	}
	public Float getWidth() {
		return Width;
	}
	public void setWidth(Float width) {
		Width = width;
	}
	public Float getHeight() {
		return Height;
	}
	public void setHeight(Float height) {
		Height = height;
	}


}
