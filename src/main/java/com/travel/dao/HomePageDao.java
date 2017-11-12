package com.travel.dao;

import java.util.List;

import com.travel.pojo.Menu;
import com.travel.pojo.Price;

public interface HomePageDao {
	List<Menu> getShowList(String country);
	
	List<Price> getHomePageMinPrice(String fromCode,String descCode,String updateTime);
}
