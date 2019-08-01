package com.ideasStudio.website.entity;

/**
 * 定制过程中图片的张数和路径
 * @author Administrator
 *
 */
public class CustPiece extends BaseEntity {

	private Integer piece;		//多张定制的第几张
	private String path;		//改张图片的path路径
	private String ordernumber; //订单文件夹
	
	public CustPiece() {
	}
	
	public CustPiece(Integer piece,String path){
		this.piece = piece;
		this.path = path;
	}

	public Integer getPiece() {
		return piece;
	}

	public void setPiece(Integer piece) {
		this.piece = piece;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getOrdernumber() {
		return ordernumber;
	}

	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}
	
	

}
