package com.ideasStudio.website.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 订单类
 * @author 赵志斌 
 *
 */
public class Order extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String uid; //用户id
	private String orderNo; //订单号
	private String recvName; //收货人
	private String recvPhone; //号码
	private String recvTel; //固话
	private String recvDistrict; //省市区
	private String recvZip; //邮编
	private String recvAddress; //详细地址
	@JsonSerialize(using=ToStringSerializer.class)
	private Double pay; //支付金额
	private Integer payStatus; //支付状态 1支付 0未支付
	private Integer outStatus;//发货状态 1发货 0未发货
	private Integer takeStatus;//收货状态 1收货 0未收货
	private Integer payWay;//支付方式 0微信支付 1支付宝支付
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date orderTime; //下单时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date payTime;// 支付时间
	private String giftCard; //礼品卡内容
	private String useCoupons;//是否用了优惠券
	
	public String getUseCoupons() {
		return useCoupons;
	}
	public void setUseCoupons(String useCoupons) {
		this.useCoupons = useCoupons;
	}
	public Integer getPayWay() {
		return payWay;
	}
	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getRecvName() {
		return recvName;
	}
	public void setRecvName(String recvName) {
		this.recvName = recvName;
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
	public String getRecvDistrict() {
		return recvDistrict;
	}
	public void setRecvDistrict(String recvDistrict) {
		this.recvDistrict = recvDistrict;
	}
	public String getRecvZip() {
		return recvZip;
	}
	public void setRecvZip(String recvZip) {
		this.recvZip = recvZip;
	}
	public Double getPay() {
		return pay;
	}
	public void setPay(Double pay) {
		this.pay = pay;
	}
	
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public Integer getOutStatus() {
		return outStatus;
	}
	public void setOutStatus(Integer outStatus) {
		this.outStatus = outStatus;
	}
	public Integer getTakeStatus() {
		return takeStatus;
	}
	public void setTakeStatus(Integer takeStatus) {
		this.takeStatus = takeStatus;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public String getGiftCard() {
		return giftCard;
	}
	public void setGiftCard(String giftCard) {
		this.giftCard = giftCard;
	}
	public String getRecvAddress() {
		return recvAddress;
	}
	public void setRecvAddress(String recvAddress) {
		this.recvAddress = recvAddress;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", uid=" + uid + ", orderNo=" + orderNo + ", recvName=" + recvName + ", recvPhone="
				+ recvPhone + ", recvTel=" + recvTel + ", recvDistrict=" + recvDistrict + ", recvZip=" + recvZip
				+ ", recvAddress=" + recvAddress + ", pay=" + pay + ", payStatus=" + payStatus + ", outStatus="
				+ outStatus + ", takeStatus=" + takeStatus + ", payWay=" + payWay + ", orderTime=" + orderTime
				+ ", payTime=" + payTime + ", giftCard=" + giftCard + ", useCoupons=" + useCoupons + "]";
	}
	
	

}
