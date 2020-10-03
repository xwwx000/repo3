package com.xwwx.sewage.common;

import java.util.List;
/**
 * 功能:封装对象(包含对象列表,页属性)
 * @author liwei
 * @since 2010-05-20
 */
public class PageList {
	private List objectList;
	private Pages pages;

	public PageList() {
	}

	public void setObjectList(List objectList) {
		this.objectList = objectList;
	}

	public void setPages(Pages pages) {
		this.pages = pages;
	}

	public List getObjectList() {
		return objectList;
	}

	public Pages getPages() {
		return pages;
	}
}
