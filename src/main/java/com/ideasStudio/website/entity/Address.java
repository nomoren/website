package com.ideasStudio.website.entity;
/**
 * 收货地址
 * @author admin
 *
 */
public class Address extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String uid;
	private String recvName;
	private Integer recvProvince;
	private Integer recvCity;
	private Integer recvArea;
	private String recvDistrict;
	private String recvAddress;
	private String recvPhone;
	private String recvTel;
	private String recvZip;
	private Integer isDefault;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getRecvName() {
		return recvName;
	}
	public void setRecvName(String recvName) {
		this.recvName = recvName;
	}
	public Integer getRecvProvince() {
		return recvProvince;
	}
	public void setRecvProvince(Integer recvProvince) {
		this.recvProvince = recvProvince;
	}
	public Integer getRecvCity() {
		return recvCity;
	}
	public void setRecvCity(Integer recvCity) {
		this.recvCity = recvCity;
	}
	public Integer getRecvArea() {
		return recvArea;
	}
	public void setRecvArea(Integer recvArea) {
		this.recvArea = recvArea;
	}
	
	public String getRecvDistrict() {
		return recvDistrict;
	}
	public void setRecvDistrict(String recvDistrict) {
		this.recvDistrict = recvDistrict;
	}
	public String getRecvAddress() {
		return recvAddress;
	}
	public void setRecvAddress(String recvAddress) {
		this.recvAddress = recvAddress;
	}
	public String getRecvPhone() {
		return recvPhone;
	}
	public void setRecvPhone(String recvPhone) {
		this.recvPhone = recvPhone;
	}
	public String getRecvTel() {
		return recvTel;
	}
	public void setRecvTel(String recvTel) {
		this.recvTel = recvTel;
	}
	public String getRecvZip() {
		return recvZip;
	}
	public void setRecvZip(String recvZip) {
		this.recvZip = recvZip;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	@Override
	public String toString() {
		return "Address [id=" + id + ", uid=" + uid + ", recvName=" + recvName + ", recvProvince=" + recvProvince
				+ ", recvCity=" + recvCity + ", recvArea=" + recvArea + ", recvDistrct=" + recvDistrict
				+ ", recvAddress=" + recvAddress + ", recvPhone=" + recvPhone + ", recvTel=" + recvTel + ", recvZip="
				+ recvZip + ", isDefault=" + isDefault + "]";
	}
	
	
	

}
