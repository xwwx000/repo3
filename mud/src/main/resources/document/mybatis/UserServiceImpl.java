package com.xwwx.sewage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xwwx.sewage.bean.User;
import com.xwwx.sewage.dao.UserDao;
import com.xwwx.sewage.service.UserService;

@Service(value="userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	public int insertUser(User user) throws Exception {

		return userDao.insert(user);
	}

	public User getUser(String userId) throws Exception {
		return userDao.getUserByUserId(userId);
	}
	public User getUserByUserIdAndId(String userId,int id) throws Exception{
		
		return userDao.getUserByUserIdAndId(userId, id);
	}

	public List<User> getAllUser(User user) {

		return userDao.getAllUser(user);
	}

	public int update(User user) throws Exception {

		return userDao.update(user);
	}

	public User getUserByUserId(String userId) throws Exception {

		return userDao.getUserByUserId(userId);
	}

	public List<User> getUserByIds(List<Integer> ids) {

		return userDao.getUserByIds(ids);
	}

	public int updateUserByMap(Map<String, Object> map) {

		return userDao.updateUserByMap(map);
	}
	
	/**
	 * 测试事务
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int insertUserTestTran(User user){
		int tmp = 0;
		try {
			tmp =  userDao.insert(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new RuntimeException();		
	}
}
