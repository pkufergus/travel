package com.travel.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.dao.MailDao;
import com.travel.dao.UserDao;
import com.travel.pojo.Mailconfig;
import com.travel.pojo.User;
import com.travel.service.UserService;
import com.travel.util.ConstantUtil;
import com.travel.util.DateUtil;
import com.travel.util.MD5Util;
import com.travel.util.ServiceUtil;

@Service
public class UserServiceImpl implements UserService {
	private static Logger logger = LoggerFactory
			.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao uDao;

	@Autowired
	private MailDao mDao;

	/**
	  * 
	  */
	@Override
	public Integer register(String email, String user_name, String password) {
		Integer regFlag = 0;
		User user = new User();
		user.setEmail(email);
		user.setUser_name(user_name);
		user.setPassword(password);
		user.setCtime(DateUtil.currentTimeStamp());
		try {
			regFlag = uDao.register(user);
			logger.info("register flag === " + regFlag);
			if (regFlag != 0) {
				List<Mailconfig> mList = mDao.getMailConfigList();
				logger.info("mList is ==="+mList);
				if (mList != null) {
					if (mList.size() > 0) {
						Mailconfig mc = mList.get(0);
						Boolean sendMailFlag = ServiceUtil.sendActiveMail(
								email, mc);
						logger.info("sendMailFlag===" + sendMailFlag);
					}
				} else {
					logger.error("mailconfig is null you should set send mail sever config");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return regFlag;
		}
		return regFlag;
	}

	/**
	 * 
	 */
	@Override
	public Boolean isEmailExist(String email) {
		List<User> userList = uDao.getObjByEmail(email);
		logger.info("userList"+userList);
		if (userList != null) {
			if (userList.size() > 0) {
				logger.info("email exists");
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 */
	@Override
	public User login(String email, String password) {
		User user = null;
		List<User> userList = uDao.login(email, password);
		if (userList != null) {
			if (!userList.isEmpty()) {
				user = userList.get(0);
				logger.info(user.getEmail());
			}
		}else{
			logger.info("userList is null");
			return user;
		}
		return user;
	}

	@Override
	public Integer activeUser(String email) {
		int activeFlag = 0;
		List<User> mList = uDao.getObjByEmail(email);
		
		if (mList.size() > 0) {
			activeFlag = uDao.activeUser(email);
		} else {
			activeFlag = -1;// 不存在
			logger.info("email is not exist");
			return activeFlag;
		}
		logger.info("activeFlag === "+activeFlag);
		return activeFlag;
	}

	@Override
	public Integer findPassword(String email,String username) {
		String password = ConstantUtil.INIT_PWD;
		List<User> uList = uDao.checkFindPwd(email, username);
		
		if(uList == null || uList.isEmpty()){
			return -2;
		}
		 
		int flag = uDao.findPassword(email, MD5Util.ToSecret(password));
		logger.info("update pwd flag === "+flag);
		if (flag != 0) {
			List<Mailconfig> mList = mDao.getMailConfigList();
			if (mList != null) {
				if (mList.size() > 0) {
					Mailconfig mc = mList.get(0);
					Boolean sendMailFlag = ServiceUtil.sendFindPwdMail(email,
							mc, password);
					logger.info("sendPwdMailFlag===" + sendMailFlag);
				}
			}else{
				logger.error("mailconfig is null you should set send mail sever config");
			}
		}
		return flag;
	}

	@Override
	public Object updatePwd(String email ,String oldPwd, String newPwd) {
		User user = null;
		List<User> userList = uDao.login(email, oldPwd);
		if (userList != null) {
			if (!userList.isEmpty()) {
				user = userList.get(0);
				logger.info(user.getEmail());
				int updatePwdFlag = uDao.updatePwd(email, newPwd);
				logger.info("updatePwdFlag==="+updatePwdFlag);
				return updatePwdFlag;
			}else {
				return "WRONG";
			}
		}else{
			logger.info("userList is null");
			return "WRONG";
		}
		//return user;
	}

}
