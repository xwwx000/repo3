package com.xwwx.sewage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xwwx.sewage.bean.PickList;
import com.xwwx.sewage.dao.PickListDao;
import com.xwwx.sewage.service.PickListService;

@Service(value="pickListService")
public class PickListServiceImpl implements PickListService{

	@Autowired
	private PickListDao pickListDao;
	public List<PickList> getPickListById(String pid) {
		return pickListDao.getPickListById(pid);
	}
}
