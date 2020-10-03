package com.xwwx.sewage.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xwwx.sewage.bean.TCommModule;
import com.xwwx.sewage.bean.TCommUser;
import com.xwwx.sewage.common.StringUtils;
import com.xwwx.sewage.dao.UserDao;
import com.xwwx.sewage.service.UserService;

@Service(value="userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;

	public TCommUser login(String userId, String userPassword) throws Exception {
		if(StringUtils.isEmpty(userId)){
            return null;
        }
		TCommUser user = userDao.getUserByUserIdAndPassword(userId,userPassword);
        return user;
	}

	public int updateUser(TCommUser user) throws	 Exception{
		
		return userDao.updateUser(user);
	}
	
	public List<Map<String,String>> getMenuByUserIdAndModuleId(String moduleid,String userid){
		List<TCommModule> list = userDao.getMenuByUserIdAndModuleId(moduleid,userid);
		List mlist = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			TCommModule module = (TCommModule) list.get(i);
			Map<String, String> maptmp = new HashMap<String, String>();
			maptmp.put("id", String.valueOf(module.getModuleid()));
			maptmp.put("levelid", String.valueOf(module.getLevelid()));
			maptmp.put("name", module.getModuledesc());
			maptmp.put("pid", String.valueOf(module.getToplevelid()));
			maptmp.put("target", module.getTarget());
			maptmp.put("iconOpen", "<app:iniPath />/js/tool/ztree/img/diy/1_open.png");
			maptmp.put("iconClose", "<app:iniPath />/js/tool/ztree/img/diy/1_close.png");
			mlist.add(maptmp);
		}
		return mlist;
	}
	 public int getUserCount() {
		 return userDao.getUserCount();
	 }
}
