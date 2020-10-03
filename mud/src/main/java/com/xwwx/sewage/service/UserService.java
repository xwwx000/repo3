package com.xwwx.sewage.service;

import java.util.List;
import java.util.Map;

import com.xwwx.sewage.bean.TCommUser;

public interface UserService {

	public TCommUser login(String userId,String userPassword) throws Exception;
	public int updateUser(TCommUser user) throws	 Exception;
	public List<Map<String,String>> getMenuByUserIdAndModuleId(String moduleid,String userid);
	 public int getUserCount();
}
