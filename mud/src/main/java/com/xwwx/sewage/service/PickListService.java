package com.xwwx.sewage.service;

import java.util.List;

import com.xwwx.sewage.bean.PickList;

public interface PickListService {
	public  List<PickList> getPickListById(String pid);
}
