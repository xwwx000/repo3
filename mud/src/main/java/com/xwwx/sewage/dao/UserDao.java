package com.xwwx.sewage.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xwwx.sewage.bean.TCommModule;
import com.xwwx.sewage.bean.TCommUser;

public interface UserDao {
 
	 public TCommUser getUserByUserIdAndPassword(@Param("USERID") String userId,@Param("USERPWD") String userPassword) throws Exception;
	 public TCommUser getUserByUserId(@Param("USERID") String userId) throws Exception;
	 public int updateUser(TCommUser user) throws Exception;
	 public List<TCommModule> getMenuByUserIdAndModuleId(@Param("moduleid") String moduleid,@Param("userid") String userid);
	 public int getUserCount();
}
