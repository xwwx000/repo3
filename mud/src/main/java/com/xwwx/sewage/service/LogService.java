package com.xwwx.sewage.service;

import java.util.List;
import java.util.Map;

import com.xwwx.sewage.bean.TCommLog;
import com.xwwx.sewage.common.PageList;
import com.xwwx.sewage.common.Pages;

public interface LogService {
	public int insertLog(TCommLog log) throws Exception;
	public PageList getLogList(String orderby, String ascordesc,Pages pages,Map paramMap);
	public int getLogListCount(Map paramMap);
	public int clearLog(Map paramMap) throws Exception;
}
