package com.travel.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.travel.dao.MenuDao;
import com.travel.pojo.City;
import com.travel.pojo.Comment;
import com.travel.pojo.Menu;

@Repository
public class MenuDaoImpl implements MenuDao {
	private static Logger logger = LoggerFactory.getLogger(MenuDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Menu> getMenuList(Integer pageNow, Integer pageSize) {
		String sql = "SELECT t1.id,t1.DepartureCode AS srcPlace,t1.DestinationCode AS destPlace,COUNT(t2.description) AS description,t1.level,t1.Undone as unDone FROM `aie_citypair` t1 LEFT JOIN `comment` t2 ON t1.id=t2.id AND t2.description!='' GROUP BY t1.DepartureCode,t1.DestinationCode";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Menu>(
				Menu.class));
	}

	@Override
	public Integer addMenu(String srcPlace, String destPlace,
			String descripton, String country, String level, String show,
			String image_url) {
		String sql = "INSERT INTO `aie_citypair` (`DepartureCode`,`DestinationCode`,`country`,`level`,`show`,`image_url`) VALUES (? , ? , ? , ? , ? , ?)";
		return jdbcTemplate.update(sql, srcPlace, destPlace, country, level,
				show, image_url);
	}

	@Override
	public Integer addDescription(Integer id, String description, String href) {

		String sql = "INSERT INTO comment (`id`,`description`,`href`) VALUES (?,?,?)";
		logger.info("id==" + id + ",description===" + description + ",href=="
				+ href);
		int flag = 0;
		try {
			flag = jdbcTemplate.update(sql, id, description, href);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return flag;
		// return jdbcTemplate.update(sql,id,description,href);
	}

	@Override
	public Integer getId(String srcPlace, String destPlace) {
		String sql = "SELECT `id` FROM aie_citypair WHERE DepartureCode=? AND DestinationCode=?";
		logger.info(sql + ",srcPlace==" + srcPlace + ",dest==" + destPlace);
		return jdbcTemplate.queryForObject(sql, Integer.class, srcPlace,
				destPlace);
	}

	@Override
	public List<Menu> getMenuList(String country) {

		if ("USA".equals(country) || "CA".equals(country)
				|| "CN".equals(country)) {
			String sql = "SELECT t1.id,t1.DepartureCode AS srcPlace,t1.DestinationCode AS destPlace,t2.description AS description,t2.href FROM `aie_citypair` t1 LEFT JOIN `comment` t2 ON t1.id=t2.id WHERE t2.href!='' AND t1.country=? GROUP BY t2.id ORDER BY level desc";
			logger.info(sql + ",country==" + country);
			return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Menu>(
					Menu.class), country);
		} else {
			String sql = "SELECT t1.id,t1.DepartureCode AS srcPlace,t1.DestinationCode AS destPlace,t2.description AS description,t2.href FROM `aie_citypair` t1 LEFT JOIN `comment` t2 ON t1.id=t2.id WHERE t2.href!='' GROUP BY t2.id ORDER BY level desc";
			logger.info(sql + ",country==" + country);
			return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Menu>(
					Menu.class));
		}

	}

	@Override
	public String getCityNameByCode(String cityCode) {
		String sql = "";
		return jdbcTemplate.queryForObject(sql, String.class, cityCode);
	}

	@Override
	public List<Menu> getComment(String fromCode, String toCode) {
		String sql = "select t2.description AS `description`  from aie_citypair t1,comment t2 where t1.DepartureCode=? and t1.DestinationCode=? AND t1.id=t2.id AND t2.description !=''";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Menu>(
				Menu.class), fromCode, toCode);
	}

	@Override
	public Integer deleteMenu(String id) {
		final String sql = "DELETE FROM `aie_citypair` WHERE `id`=?";
		logger.info("deleteMenu id==" + id + ",sql=" + sql);
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public Integer deleteComment(String id) {
		final String sql = "DELETE FROM `comment` WHERE `id`=?";
		logger.info("deleteComment id==" + id + ",sql=" + sql);
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public Integer addComment(String id, String comment) {
		final String sql = "INSERT INTO `comment` (`id`,`description`) values (?,?)";
		return jdbcTemplate.update(sql, id, comment);
	}

	@Override
	public List<Comment> getComments(String id) {
		final String sql = "SELECT * FROM `comment` WHERE `id` = ? AND `description` !='' AND `description` IS NOT NULL";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Comment>(
				Comment.class), id);
	}

	@Override
	public Integer deleteCommentByCid(String cid) {
		final String sql = "DELETE FROM `comment` WHERE `cid` = ?";
		return jdbcTemplate.update(sql, cid);
	}

	@Override
	public List<Comment> getComments(String fromCode, String toCode) {
		final String sql = "SELECT t2.* FROM aie_citypair t1,comment t2 WHERE t1.DepartureCode = ? AND t1.DestinationCode = ? AND t1.id=t2.id AND t2.`description` !='' AND t2.`description` IS NOT NULL";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Comment>(
				Comment.class), fromCode, toCode);
	}

	@Override
	public List<Menu> isMenuExists(String fromCode, String toCode,
			String country) {
		fromCode = fromCode.split("&")[0];
		toCode = toCode.split("&")[0];
		final String sql = "SELECT DepartureCode AS srcPlace ,DestinationCode AS destPlace FROM aie_citypair WHERE DepartureCode = ? "
				+ "AND DestinationCode = ? AND country=?";
		logger.info("sql==" + sql + ",fromCode" + fromCode + ",toCode=="
				+ toCode + ",country==" + country);
		List<Menu> tempList = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<Menu>(Menu.class), fromCode, toCode,
				country);
		logger.info(tempList.size() + "......................");
		return tempList;
	}

	@Override
	public City getCityByCode(String cityCode) {
		final String sql = "SELECT t1.citycode,t1.englishname AS englishName,"
				+ "t1.chinesename AS chineseName,t1.traditionalChineseName AS traditionalChineseName,"
				+ "t1.country AS country FROM aie_cityname t1 WHERE t1.citycode=?";
		List<City> cList = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<City>(City.class), cityCode);
		City city = new City();
		if (cList.size() > 0) {
			city = cList.get(0);
		}
		return city;
	}

	@Override
	public City getFromCityByCid(String cid) {
		final String sql = "SELECT t3.* FROM comment t1,aie_citypair t2,aie_cityname t3"
				+ " WHERE t1.cid=? AND t1.id=t2.id AND t2.DepartureCode = t3.citycode";
		List<City> cList = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<City>(City.class), cid);
		City city = new City();
		if (cList.size() > 0) {
			city = cList.get(0);
		}
		return city;
	}

	@Override
	public City getToCityByCid(String cid) {
		final String sql = "SELECT t3.* FROM comment t1,aie_citypair t2,aie_cityname t3"
				+ " WHERE t1.cid=? AND t1.id=t2.id AND t2.DestinationCode = t3.citycode";
		List<City> cList = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<City>(City.class), cid);
		City city = new City();
		if (cList.size() > 0) {
			city = cList.get(0);
		}
		return city;
	}

	@Override
	public List<Menu> getMenuListNotChina() {
		final String sql = "SELECT t1.id, t1.DepartureCode AS srcCode,t1.DestinationCode AS destCode,t1.country,t2.href FROM aie_citypair t1 JOIN comment t2 ON t1.country!='CN' AND t2.href!= '' AND t2.href IS NOT NULL AND t2.id=t1.id group by t1.DepartureCode,t1.DestinationCode";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Menu>(
				Menu.class));
	}

}
