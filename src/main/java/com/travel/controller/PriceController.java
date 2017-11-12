package com.travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.travel.service.PriceService;

@Controller
@RequestMapping(value = "/price")
public class PriceController {
	@Autowired
	private PriceService pService;
	
	@ResponseBody
	@RequestMapping(value = { "/price_list.json" } , method = {RequestMethod.GET}, produces = {"application/json;charset=UTF-8"})
	 public Object getCityList(){
		 return JSON.toJSONString(pService.getPriceList());
	 }
	
	@ResponseBody
	@RequestMapping(value = { "/new_price_list.json" } , method = {RequestMethod.GET}, produces = {"application/json;charset=UTF-8"})
	 public Object getPriceList(){
		 return JSON.toJSONString(pService.newGetPriceService());
	 }
}
