package com.ideasStudio.website.entity;



import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 商品信息实体类
 * @author 赵志斌
 *
 */
public class GoodsEntity extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String number;//编号
	private Integer material;//材质
	private Integer bigtype;//风格
	private Integer smalltype;//类型
	private String title;//标题
	private String path;//图片路径
	private String introduce;//商品介绍
	private Integer status;//状态 0下架 1上架
	
	public Integer getBigtype() {
		return bigtype;
	}
	public void setBigtype(Integer bigtype) {
		this.bigtype = bigtype;
	}
	public Integer getSmalltype() {
		return smalltype;
	}
	public void setSmalltype(Integer smalltype) {
		this.smalltype = smalltype;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Integer getMaterial() {
		return material;
	}
	public void setMaterial(Integer material) {
		this.material = material;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	@Override
	public String toString() {
		return "GoodsEntity [id=" + id + ", number=" + number + ", material=" + material + ", bigtype=" + bigtype
				+ ", smalltype=" + smalltype + ", title=" + title + ", path=" + path + ", introduce=" + introduce
				+ ", status=" + status + "]";
	}

	
}
