package com.xwwx.sewage.bean;

public class TCommModule {
	private Integer moduleid;
	private String moduledesc;
	private String filename;
	private String imagename;
	private String target;
	private Integer levelid;
	private Integer toplevelid;
	private Integer visible;
	private Integer status;
	public Integer getModuleid() {
		return moduleid;
	}
	public void setModuleid(Integer moduleid) {
		this.moduleid = moduleid;
	}
	public String getModuledesc() {
		return moduledesc;
	}
	public void setModuledesc(String moduledesc) {
		this.moduledesc = moduledesc;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getImagename() {
		return imagename;
	}
	public void setImagename(String imagename) {
		this.imagename = imagename;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public Integer getLevelid() {
		return levelid;
	}
	public void setLevelid(Integer levelid) {
		this.levelid = levelid;
	}
	public Integer getToplevelid() {
		return toplevelid;
	}
	public void setToplevelid(Integer toplevelid) {
		this.toplevelid = toplevelid;
	}
	public Integer getVisible() {
		return visible;
	}
	public void setVisible(Integer visible) {
		this.visible = visible;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "TCommModule [moduleid=" + moduleid + ", moduledesc=" + moduledesc + ", filename=" + filename
				+ ", imagename=" + imagename + ", target=" + target + ", levelid=" + levelid + ", toplevelid="
				+ toplevelid + ", visible=" + visible + ", status=" + status + "]";
	}
	public TCommModule(Integer moduleid, String moduledesc, String filename, String imagename, String target,
			Integer levelid, Integer toplevelid, Integer visible, Integer status) {
		super();
		this.moduleid = moduleid;
		this.moduledesc = moduledesc;
		this.filename = filename;
		this.imagename = imagename;
		this.target = target;
		this.levelid = levelid;
		this.toplevelid = toplevelid;
		this.visible = visible;
		this.status = status;
	}
	public TCommModule() {
	}
}
