package com.travel.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.travel.dao.PriceDao;
import com.travel.pojo.MasterCount;
import com.travel.pojo.MasterData;

@Repository
public class PriceDaoImpl implements PriceDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<MasterData> getTicketPriceList(String fromDate,String toDate) {
		final String sql ="select t1.DepartureDate,t1.ReturnDate,t1.DepartureCity,t1.ArrivalCity,t2.country ,min(convert(price , SIGNED)) as price from aie_masterdata t1,aie_cityname t2 where t1.DepartureCity=t2.citycode and t2.country != 'CN' AND t1.DepartureDate>=? AND t1.DepartureDate<=?  group by t1.DepartureCity,t1.ArrivalCity";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<MasterData>(MasterData.class),fromDate,toDate);
	}

	@Override
	public List<MasterData> getUpdateTime() {
		final String sql = "select UpdatedDate from aie_masterdata order by UpdatedDate desc limit 1";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<MasterData>(MasterData.class));
	}

	@Override
	public List<MasterCount> newGetPriceService(String updateTime) {
		final String sql = "SELECT * from aie_master_count where update_time=? ORDER BY CONVERT(month,signed)";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<MasterCount>(MasterCount.class),updateTime);
	}

	@Override
	public List<MasterCount> newGetMasterCountUpdateTime() {
		final String sql = "select Update_time from aie_master_count order by Update_time desc limit 1";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<MasterCount>(MasterCount.class));
	}

	@Override
	public List<MasterCount> getUrlList(String updateTime) {
		final String sql = "SELECT * FROM `aie_master_count` t1,`aie_citypair` t2 WHERE `update_time`=? AND t1.fromcode=t2.DepartureCode AND t1.tocode=t2.DestinationCode  GROUP BY `url` ORDER BY t2.level DESC";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<MasterCount>(MasterCount.class),updateTime);
	}

}
