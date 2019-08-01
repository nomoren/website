package com.ideasStudio.website.entity;

import java.io.Serializable;

/**
 * 商品表实体类
 * @author 赵志斌
 *
 */
public class MaterialEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String material;//材质
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	@Override
	public String toString() {
		return "MaterialEntity [id=" + id + ", material=" + material + "]";
	}
	
	
}
