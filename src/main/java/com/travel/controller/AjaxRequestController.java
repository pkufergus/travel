package com.travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.travel.service.AjaxRequestService;

@Controller
@RequestMapping(value = "/ajax")
public class AjaxRequestController {
	@Autowired
	private AjaxRequestService ajaxService;
	
	@ResponseBody
	@RequestMapping(value = { "/auto_complete.json" }, method = { RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	public Object getCityList(@RequestParam("prefix")String prefix) {
		return JSON.toJSONString(ajaxService.autoComplete(prefix));
	}
	
	
	@ResponseBody
	@RequestMapping(value = { "/auto_complete_air.json" }, method = { RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	public Object autoAirLine(@RequestParam("mode")String mode,@RequestParam("prefix")String prefix) {
		return JSON.toJSONString(ajaxService.autoCompleteAir(mode,prefix));
	}
}
