package com.travel.dao;

import java.util.List;

import com.travel.pojo.City;

public interface CityDao {
	List<City> getCityList(Integer pageNow , Integer pageSize);
	
	List<City> getCityList();
	
	Integer addCity(String cityCode , String engName,String chName,String triName ,String country,String city_level);
	
	public Integer addDescription(Integer id, String description,String href);
	
	Integer deleteCity(String cityCode);
	
	List<City> fromCity(String country);
	
	List<City> toCity();
	
	 List<City> newToCity(String country);
	
	List<City> getCityByCodeCountry(String cityCode,String country);
}
