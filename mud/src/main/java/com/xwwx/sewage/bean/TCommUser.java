package com.xwwx.sewage.bean;

public class TCommUser {
	private int id;
	private String userid;
	private String username;
	private String userpwd;
	private String deptcode;
	private int groupid;
	private Integer status;
	private String lastloginip;
	private String lastlogintime;
	private String usertoken;
	private int pagenum;
	private TCommDept dept;
	private TCommUserGroup group;

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

	public String getUserpwd() {
		return userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getLastloginip() {
		return lastloginip;
	}

	public void setLastloginip(String lastloginip) {
		this.lastloginip = lastloginip;
	}

	public String getLastlogintime() {
		return lastlogintime;
	}

	public void setLastlogintime(String lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	public String getUsertoken() {
		return usertoken;
	}

	public void setUsertoken(String usertoken) {
		this.usertoken = usertoken;
	}

	public String getDeptcode() {
		return deptcode;
	}

	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	public int getPagenum() {
		return pagenum;
	}

	public void setPagenum(int pagenum) {
		this.pagenum = pagenum;
	}

	public TCommDept getDept() {
		return dept;
	}

	public void setDept(TCommDept dept) {
		this.dept = dept;
	}

	public TCommUserGroup getGroup() {
		return group;
	}

	public void setGroup(TCommUserGroup group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return "TCommUser [id=" + id + ", userid=" + userid + ", username=" + username + ", userpwd=" + userpwd
				+ ", deptcode=" + deptcode + ", groupid=" + groupid + ", status=" + status + ", lastloginip="
				+ lastloginip + ", lastlogintime=" + lastlogintime + ", usertoken=" + usertoken + ", dept=" + dept
				+ ", group=" + group + "]";
	}
}