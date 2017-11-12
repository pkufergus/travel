package com.travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.travel.service.HomePageService;

@Controller
@RequestMapping(value = "/homepage")
public class HomePageController {
	
	@Autowired
	private HomePageService hpService;
	
	@ResponseBody
	@RequestMapping(value = { "/get_show_image.json" } , method = {RequestMethod.GET}, produces = {"application/json;charset=UTF-8"})
	public Object getShowImage(@RequestParam("country")String country){
		return JSON.toJSONString(hpService.getHomePageImg(country));
	}
}
