package com.travel.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.dao.CityDao;
import com.travel.dao.HomePageDao;
import com.travel.dao.PriceDao;
import com.travel.pojo.City;
import com.travel.pojo.Menu;
import com.travel.service.HomePageService;

@Service
public class HomePageServiceImpl implements HomePageService {
	 private static Logger logger = LoggerFactory.getLogger(HomePageServiceImpl.class);
	@Autowired
	private HomePageDao hpDao;
	@Autowired
	private PriceDao pDao;
	@Autowired
	private CityDao cDao;

	@Override
	public Object getHomePageImg(String country) {
		
		List<Menu> mList =hpDao.getShowList(country);
		
		logger.info("mList.size="+mList.size()+",country="+country);
		String updateTime = "";
		try{
			updateTime = pDao.getUpdateTime().get(0).getUpdatedDate().split(" ")[0];
		}catch(Exception e){
			updateTime = "";
		}
		logger.info("updateTime="+updateTime);
		List<City> cList = cDao.getCityList();
		
		logger.info("cList="+cList.size());
		
		for(Menu menu : mList){
			for (City ct : cList) {
				if (menu.getSrcPlace().equals(ct.getCityCode())) {
					menu.setSrcCode(menu.getSrcPlace());
					menu.setSrcPlace(ct.getChineseName());
					
				}
				if (menu.getDestPlace().equals(ct.getCityCode())) {
					menu.setDestCode(menu.getDestPlace());
					menu.setDestPlace(ct.getChineseName());
				}
			}
			try{
			menu.setPrice_type("CA".equals(country) ? "CAD " : "USD ");
			String priceTmp = hpDao.getHomePageMinPrice(menu.getSrcCode(), menu.getDestCode(), updateTime).get(0).getMinPrice();
			logger.info("priceTmp==="+priceTmp);
			menu.setPrice(priceTmp);
			menu.setHref(menu.getHref().split("/")[1]);
			}catch(Exception e){
				logger.error(e.getLocalizedMessage());
			}
		}
		return mList;
	}
	
	
}
