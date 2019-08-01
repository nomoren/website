package com.ideasStudio.website.entity;
/**
 * 员工码实体类
 * @author Administrator
 *
 */



public class EmpCode extends BaseEntity{
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String code;//员工码
	private String name;//姓名
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "EmpCode [id=" + id + ", code=" + code + ", name=" + name + "]";
	}
	
	
}
