package com.schedule.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.schedule.dao.TicketJobDao;
import com.travel.pojo.City;
import com.travel.pojo.MailOrder;
import com.travel.pojo.Mailconfig;

@Repository("srcRepositoryBase")
public class TicketJobDaoImpl implements TicketJobDao {
	private static Logger logger = LoggerFactory.getLogger(TicketJobDao.class);
	
	@Autowired
	private JdbcTemplate srcJdbcTemplate;
	
	@Override
	public List<MailOrder> getOrderEmails(String threeDaysAgo){
		List<MailOrder> emailList = new ArrayList<MailOrder>();
		final String sql = "SELECT t2.email,t2.leave_city,t2.dest_city,t2.leave_date,t2.back_date FROM `aie_masterdata` t1,"
				+ "mail_order t2 WHERE t1.DepartureDate=t2.leave_date"
				+ " and t1.ReturnDate=t2.back_date and t1.DepartureCity=t2.leave_city"
				+ " and t1.ArrivalCity=t2.dest_city "
				+ "and t1.Price<=t2.max_price AND t2.order_date >=?";
		emailList = srcJdbcTemplate.query(sql, new BeanPropertyRowMapper<MailOrder>(MailOrder.class),threeDaysAgo);
		logger.info("emailList.size==="+emailList.size());
		return emailList;
	}
	
	@Override
	public List<Mailconfig> getMailConfigList() {
		String sql = "SELECT `id`,`server`,`port`,`username`,`password`,`from` FROM `mailconfig`";
		return srcJdbcTemplate.query(sql, new BeanPropertyRowMapper<Mailconfig>(Mailconfig.class));
	}

	@Override
	public List<City> getCityName() {
		String sql = "SELECT `citycode`,`englishname` AS englishName,`chinesename` AS chineseName,"
				+ "`TraditionalChineseName`,`undone` FROM aie_cityname";
		return srcJdbcTemplate.query(sql, new BeanPropertyRowMapper<City>(
				City.class));
	}
}
