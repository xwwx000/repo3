package com.xwwx.sewage.bean;

public class TCsPdaMacAddr {

	public int id;
	public String macaddr;
	public int status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMacaddr() {
		return macaddr;
	}
	public void setMacaddr(String macaddr) {
		this.macaddr = macaddr;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "TCsPdaMacAddr [id=" + id + ", macaddr=" + macaddr + ", status=" + status + "]";
	}
	
}
