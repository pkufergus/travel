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
import com.travel.service.CityService;

@Controller
@RequestMapping(value = "/city")
public class CityController {

	private static Logger logger = LoggerFactory
			.getLogger(CityController.class);

	@Autowired
	private CityService cService;

	@ResponseBody
	@RequestMapping(value = { "/citylist.json" } , method = {RequestMethod.GET}, produces = {"application/json;charset=UTF-8"})
	 public Object getCityList(@RequestParam("pageNow")String pageNow){
		 logger.info("in citylist.json");
		 return JSON.toJSONString(cService.getCityList(1, 10));
	 }
	
	@ResponseBody
	@RequestMapping(value = { "/addcity.json" } , method = {RequestMethod.GET}, produces = {"application/json;charset=UTF-8"})
	 public Object addcity(@RequestParam("cityCode")String cityCode,@RequestParam("engName")String engName,
			 @RequestParam("chName")String chName,@RequestParam("triName")String triName,@RequestParam("country")String country
			 ,@RequestParam("city_level")String city_level){
		 logger.info("in citylist.json");
		 return JSON.toJSONString(cService.addCity(cityCode,engName,chName,triName,country,city_level));
	 }
	
	@ResponseBody
	@RequestMapping(value = { "/delete_city.json" } , method = {RequestMethod.GET}, produces = {"application/json;charset=UTF-8"})
	 public Object deleteCity(@RequestParam("cityCode")String cityCode){
		 logger.info("delete cityCode==="+cityCode);
		 return JSON.toJSONString(cService.deleteCity(cityCode));
	 }
	
	@ResponseBody
	@RequestMapping(value = { "/from_to_city.json" } , method = {RequestMethod.GET}, produces = {"application/json;charset=UTF-8"})
	 public Object fromToCityList(@RequestParam("country")String country){
		 logger.info("==="+country);
		 return JSON.toJSONString(cService.getFromToCity(country));
	 }
}
