package com.xwwx.sewage.bean;

import java.math.BigDecimal;

/**
 * 业务数据对象
 * @author 可乐罐
 *
 */
public class TCsOrder {

	private int id;
	private String snum;
	private String carnum;
	private String goodstype;
	private BigDecimal goodsweight;
	private String sgoodsdept;
	private String sgoodstime;
	private String rgoodstime;
	private String rgoodsdept;
	private String consignor;
	private String consignee;
	private String rfid;
	private int rfidstatus;
	private int rgoodsstatus;
	private int rgoodsostatus;
	private int status;
	private BigDecimal lo;
	private BigDecimal la;
	private int datatype;
	private String remark;
	private int manualflag;
	private int otheri1;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSnum() {
		return snum;
	}
	public void setSnum(String snum) {
		this.snum = snum;
	}
	public String getCarnum() {
		return carnum;
	}
	public void setCarnum(String carnum) {
		this.carnum = carnum;
	}
	public String getGoodstype() {
		return goodstype;
	}
	public void setGoodstype(String goodstype) {
		this.goodstype = goodstype;
	}
	public BigDecimal getGoodsweight() {
		return goodsweight;
	}
	public void setGoodsweight(BigDecimal goodsweight) {
		this.goodsweight = goodsweight;
	}
	public String getSgoodstime() {
		return sgoodstime;
	}
	public void setSgoodstime(String sgoodstime) {
		this.sgoodstime = sgoodstime;
	}
	public String getRgoodstime() {
		return rgoodstime;
	}
	public void setRgoodstime(String rgoodstime) {
		this.rgoodstime = rgoodstime;
	}
	public String getSgoodsdept() {
		return sgoodsdept;
	}
	public void setSgoodsdept(String sgoodsdept) {
		this.sgoodsdept = sgoodsdept;
	}
	public String getRgoodsdept() {
		return rgoodsdept;
	}
	public void setRgoodsdept(String rgoodsdept) {
		this.rgoodsdept = rgoodsdept;
	}
	public String getConsignor() {
		return consignor;
	}
	public void setConsignor(String consignor) {
		this.consignor = consignor;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public int getRgoodsstatus() {
		return rgoodsstatus;
	}
	public void setRgoodsstatus(int rgoodsstatus) {
		this.rgoodsstatus = rgoodsstatus;
	}
	public BigDecimal getLo() {
		return lo;
	}
	public void setLo(BigDecimal lo) {
		this.lo = lo;
	}
	public BigDecimal getLa() {
		return la;
	}
	public void setLa(BigDecimal la) {
		this.la = la;
	}
	public int getRgoodsostatus() {
		return rgoodsostatus;
	}
	public void setRgoodsostatus(int rgoodsostatus) {
		this.rgoodsostatus = rgoodsostatus;
	}
	public int getDatatype() {
		return datatype;
	}
	public void setDatatype(int datatype) {
		this.datatype = datatype;
	}
	public String getRfid() {
		return rfid;
	}
	public void setRfid(String rfid) {
		this.rfid = rfid;
	}
	public int getRfidstatus() {
		return rfidstatus;
	}
	public void setRfidstatus(int rfidstatus) {
		this.rfidstatus = rfidstatus;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getManualflag() {
		return manualflag;
	}
	public void setManualflag(int manualflag) {
		this.manualflag = manualflag;
	}
	public int getOtheri1() {
		return otheri1;
	}
	public void setOtheri1(int otheri1) {
		this.otheri1 = otheri1;
	}
	
}
