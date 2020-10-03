package com.xwwx.sewage.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.xwwx.sewage.bean.TCommUser;
@Repository("userJdbcTemplateDao")
public class UserDaoImp {
	private static final Log logger = LogFactory.getLog(UserDaoImp.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<TCommUser> getAllUserList() {
		String sql = "select * from user";
		List<TCommUser> list = null;
		try {
			RowMapper<TCommUser> mapper = new RowMapper<TCommUser>() {
				public TCommUser mapRow(ResultSet arg0, int arg1) throws SQLException {
					TCommUser user = new TCommUser();
					user.setUserid(arg0.getString("userid"));
					user.setUsername(arg0.getString("username"));
					return user;
				}
			};
			list = jdbcTemplate.query(sql, new Object[] {}, mapper);
			return (list == null || list.isEmpty()) ? null : list;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	public List<Map<String, String>> getMenuByUserIdAndModuleId(String moduleid, String userid) {

		return null;
	}
}
