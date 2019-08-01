package com.ideasStudio.website.entity;

/**
 * 私人订制图形实体类
 * @author Administrator
 *
 */
public class Cust {
	
	private Integer id;    //主键的id
	private Integer shapeId; //图形的id
	private String url;   //图形的对应的url地址
	
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
	
	@Override
	public String toString() {
		return "Cust [id=" + id + ", shapeId=" + shapeId + ", url=" + url + "]";
	}
	
	
	
	

}
