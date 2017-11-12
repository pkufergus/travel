package com.travel.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.travel.dao.CityDao;
import com.travel.pojo.City;

@Repository
public class CityDaoImpl implements CityDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<City> getCityList(Integer pageNow, Integer pageSize) {
		String sql = "SELECT `citycode`,`englishname` AS englishName,`chinesename` AS chineseName,"
				+ "`TraditionalChineseName`,`undone`,`city_level` FROM aie_cityname";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<City>(
				City.class));
	}

	@Override
	public Integer addCity(String cityCode, String engName, String chName,
			String triName,String country,String city_level) {
		String sql = "INSERT INTO `aie_cityname` (`citycode`,`englishname`,`chinesename`,`TraditionalChineseName`,`country`,`city_level`) VALUES (?,?,?,?,?,?)";
		return jdbcTemplate.update(sql, cityCode, engName, chName, triName,country,city_level);
	}

	@Override
	public List<City> getCityList() {
		String sql = "SELECT `citycode`,`englishname` AS englishName,`chinesename` AS chineseName,"
				+ "`TraditionalChineseName`,`undone` FROM aie_cityname";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<City>(
				City.class));
	}

	@Override
	public Integer addDescription(Integer id, String description, String href) {
		String sql = "INSERT INTO comment (`id`,`description`,`href`) VALUES (?,?,?)" ;
		try{
		return jdbcTemplate.update(sql,id,description,href);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Integer deleteCity(String cityCode) {
		final String sql = "DELETE FROM `aie_cityname` WHERE `citycode`=?";
		return jdbcTemplate.update(sql,cityCode);
	}

	@Override
	public List<City> fromCity(String country) {
		String sql = "SELECT `citycode`,`englishname` AS englishName,`chinesename` AS chineseName,"
				+ "`TraditionalChineseName`,`undone`,`country` FROM aie_cityname WHERE `country`=? ORDER BY `city_level` DESC limit 10";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<City>(
				City.class),country);
	}

	@Override
	public List<City> toCity() {
		String sql = "SELECT `citycode`,`englishname` AS englishName,`chinesename` AS chineseName,"
				+ "`TraditionalChineseName`,`undone`,`country` FROM aie_cityname ORDER BY `city_level` DESC";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<City>(
				City.class));
	}

	@Override
	public List<City> getCityByCodeCountry(String cityCode, String country) {
		final String sql = "SELECT * FROM aie_cityname WHERE `citycode`=? AND `country`=?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<City>(
				City.class),cityCode,country);
	}

	@Override
	public List<City> newToCity(String country) {
		if("CN".equals(country)){
			String sql = "SELECT `citycode`,`englishname` AS englishName,`chinesename` AS chineseName,"
					+ "`TraditionalChineseName`,`undone`,`country` FROM aie_cityname WHERE `country`!='CN' ORDER BY `country` DESC,`city_level` DESC";
			return jdbcTemplate.query(sql, new BeanPropertyRowMapper<City>(
					City.class));
		}
		String sql = "SELECT `citycode`,`englishname` AS englishName,`chinesename` AS chineseName,"
				+ "`TraditionalChineseName`,`undone`,`country` FROM aie_cityname WHERE `country`='CN' ORDER BY `city_level` DESC";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<City>(
				City.class));
	}
}
