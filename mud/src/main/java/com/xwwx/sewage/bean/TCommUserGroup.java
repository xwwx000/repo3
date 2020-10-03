package com.xwwx.sewage.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class TCommUserGroup implements Serializable{
	private Integer groupid;
	private String groupdesc;
	private Set<TCommUser> user = new HashSet<TCommUser>();
	/** default constructor */
	public TCommUserGroup() {
	}

	/** full constructor */
	public TCommUserGroup(Integer groupid, String groupdesc) {
		this.groupid = groupid;
		this.groupdesc = groupdesc;
	}

	public Set<TCommUser> getUser() {
		return user;
	}

	public void setUser(Set<TCommUser> user) {
		this.user = user;
	}

	public Integer getGroupid() {
		return this.groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public String getGroupdesc() {
		return this.groupdesc;
	}

	public void setGroupdesc(String groupdesc) {
		this.groupdesc = groupdesc;
	}

	@Override
	public String toString() {
		return "TCommUserGroup [groupid=" + groupid + ", groupdesc=" + groupdesc + "]";
	}

}
