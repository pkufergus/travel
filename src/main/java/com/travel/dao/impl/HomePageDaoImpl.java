package com.travel.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.travel.dao.HomePageDao;
import com.travel.pojo.Menu;
import com.travel.pojo.Price;


@Repository
public class HomePageDaoImpl implements HomePageDao {
	private static Logger logger = LoggerFactory.getLogger(HomePageDaoImpl.class);
	@Autowired
	private JdbcTemplate jdbcTemplage;

	@Override
	public List<Menu> getShowList(String country) {
		final String sql = "SELECT t1.`id`,t1.`DepartureCode` AS srcPlace,"
				+ "t1.`DestinationCode` AS destPlace ,t1.`country`,t1.`level`,t1.`show`,"
				+ "t1.`image_url`,t2.`href` FROM `aie_citypair` t1,`comment`"
				+ " t2 WHERE t1.id = t2.id AND t1.country=? AND t1.`show`=1";
		logger.info("sql="+sql+",.........country="+country);
		return jdbcTemplage.query(sql, new BeanPropertyRowMapper<Menu>(Menu.class),country);
	}

	@Override
	public List<Price> getHomePageMinPrice(String fromCode, String descCode,
			String updateTime) {
		final String sql = "select min(t.p) as minPrice from (select convert(price,signed) as p from aie_masterdata where DepartureCity=? and ArrivalCity=? "
				+ "and DepartureDate >= now()) t";
		logger.info("sql="+sql+",fromCode="+fromCode+",destCode="+descCode+",updateTime="+updateTime);
		return jdbcTemplage.query(sql, new BeanPropertyRowMapper<Price>(Price.class),fromCode,descCode);
	}
	
	
}
