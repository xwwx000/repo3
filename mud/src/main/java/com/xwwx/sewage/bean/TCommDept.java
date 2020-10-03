package com.xwwx.sewage.bean;

import java.io.Serializable;

public class TCommDept implements Serializable{

	private String deptcode;
	private String deptname;
	private String upcode;
	private String scopecode;
	private String manager;
	private Integer seq;
	private Integer cstatus;
	private String msg;

	public String getDeptcode() {
		return deptcode;
	}
	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getUpcode() {
		return upcode;
	}
	public void setUpcode(String upcode) {
		this.upcode = upcode;
	}
	public String getScopecode() {
		return scopecode;
	}
	public void setScopecode(String scopecode) {
		this.scopecode = scopecode;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}

	public Integer getSeq() {
		return seq;
	}
	public Integer getCstatus() {
		return cstatus;
	}
	public void setCstatus(Integer cstatus) {
		this.cstatus = cstatus;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public TCommDept() {
	}
	public TCommDept(String deptcode, String deptname, String upcode, String scopecode, String manager, Integer seq,
			Integer cstatus, String msg) {
		super();
		this.deptcode = deptcode;
		this.deptname = deptname;
		this.upcode = upcode;
		this.scopecode = scopecode;
		this.manager = manager;
		this.seq = seq;
		this.cstatus = cstatus;
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "TCommDept [deptcode=" + deptcode + ", deptname=" + deptname + ", upcode=" + upcode + ", scopecode="
				+ scopecode + ", manager=" + manager + ", seq=" + seq + ", cstatus=" + cstatus + ", msg=" + msg + "]";
	}

}
