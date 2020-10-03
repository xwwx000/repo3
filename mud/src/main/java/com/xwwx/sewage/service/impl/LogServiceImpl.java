package com.xwwx.sewage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xwwx.sewage.bean.TCommLog;
import com.xwwx.sewage.common.PageList;
import com.xwwx.sewage.common.Pages;
import com.xwwx.sewage.dao.LogDao;
import com.xwwx.sewage.service.LogService;

@Service(value="logService")
public class LogServiceImpl implements LogService{
	@Autowired
	private LogDao logDao;
	public int insertLog(TCommLog log) throws Exception{
		return logDao.insertLog(log);
	}
	public PageList getLogList(String orderby, String ascordesc,Pages pages,Map paramMap){
		PageList pl = new PageList();
		pages.setTotalNum(getLogListCount(paramMap));
		pages.executeCount();
		paramMap.put("orderby", orderby);
		paramMap.put("ascordesc", ascordesc);
		paramMap.put("count", pages.getPerPageNum());//每页显示数量
		paramMap.put("start", (pages.getPage()-1)*pages.getPerPageNum());//开始记录号
		List list = logDao.getLogList(paramMap);
		pl.setObjectList(list);
		pl.setPages(pages);
		return pl;
	}
	public int getLogListCount(Map paramMap) {
		
		return logDao.getLogListCount(paramMap);
	}
	/**
	 * 清空日志
	 */
	public int clearLog(Map paramMap) throws Exception{
		
		return logDao.clearLog(paramMap);
	}
}
