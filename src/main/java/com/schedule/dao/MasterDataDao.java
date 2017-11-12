package com.schedule.dao;

import java.util.List;

import com.travel.pojo.City;
import com.travel.pojo.MasterCount;
import com.travel.pojo.MasterData;
import com.travel.pojo.Menu;

public interface MasterDataDao {
	
	List<City> getCityName();
	
	List<Menu> getMenusNotChina();
	
	List<MasterData> getMasterDataList(String fromCode,String toCode,String fromD,String toD,String fromDS,String toDS
			,int begin,int end);
	
	List<MasterData> getUpdateTime();
	
	Integer saveMasterCount(MasterCount mc);
	
	Integer updateMasterCount(MasterCount mc);
	
	List<MasterCount> getCountByYearMonthCode(MasterCount mc);
}
