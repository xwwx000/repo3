package com.xwwx.sewage.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xwwx.sewage.bean.TCsPdaMacAddr;
import com.xwwx.sewage.bean.TCsPdaUser;
import com.xwwx.sewage.common.PageList;
import com.xwwx.sewage.common.Pages;

public interface PdaService {
	public List<String> getAllPdaMacAddrList();
	//分页 mac地址列表
	public PageList getPdaMacAddrList(String orderby, String ascordesc,Pages pages,Map paramMap);
	//分页  mac地址数量
	public int getPdaMacAddrCount(Map paramMap);
	public TCsPdaUser getUserByUserIdAndPassword(String pdauserid,String pdapwd) throws Exception;
	//插入MAC地址
	public int insertPdaMacAddr(TCsPdaMacAddr tCsPdaMacAddr) throws Exception;
	//更新MAC地址
	public int updatePdaMacAddr(TCsPdaMacAddr tCsPdaMacAddr) throws Exception;
	//删除MAC地址
	public int deletePdaMacAddr(TCsPdaMacAddr tCsPdaMacAddr) throws Exception;
	//分页 pda用户列表
	public PageList getPdaUserList(String orderby, String ascordesc,Pages pages,Map paramMap);
	//分页 pda用户数量
	public int getPdaUserCount(Map paramMap);
	//插入pda用户
	public int insertPdaUser(TCsPdaUser tCsPdaUser) throws Exception;
	//更新pda用户
	public int updatePdaUser(TCsPdaUser tCsPdaUser) throws Exception;
	//根据ID查找pda用户
	public TCsPdaUser getPdaUserById(int id);
	//删除pda用户
	public int deletePdaUser(TCsPdaUser tCsPdaUser) throws Exception;
	//插入tid码
	public void insertTid(String tidcode) throws Exception;
	//取tid列表数量
	public int getTidCount();
	//判断是否存在tid码
	public String getTidByCode(@Param("tidcode") String tidcode);
	//删除tid码
	public void deleteTidByCode(@Param("tidcode") String tidcode) throws Exception;
}
