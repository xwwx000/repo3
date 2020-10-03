package com.xwwx.sewage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xwwx.sewage.bean.PickList;

public interface PickListDao {

	public  List<PickList> getPickListById(@Param("pid") String pid);
}
