package com.xwwx.sewage.bean;

public class TCsPdaUser {
    private int id;
	private String pdauserid;
	private String pdausername;
	private String pdapwd;
	private String pdadept;
	private int pdatype;
	private int status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPdauserid() {
		return pdauserid;
	}
	public void setPdauserid(String pdauserid) {
		this.pdauserid = pdauserid;
	}
	public String getPdausername() {
		return pdausername;
	}
	public void setPdausername(String pdausername) {
		this.pdausername = pdausername;
	}
	public String getPdapwd() {
		return pdapwd;
	}
	public void setPdapwd(String pdapwd) {
		this.pdapwd = pdapwd;
	}
	public int getPdatype() {
		return pdatype;
	}
	public void setPdatype(int pdatype) {
		this.pdatype = pdatype;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPdadept() {
		return pdadept;
	}
	public void setPdadept(String pdadept) {
		this.pdadept = pdadept;
	}
	@Override
	public String toString() {
		return "TCsPdaUser [id=" + id + ", pdauserid=" + pdauserid + ", pdausername=" + pdausername + ", pdapwd="
				+ pdapwd + ", pdadept=" + pdadept + ", pdatype=" + pdatype + ", status=" + status + "]";
	}
	
}
