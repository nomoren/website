package com.ideasStudio.website.entity;

/**
 * 私人订制图形数据
 * @author Administrator
 *
 */
public class Shape extends BaseEntity{

	 private static final long serialVersionUID = 1L;
	 private Integer id;		    //表ID
	 private Integer shapeId;	    //图形Id
	 private String shapeNo;	    //图形编号
	 private String titleImgURL;	//图形样品图
	 private String shapeName;     //图形名称
	 private Integer adjustable;   //图形是否可调  这里作用备用，目前这块的工能在表shapeurl里面
	 private String shapeTitle;    //图形标题内容
	 private String material;      //图形适用材料
	 private Double price;			//起步价
	 private Integer width;			//宽度
	 private Integer height;		//高度
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
	public String getShapeNo() {
		return shapeNo;
	}
	public void setShapeNo(String shapeNo) {
		this.shapeNo = shapeNo;
	}
	public String getTitleImgURL() {
		return titleImgURL;
	}
	public void setTitleImgURL(String titleImgURL) {
		this.titleImgURL = titleImgURL;
	}
	public String getShapeName() {
		return shapeName;
	}
	public void setShapeName(String shapeName) {
		this.shapeName = shapeName;
	}
	public Integer getAdjustable() {
		return adjustable;
	}
	public void setAdjustable(Integer adjustable) {
		this.adjustable = adjustable;
	}
	public String getShapeTitle() {
		return shapeTitle;
	}
	public void setShapeTitle(String shapeTitle) {
		this.shapeTitle = shapeTitle;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public Double getPrice() {
		return price;
	}
	public Integer getWidth() {
		return width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	@Override
	public String toString() {
		return "Shape [id=" + id + ", shapeId=" + shapeId + ", shapeNo=" + shapeNo + ", titleImgURL=" + titleImgURL
				+ ", shapeName=" + shapeName + ", adjustable=" + adjustable + ", shapeTitle=" + shapeTitle
				+ ", material=" + material + ", price=" + price + ", width=" + width + ", height=" + height + "]";
	}

	
	
}
