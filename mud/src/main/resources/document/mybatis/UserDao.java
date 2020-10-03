package com.xwwx.sewage.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xwwx.sewage.bean.TCommUser;
import com.xwwx.sewage.bean.User;

public interface UserDao {
	 //public User getUserByUserId(@Param("userId")String userId) throws Exception;
	 public User getUserByUserId(String userId) throws Exception;
	 public User getUserByUserIdAndId(@Param("userId") String userId,@Param("id") int id) throws Exception;
	 public List<User> getAllUser(User user);
	 public int insert(User user)throws Exception;
	 public int update(User user)throws Exception;
	 public List<User> getUserByIds(@Param("ids") List<Integer> ids);
	 
	 public int updateUserByMap(Map<String,Object> map);
	 
	 
	 public TCommUser getUserByUserIdAndPassword(@Param("USERID") String userId,@Param("USERPWD") String userPassword) throws Exception;
}
