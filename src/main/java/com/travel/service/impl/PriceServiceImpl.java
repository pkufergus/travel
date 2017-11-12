package com.travel.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.dao.CityDao;
import com.travel.dao.MenuDao;
import com.travel.dao.PriceDao;
import com.travel.pojo.City;
import com.travel.pojo.MasterCount;
import com.travel.pojo.MasterCountWeb;
import com.travel.pojo.MasterData;
import com.travel.pojo.Menu;
import com.travel.pojo.PriceNew;
import com.travel.service.PriceService;
import com.travel.util.DateUtil;
import com.travel.util.HandleService;

@Service
public class PriceServiceImpl implements PriceService {
	private static Logger logger = LoggerFactory
			.getLogger(PriceServiceImpl.class);
	@Autowired
	private PriceDao pDao;
	
	@Autowired
	private CityDao cDao;
	
	@Autowired
	private MenuDao mDao;
	
	@Override
	public Object getPriceList(){
		String fromD1 = DateUtil.getMonthFirstDay(1);
		String toD1 = DateUtil.getMonthLastDay(1);
		
		String fromD2 = DateUtil.getMonthFirstDay(2);
		String toD2 = DateUtil.getMonthLastDay(2);
		
		String fromD3 = DateUtil.getMonthFirstDay(3);
		String toD3 = DateUtil.getMonthLastDay(3);
		
		String fromD4 = DateUtil.getMonthFirstDay(4);
		String toD4 = DateUtil.getMonthLastDay(4);
		
		List<MasterData> list1 = pDao.getTicketPriceList(fromD1, toD1);
		List<MasterData> list2 = pDao.getTicketPriceList(fromD2, toD2);
		List<MasterData> list3 = pDao.getTicketPriceList(fromD3, toD3);
		List<MasterData> list4 = pDao.getTicketPriceList(fromD4, toD4);
		
		List<City> cityList = cDao.getCityList();
		List<Menu> mList = mDao.getMenuListNotChina();
		for(Menu menu : mList){
			for(City city : cityList){
				if(menu.getSrcCode().equals(city.getCityCode())){
					menu.setSrcPlace(city.getChineseName());
				}
				if(menu.getDestCode().equals(city.getCityCode())){
					menu.setDestPlace(city.getChineseName());
				}
			}
		}
		/**
		 * 更新日期
		 */
		List<MasterData> mdList = pDao.getUpdateTime();
		String updateTime = "";
		if(!mdList.isEmpty()){
			updateTime = mdList.get(0).getUpdatedDate();
		}
		
		return HandleService.handlePriceList(mList, list1, list2, list3, list4, fromD1, fromD2, fromD3, fromD4,updateTime);
	}

	@Override
	public List<MasterCountWeb> newGetPriceService() {
		String updateTime = "";
		try{
			List<MasterCount> upList = pDao.newGetMasterCountUpdateTime();
			updateTime = upList.get(0).getUpdate_time();
		}catch(Exception e){
			e.printStackTrace();
			updateTime = "";
		}
		List<MasterCount> priceList = pDao.newGetPriceService(updateTime);
		List<MasterCountWeb> webList = new ArrayList<MasterCountWeb>();
		List<MasterCount> urlList = pDao.getUrlList(updateTime);
		for(MasterCount mc : urlList){
			MasterCountWeb mcw = new MasterCountWeb();
			mcw.setFromP(mc.getFromP());
			logger.info(mcw.getFromP());
			mcw.setToP(mc.getToP());
			mcw.setPrice_type(mc.getPrice_type());
			
			String url = mc.getUrl();
			mcw.setUrl(url.split("\\.")[1]);
			mcw.setUpdate_time(mc.getNewUpdateTime());
			
			List<PriceNew> priceNewList = new ArrayList<PriceNew>();
			 for(MasterCount mcIn : priceList){
				
				 if(mcIn.getUrl().equals(url)){
					 PriceNew pn = new PriceNew();
					 pn.setMonth(mcIn.getMonth());
					 pn.setOneP("0".equals(mcIn.getDou_week_price())?"暂无票价":mcIn.getDou_week_price());
					 pn.setTwoP("0".equals(mcIn.getOne_month_price())?"暂无票价":mcIn.getOne_month_price());
					 pn.setThreeP("0".equals(mcIn.getThree_month_price())?"暂无票价":mcIn.getThree_month_price());
					 pn.setFourP("0".equals(mcIn.getSix_month_price())?"暂无票价":mcIn.getSix_month_price());
					 priceNewList.add(pn);
				 }
				
			 }
			 mcw.setMin_price(HandleService.getMinPrice(priceNewList));
			 mcw.setObjList(priceNewList);
			 webList.add(mcw);
		}
		return webList;
	}
}
