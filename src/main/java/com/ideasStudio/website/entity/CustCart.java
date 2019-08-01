package com.ideasStudio.website.entity;

import java.util.Date;

/**
 * 私人订制购物车相关信息
 * @author Administrator
 *
 */
public class CustCart extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Integer id;					//'购物车自增ID',
	private String uid;					//'用户ID',
	private String uuid;				//'用户临时uuid'
	private String material;			//'材质'
	private Integer shapeid;			//'图形的id' ,
	private Integer custornum;			//'定制数量',
	private String ordernum;			//'私人订制订单号码',
	private String path;				//'私人订制用户上传资源路径',
	private Integer width;				//图形宽度
	private Integer height;				//图形高度
	private Double price;				//'私人订制价格',
	private Double paintingPrice;		//'单独画的价格 不算边框'
	private Double total;				//'私人订制总价',
	private Integer borderId;			//'边框编号',
	private Double borderprice;			//'边框价格',
	private Date createtime;			//'创建时间',
	private String createuid;			//'创建者id',
	private String createuuid;			//'创建者uuid'
	private String createname;			//'创建者姓名',
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
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public Integer getShapeid() {
		return shapeid;
	}
	public void setShapeid(Integer shapeid) {
		this.shapeid = shapeid;
	}
	public Integer getCustornum() {
		return custornum;
	}
	public void setCustornum(Integer custornum) {
		this.custornum = custornum;
	}
	public String getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Integer getBorderId() {
		return borderId;
	}
	public void setBorderId(Integer borderId) {
		this.borderId = borderId;
	}
	public Double getBorderprice() {
		return borderprice;
	}
	public void setBorderprice(Double borderprice) {
		this.borderprice = borderprice;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getCreateuid() {
		return createuid;
	}
	public void setCreateuid(String createuid) {
		this.createuid = createuid;
	}
	public String getCreatename() {
		return createname;
	}
	public void setCreatename(String createname) {
		this.createname = createname;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getCreateuuid() {
		return createuuid;
	}
	public void setCreateuuid(String createuuid) {
		this.createuuid = createuuid;
	}
	public Double getPaintingPrice() {
		return paintingPrice;
	}
	public void setPaintingPrice(Double paintingPrice) {
		this.paintingPrice = paintingPrice;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	@Override
	public String toString() {
		return "CustCart [id=" + id + ", uid=" + uid + ", uuid=" + uuid + ", material=" + material + ", shapeid="
				+ shapeid + ", custornum=" + custornum + ", ordernum=" + ordernum + ", path=" + path + ", width="
				+ width + ", height=" + height + ", price=" + price + ", paintingPrice=" + paintingPrice + ", total="
				+ total + ", borderId=" + borderId + ", borderprice=" + borderprice + ", createtime=" + createtime
				+ ", createuid=" + createuid + ", createuuid=" + createuuid + ", createname=" + createname + "]";
	}
}
