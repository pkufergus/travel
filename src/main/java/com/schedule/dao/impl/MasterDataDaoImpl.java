package com.schedule.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.schedule.dao.MasterDataDao;
import com.travel.pojo.City;
import com.travel.pojo.MasterCount;
import com.travel.pojo.MasterData;
import com.travel.pojo.Menu;

@Repository
public class MasterDataDaoImpl implements  MasterDataDao{
	@Autowired
	private JdbcTemplate srcJdbcTemplate;
	
	@Override
	public List<Menu> getMenusNotChina(){
		final String sql = "SELECT t1.id, t1.DepartureCode AS srcCode,t1.DestinationCode AS destCode,t1.country,t2.href FROM aie_citypair t1 JOIN comment t2 ON t1.country!='CN' AND t2.href!= '' AND t2.href IS NOT NULL AND t2.id=t1.id group by t1.DepartureCode,t1.DestinationCode";
		return srcJdbcTemplate.query(sql, new BeanPropertyRowMapper<Menu>(Menu.class));
	}
	
	@Override
	public List<City> getCityName() {
		String sql = "SELECT `citycode`,`englishname` AS englishName,`chinesename` AS chineseName,"
				+ "`TraditionalChineseName`,`undone` FROM aie_cityname";
		return srcJdbcTemplate.query(sql, new BeanPropertyRowMapper<City>(
				City.class));
	}

	@Override
	public List<MasterData> getMasterDataList(String fromCode,String toCode,String fromD, String toD,
			String fromDS, String toDS,int begin,int end) {
		final String sql = "select min(pri) as price from (SELECT ID, DepartureDate, ReturnDate,DepartureCity,ArrivalCity,convert(Price,signed)"
				+ " as pri,Stops  FROM aie_masterdata   WHERE DepartureCity = ?  AND ArrivalCity = ?   AND DepartureDate >=? AND"
				+ "  DepartureDate<=?  AND to_days(Returndate)-to_days(DepartureDate)>? and to_days(Returndate)-to_days(DepartureDate)<=?) t";
		return srcJdbcTemplate.query(sql, new BeanPropertyRowMapper<MasterData>(MasterData.class),fromCode,toCode,fromD,toD,begin,end);
	}

	@Override
	public List<MasterData> getUpdateTime() {
		final String sql = "select UpdatedDate from aie_masterdata order by UpdatedDate desc limit 1";
		return srcJdbcTemplate.query(sql, new BeanPropertyRowMapper<MasterData>(MasterData.class));
	}

	@Override
	public Integer saveMasterCount(MasterCount mc) {
		final String sql = "INSERT INTO aie_master_count (`year`,`month`,`dou_week_price`,`one_month_price`,"
				+ "`three_month_price`,`six_month_price`,`url`,`min_price`,"
				+ "`fromP`,`toP`,`fromCode`,`toCode`,`update_time`,`country`,`price_type`,`newUpdateTime`)"
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return srcJdbcTemplate.update(sql,mc.getYear(),mc.getMonth(),mc.getDou_week_price(),mc.getOne_month_price(),
				mc.getThree_month_price(),mc.getSix_month_price(),mc.getUrl(),mc.getMin_price(),
				mc.getFromP(),mc.getToP(),mc.getFromCode(),mc.getToCode(),mc.getUpdate_time(),mc.getCountry(),mc.getPrice_type(),mc.getNewUpdateTime());
	}

	@Override
	public List<MasterCount> getCountByYearMonthCode(MasterCount mc) {
		final String sql = "SELECT * FROM `aie_master_count`"
				+ " WHERE `year`=? AND `month`=? AND `fromcode`=? AND `tocode`=? AND update_time=?";
		return srcJdbcTemplate.query(sql, new BeanPropertyRowMapper<MasterCount>(MasterCount.class),mc.getYear(),mc.getMonth()
				,mc.getFromCode(),mc.getToCode(),mc.getUpdate_time());
	}

	@Override
	public Integer updateMasterCount(MasterCount mc) {
		final String sql = "UPDATE `aie_master_count` SET `dou_week_price`=?,one_month_price=?,three_month_price=?,six_month_price=?,newUpdateTime=? "
				+ "WHERE `year`=? AND `month`=? AND `fromcode`=? AND `tocode`=? AND update_time=?";
		return srcJdbcTemplate.update(sql,
				mc.getDou_week_price(),mc.getOne_month_price(),mc.getThree_month_price(),mc.getSix_month_price(),mc.getNewUpdateTime()
				,mc.getYear(),mc.getMonth()
				,mc.getFromCode(),mc.getToCode(),mc.getUpdate_time());
	}
}
