package com.xwwx.sewage.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xwwx.sewage.bean.TCsPdaMacAddr;
import com.xwwx.sewage.bean.TCsPdaUser;
import com.xwwx.sewage.common.PageList;
import com.xwwx.sewage.common.Pages;
import com.xwwx.sewage.dao.PdaDao;
import com.xwwx.sewage.service.PdaService;

@Service(value="pdaService")
public class PdaServiceImpl implements PdaService{
	@Autowired
	private PdaDao pdaDao;
	public List<String> getAllPdaMacAddrList() {
		return pdaDao.getAllPdaMacAddrList();
	}
	//分页 mac地址列表
	public PageList getPdaMacAddrList(String orderby, String ascordesc,Pages pages,Map paramMap){
		PageList pl = new PageList();
		pages.setTotalNum(getPdaMacAddrCount(paramMap));
		pages.executeCount();
		paramMap.put("orderby", orderby);
		paramMap.put("ascordesc", ascordesc);
		paramMap.put("count", pages.getPerPageNum());//每页显示数量
		paramMap.put("start", (pages.getPage()-1)*pages.getPerPageNum());//开始记录号
		List<TCsPdaMacAddr> list = pdaDao.getPdaMacAddrList(paramMap);
		pl.setObjectList(list);
		pl.setPages(pages);
		return pl;
	}
	//分页  mac地址数量
	public int getPdaMacAddrCount(Map paramMap) {
		return pdaDao.getPdaMacAddrCount(paramMap);
	}
	public TCsPdaUser getUserByUserIdAndPassword(String pdauserid,String pdapwd) throws Exception{
		return pdaDao.getUserByUserIdAndPassword(pdauserid, pdapwd);
	}
	//插入MAC地址
	public int insertPdaMacAddr(TCsPdaMacAddr tCsPdaMacAddr) throws Exception{
		return pdaDao.insertPdaMacAddr(tCsPdaMacAddr);
	}
	//更新MAC地址
	public int updatePdaMacAddr(TCsPdaMacAddr tCsPdaMacAddr) throws Exception{
		return pdaDao.updatePdaMacAddr(tCsPdaMacAddr);
	}
	//删除MAC地址
	public int deletePdaMacAddr(TCsPdaMacAddr tCsPdaMacAddr) throws Exception{
		return pdaDao.deletePdaMacAddr(tCsPdaMacAddr);
	}
	//分页 pda用户列表
	public PageList getPdaUserList(String orderby, String ascordesc,Pages pages,Map paramMap) {
		PageList pl = new PageList();
		pages.setTotalNum(getPdaUserCount(paramMap));
		pages.executeCount();
		paramMap.put("orderby", orderby);
		paramMap.put("ascordesc", ascordesc);
		paramMap.put("count", pages.getPerPageNum());//每页显示数量
		paramMap.put("start", (pages.getPage()-1)*pages.getPerPageNum());//开始记录号
		List<TCsPdaUser> list = pdaDao.getPdaUserList(paramMap);
		pl.setObjectList(list);
		pl.setPages(pages);
		return pl;
	}
	//分页 pda用户数量
	public int getPdaUserCount(Map paramMap) {
		return pdaDao.getPdaUserCount(paramMap);
	}
	//插入pda用户
	public int insertPdaUser(TCsPdaUser tCsPdaUser) throws Exception{
		return pdaDao.insertPdaUser(tCsPdaUser);
	}
	//更新pda用户
	public int updatePdaUser(TCsPdaUser tCsPdaUser) throws Exception{
		return pdaDao.updatePdaUser(tCsPdaUser);
	}
	//根据ID查找pda用户
	public TCsPdaUser getPdaUserById(int id) {
		return pdaDao.getPdaUserById(id);
	}
	//删除pda用户
	public int deletePdaUser(TCsPdaUser tCsPdaUser) throws Exception{
		return pdaDao.deletePdaUser(tCsPdaUser);
	}
	//插入tid码
	public void insertTid(String tidcode) throws Exception{
		pdaDao.insertTid(tidcode);
	}
	//取tid列表数量
	public int getTidCount() {
		return pdaDao.getTidCount();
	}
	//判断是否存在tid码
	public String getTidByCode(@Param("tidcode") String tidcode) {
		return pdaDao.getTidByCode(tidcode);
	}
	//删除tid码
	public void deleteTidByCode(@Param("tidcode") String tidcode) throws Exception{
		pdaDao.deleteTidByCode(tidcode);
	}
}
