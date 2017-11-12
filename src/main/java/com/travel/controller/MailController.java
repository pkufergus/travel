package com.travel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.travel.service.MailOrderService;
import com.travel.service.MailService;

@Controller
@RequestMapping(value = "/mail")
public class MailController {
	 private static Logger logger = LoggerFactory.getLogger(MailController.class);
	@Autowired
	private MailService mService;
	
	@Autowired
	private MailOrderService moService;
	
	@ResponseBody
	@RequestMapping(value = { "/maillist.json" } , method = {RequestMethod.GET}, produces = {"application/json;charset=UTF-8"})
	public Object mailList(){
		return JSON.toJSONString(mService.getMailList());
	}
	@ResponseBody
	@RequestMapping(value = { "/mailedit.json" } , method = {RequestMethod.GET}, produces = {"application/json;charset=UTF-8"})
	public Object mailEdit(@RequestParam("id")String id , @RequestParam("server")String server,
			@RequestParam("port")String port,@RequestParam("username")String username,
			@RequestParam("password")String password,@RequestParam("from")String 
			from){
		mService.editMail(id, server, port, username, password, from);
		return JSON.toJSONString(mService.getMailList());
	}
	
	@ResponseBody
	@RequestMapping(value = { "/save_mail_order.json" } , method = {RequestMethod.GET}, produces = {"application/json;charset=UTF-8"})
	public Object saveOrderMail(@RequestParam("email")String email,
			@RequestParam("leave_date")String leave_date,
			@RequestParam("back_date")String back_date,
			@RequestParam("round_trip")String round_trip,@RequestParam("leave_city")String leave_city,
			@RequestParam("dest_city")String dest_city,
			@RequestParam("max_price")String max_price){
		logger.info("mail--"+email); 
		return JSON.toJSONString(moService.insertMailOrder(email, leave_date, back_date, round_trip, leave_city, dest_city, max_price));
	}
	
}
