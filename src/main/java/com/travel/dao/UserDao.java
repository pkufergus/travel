package com.travel.dao;

import java.util.List;

import com.travel.pojo.User;

public interface UserDao {
	/**
	  * 注册
	  */
	Integer register(User user);
	
	/**
	 * 校验邮箱是否存在
	 */
	List<User> getObjByEmail(String email);
	
	/**
	 * 登录
	 */
	List<User> login(String email , String password);
	
	Integer activeUser(String email);
	
	Integer findPassword(String email,String password);
	
	List<User> checkFindPwd(String email,String username);
	
	Integer updatePwd(String email,String pwd);
}
