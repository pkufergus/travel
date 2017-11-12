package com.travel.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.travel.pojo.User;
import com.travel.service.UserService;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	private static Logger logger = LoggerFactory
			.getLogger(UserController.class);

	@Autowired
	private UserService uService;

	@Autowired
	private HttpSession session;

	@ResponseBody
	@RequestMapping(value = { "/login.json" }, method = { RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	public Object login(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("email") String email,
			@RequestParam("password") String password,@RequestParam("checkCode")String checkCode) {
		logger.info("email:" + email + ",password:" + password);
		String ckCode = (String) session.getAttribute("checkcode");
		logger.info("ckCode === "+ckCode);
		if(!ckCode.equals(checkCode.toUpperCase())){
			return JSON.toJSONString("-1");
		}
		User user = uService.login(email, password);
		if (user != null) {
			session.setAttribute("user", user);
		}
		return JSON.toJSONString(user);
	}

	@ResponseBody
	@RequestMapping(value = { "/register.json" }, method = { RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	public Object register(@RequestParam("email") String email,
			@RequestParam("user_name") String user_name,
			@RequestParam("password") String password) {
		return JSON.toJSONString(uService.register(email, user_name, password));
	}

	@ResponseBody
	@RequestMapping(value = { "/findpwd.json" }, method = { RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	public Object findPwd(@RequestParam("email") String email,@RequestParam("username")String username,@RequestParam("checkCode")String checkCode) {
		String ckCode = (String) session.getAttribute("checkcode");
		logger.info("ckCode === "+ckCode);
		if(!ckCode.equals(checkCode.toUpperCase())){
			return JSON.toJSONString("-1");
		}
		return JSON.toJSONString(uService.findPassword(email,username));
	}

	@ResponseBody
	@RequestMapping(value = { "/check_email.json" }, method = { RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	public Object checkEmail(@RequestParam("email") String email) {
		return JSON.toJSONString(uService.isEmailExist(email));
	}

	@ResponseBody
	@RequestMapping(value = { "/activemail.json" }, method = { RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	public Object activemail(@RequestParam("email") String email,
			HttpServletResponse response) {
		// return JSON.toJSONString(uService.isEmailExist(email));
		int activeFlag = uService.activeUser(email);
		if (activeFlag == 0) {// 失败
			return JSON.toJSONString("激活失败");
		} else if (activeFlag == -1) {// 不存在
			return JSON.toJSONString("邮件不存在");
		} else {// 成功
			try {
				response.sendRedirect("/v1/send.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = { "/issessionexist.json" }, method = { RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	public Object isSessonExist(){
		User user = (User)session.getAttribute("user");
		return JSON.toJSONString(user);
	}
	
	@ResponseBody
	@RequestMapping(value = { "/loginout.json" }, method = { RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	public Object loginOut(){
		session.removeAttribute("user");
		return JSON.toJSONString("user");
	}
	
	@ResponseBody
	@RequestMapping(value = { "/update_pwd.json" }, method = { RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	public Object updatePwd(@RequestParam("oldPwd")String oldPwd,@RequestParam("newPwd")String newPwd){
		User user = (User)session.getAttribute("user");
		String email = user.getEmail();
		return JSON.toJSONString(uService.updatePwd(email, oldPwd, newPwd));
	}
	
}
