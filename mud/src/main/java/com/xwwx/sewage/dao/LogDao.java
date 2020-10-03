package com.xwwx.sewage.dao;

import java.util.List;
import java.util.Map;

import com.xwwx.sewage.bean.TCommLog;

public interface LogDao {

	public int insertLog(TCommLog log) throws Exception;
	public List<TCommLog> getLogList(Map paramMap);
	public int getLogListCount(Map paramMap);
	public int clearLog(Map paramMap) throws Exception;
}
