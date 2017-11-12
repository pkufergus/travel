package com.travel.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.travel.dao.MailOrderDao;
import com.travel.pojo.MailOrder;

@Repository
public class MailOrderDaoImpl implements MailOrderDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Integer insertMailOrder(MailOrder mo) {
		final String sql = "INSERT INTO `mail_order` (`email`,`leave_date`,`back_date`,"
				+ "`round_trip`,`leave_city`,`dest_city`,`max_price`,`order_date`)"
				+ " VALUES (?,?,?,?,?,?,?,?)";
		return jdbcTemplate.update(sql,mo.getEmail(),mo.getLeave_date(),mo.getBack_date(),
				mo.getRound_trip(),mo.getLeave_city(),mo.getDest_city(),mo.getMax_price(),
				mo.getOrder_date());
	}

}
