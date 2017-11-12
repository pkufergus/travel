package com.travel.service.impl;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.dao.MailOrderDao;
import com.travel.pojo.MailOrder;
import com.travel.service.MailOrderService;
import com.travel.util.DateUtil;

@Service
public class MailOrderServiceImpl implements MailOrderService {
	 private static Logger logger = LoggerFactory.getLogger(MailOrderServiceImpl.class);
	@Autowired
	private MailOrderDao mDao;

	@Override
	public Integer insertMailOrder(String email,String leave_date,String back_date,String round_trip,
			String leave_city,String dest_city,String max_price) {
		logger.info("email=="+email);
		MailOrder mo = new MailOrder();
		mo.setEmail(email);
		mo.setLeave_city(leave_city);
		mo.setDest_city(dest_city);
		mo.setRound_trip(round_trip);
		mo.setMax_price(max_price);
		mo.setLeave_date(leave_date);
		mo.setBack_date(back_date);
		mo.setOrder_date(DateUtil.getCurrentDate());
		return mDao.insertMailOrder(mo);
	}
	
	public static void main(String[] args) {
		System.out.println(Timestamp.valueOf("2015-06-29 00:00:00"));
	}
}
