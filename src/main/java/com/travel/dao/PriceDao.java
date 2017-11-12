package com.travel.dao;

import java.util.List;

import com.travel.pojo.MasterCount;
import com.travel.pojo.MasterData;

public interface PriceDao {
	List<MasterData> getTicketPriceList(String fromDate,String toDate);
	
	List<MasterData> getUpdateTime();
	
	List<MasterCount> newGetPriceService(String updateTime);
	
	List<MasterCount> newGetMasterCountUpdateTime();
	
	List<MasterCount> getUrlList(String updateTime);
}	
