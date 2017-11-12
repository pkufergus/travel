package com.travel.service;

import com.travel.pojo.User;

public interface UserService {
	/**
	 * 注册
	 */
	Integer register(String email, String user_name, String password);

	/**
	 * 校验邮箱是否存在
	 */
	Boolean isEmailExist(String email);

	/**
	 * 登录
	 */
	User login(String email, String password);

	Integer activeUser(String email);
	
	Integer findPassword(String email,String username);
	
	Object updatePwd(String email ,String oldPwd,String newPwd);
}
