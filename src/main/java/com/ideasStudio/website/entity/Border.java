package com.ideasStudio.website.entity;

/**
 * 边框实体类
 * @author Administrator
 *
 */
public class Border extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Integer borderId; 		//'边框id',
	private String name;			//'边框名称',
	private String keyword;			//'边框关键字 模糊查询使用',
	private String path;			//'边框图片路径',
	private double price;			//'边框价格  米/元'
	private String Appmaterials;	// comment'适用材料',
	public Integer getBorderId() {
		return borderId;
	}
	public void setBorderId(Integer borderId) {
		this.borderId = borderId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getAppmaterials() {
		return Appmaterials;
	}
	public void setAppmaterials(String appmaterials) {
		Appmaterials = appmaterials;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Border [borderId=" + borderId + ", name=" + name + ", keyword=" + keyword + ", path=" + path
				+ ", price=" + price + ", Appmaterials=" + Appmaterials + "]";
	}
	
	

	 
}
