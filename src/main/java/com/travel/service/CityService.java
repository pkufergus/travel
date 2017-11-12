package com.travel.service;

import java.util.List;

import com.travel.pojo.City;

public interface CityService {
	List<City> getCityList(Integer pageNow , Integer pageSize);
	
	Object addCity(String cityCode , String engName,String chName,String triName ,String country,String city_level);
	
	Integer deleteCity(String cityCode);
	
	List<Object> getFromToCity(String country);
}
