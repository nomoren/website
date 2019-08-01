package com.ideasStudio.website.entity;

/**
 * 前端页面传输过来的更新购物车的相关参数，由于数据比较多，所以用到实体类接收比较方便
 * @author Administrator
 *
 */
public class CustCartArg {
	private Integer id;				//订单记录Id对应数据库中第二部操作插入数据库中的记录ID
    private String ordernumber;	//订单号码
    private Integer shape;			//图形ID
    private String material;		//材质
    private String publicKey;		//通过订单记录Id和用户的uid或者uuid生成的MD5秘钥，用来判断传到前端的订单记录id没有被篡改过
    private Integer perimeter;		//周长   单位米
    private Double total;			//前端计算的总价
    private Double paintingPrice;	//画的价格
    private Integer borderId;		//边框的Id
    private Double borderPrice;		//边框的价格(不是总价)
    private Integer sizeWidth;		//定制尺寸的宽度
    private Integer sizeHeight;		//定制尺寸的高度
    private Integer sizeId;			//如果标准尺寸的id，这个方便直接在数据库中查找
    private Boolean adjustable;		//定制的图形是否是标准尺寸还是可调的尺寸
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}
	public Integer getShape() {
		return shape;
	}
	public void setShape(Integer shape) {
		this.shape = shape;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public Integer getPerimeter() {
		return perimeter;
	}
	public void setPerimeter(Integer perimeter) {
		this.perimeter = perimeter;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Double getPaintingPrice() {
		return paintingPrice;
	}
	public void setPaintingPrice(Double paintingPrice) {
		this.paintingPrice = paintingPrice;
	}
	public Integer getBorderId() {
		return borderId;
	}
	public void setBorderId(Integer borderId) {
		this.borderId = borderId==-1?null:borderId;
	}
	public Double getBorderPrice() {
		return borderPrice;
	}
	public void setBorderPrice(Double borderPrice) {
		this.borderPrice = borderPrice;
	}
	public Integer getSizeWidth() {
		return sizeWidth;
	}
	public void setSizeWidth(Integer sizeWidth) {
		this.sizeWidth = sizeWidth;
	}
	public Integer getSizeHeight() {
		return sizeHeight;
	}
	public void setSizeHeight(Integer sizeHeight) {
		this.sizeHeight = sizeHeight;
	}
	public Integer getSizeId() {
		return sizeId;
	}
	public void setSizeId(Integer sizeId) {
		this.sizeId = sizeId==-1?null:sizeId;
	}
	public Boolean getAdjustable() {
		return adjustable;
	}
	public void setAdjustable(Boolean adjustable) {
		this.adjustable = adjustable;
	}
	@Override
	public String toString() {
		return "CustCartArg [id=" + id + ", ordernumber=" + ordernumber + ", shape=" + shape + ", material=" + material
				+ ", publicKey=" + publicKey + ", perimeter=" + perimeter + ", total=" + total + ", paintingPrice="
				+ paintingPrice + ", borderId=" + borderId + ", borderPrice=" + borderPrice + ", sizeWidth=" + sizeWidth
				+ ", sizeHeight=" + sizeHeight + ", sizeId=" + sizeId + ", adjustable=" + adjustable + "]";
	}
}
