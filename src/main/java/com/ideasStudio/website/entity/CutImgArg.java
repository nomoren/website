package com.ideasStudio.website.entity;

/**
 * 切图的参数的实体类
 * @author Administrator
 *
 */
public class CutImgArg extends BaseEntity {
	
	private Integer dataX;		//矩形的X坐标
	private Integer dataY;		//矩形的Y坐标
	private Integer dataHeight;	//矩形的高度
	private Integer dataWidth; 	//矩形的宽度
	
	public CutImgArg(Integer dataX, Integer dataY, Integer dataHeight,
			Integer dataWidth) {
		super();
		this.dataX = dataX;
		this.dataY = dataY;
		this.dataHeight = dataHeight;
		this.dataWidth = dataWidth;
	}

	public Integer getDataX() {
		return dataX;
	}

	public void setDataX(Integer dataX) {
		this.dataX = dataX;
	}

	public Integer getDataY() {
		return dataY;
	}

	public void setDataY(Integer dataY) {
		this.dataY = dataY;
	}

	public Integer getDataHeight() {
		return dataHeight;
	}

	public void setDataHeight(Integer dataHeight) {
		this.dataHeight = dataHeight;
	}

	public Integer getDataWidth() {
		return dataWidth;
	}

	public void setDataWidth(Integer dataWidth) {
		this.dataWidth = dataWidth;
	}
}
