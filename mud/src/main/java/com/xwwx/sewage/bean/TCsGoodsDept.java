package com.xwwx.sewage.bean;

/**
 * 发货单位
 * @author 可乐罐
 *
 */
public class TCsGoodsDept {

	private int id;
	private String deptcode;
	private String deptname;
	private String deptmark;
	private int status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDeptcode() {
		return deptcode;
	}
	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}
	public String getDeptmark() {
		return deptmark;
	}
	public void setDeptmark(String deptmark) {
		this.deptmark = deptmark;
	}

}
