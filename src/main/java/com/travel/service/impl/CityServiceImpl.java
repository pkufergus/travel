package com.travel.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.dao.CityDao;
import com.travel.pojo.City;
import com.travel.service.CityService;

@Service
public class CityServiceImpl implements CityService {
	
	 private static Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);
	
	@Autowired
	private CityDao cDao;
	
	@Override
	public List<City> getCityList(Integer pageNow, Integer pageSize) {
		List<City> cList = null;
		try{
			cList = cDao.getCityList(pageNow, pageSize);
			logger.info("pageNow==="+pageNow+",pageSize==="+pageSize+",cityList length==="+cList.size());
		}catch(Exception e){
			logger.error(e.getMessage());
			return cList;
		}
		return cList;
	}

	@Override
	public Object addCity(String cityCode, String engName, String chName,
			String triName,String country,String city_level) {
		Integer flag = 0;
		
		List<City> existList = cDao.getCityByCodeCountry(cityCode, country);
	 
		if(existList.size() > 0){
			return "EXIST";
		}
		
		try{
			flag = cDao.addCity(cityCode, engName, chName, triName,country,city_level);
			logger.info("cityCode==="+cityCode+",engName==="+engName+
					",chName==="+chName+",triName==="+triName+".Flag==="+flag);
		}catch(Exception e){
			logger.error(e.getMessage());
			return flag;
		}
		return flag;
	}

	@Override
	public Integer deleteCity(String cityCode) {
		return cDao.deleteCity(cityCode);
	}

	@Override
	public List<Object> getFromToCity(String country) {
		List<Object> objList = new LinkedList<Object>();
		List<City> fromCity = cDao.fromCity(country);
		for(City fcity : fromCity){
			fcity.setEnglishName(fcity.getEnglishName().replace(" ", "-"));
		}
		objList.add(fromCity);
		
		List<City> toCity = cDao.newToCity(country);
		for(City tCity: toCity){
			tCity.setEnglishName(tCity.getEnglishName().replace(" ", "-"));
		}
		objList.add(toCity);
		return objList;
	}

}
