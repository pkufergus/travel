package com.travel.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.travel.dao.UserDao;
import com.travel.pojo.User;

@Repository
public class UserDaoImpl implements UserDao {
	private static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Integer register(User user) {
		Integer regFlag = 0;
		String sql = "INSERT INTO `user` (`email`,`password`,`user_name`,`ctime`) values (?,?,?,?)";
		try{
			regFlag = jdbcTemplate.update(sql,user.getEmail(),user.getPassword(),user.getUser_name(),
					user.getCtime());
			logger.info("regFlag==="+regFlag);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return regFlag;
		}
		return regFlag;
	}

	@Override
	public List<User> getObjByEmail(String email) {
		String sql = "SELECT `id`,`email`,`password`,`ctime`,`mtime`,`status`,`user_name`"
				+ " FROM `user` WHERE `email` = ?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class),email);
	}

	@Override
	public List<User> login(String email, String password) {
		String sql = "SELECT `id`,`email`,`password`,`ctime`,`mtime`,`status`,`user_name` "
				+ "FROM `user` WHERE `email`=? AND `password`=?";
				
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class),email,password);
	}

	@Override
	public Integer activeUser(String email) {
		String sql = "UPDATE `user` SET `status`=1 WHERE `email`=?";
		return jdbcTemplate.update(sql,email);
	}

	@Override
	public Integer findPassword(String email, String password) {
		String sql = "UPDATE `user` SET `password`=? WHERE `email`=?";
		return jdbcTemplate.update(sql,password,email);
	}

	@Override
	public List<User> checkFindPwd(String email, String username) {
		String sql = "select  `id`,`email`,`password`,`ctime`,`mtime`,`status`,`user_name` "
				+ " FROM `user` WHERE `email`=? AND `user_name`=?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class),email,username);
	}

	@Override
	public Integer updatePwd(String email, String pwd) {
		final String sql = "UPDATE `user` SET password=? WHERE email=?";
		logger.info("email=="+email+",pwd=="+pwd+",sql=="+sql);
		return jdbcTemplate.update(sql,pwd,email);
	}

}
