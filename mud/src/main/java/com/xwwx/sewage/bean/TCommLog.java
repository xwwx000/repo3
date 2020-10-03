package com.xwwx.sewage.bean;

public class TCommLog {

	private int id;
	private String userid;
	private String username;
	private String processtime;
	private String useripaddress;
	private int logtype;
	private String logdesc;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getProcesstime() {
		return processtime;
	}
	public void setProcesstime(String processtime) {
		this.processtime = processtime;
	}
	public String getUseripaddress() {
		return useripaddress;
	}
	public void setUseripaddress(String useripaddress) {
		this.useripaddress = useripaddress;
	}
	public int getLogtype() {
		return logtype;
	}
	public void setLogtype(int logtype) {
		this.logtype = logtype;
	}
	public String getLogdesc() {
		return logdesc;
	}
	public void setLogdesc(String logdesc) {
		this.logdesc = logdesc;
	}
	
}
