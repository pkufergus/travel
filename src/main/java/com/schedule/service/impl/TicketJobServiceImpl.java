package com.schedule.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schedule.dao.TicketJobDao;
import com.schedule.dao.impl.TicketJobDaoImpl;
import com.schedule.service.TicketJobService;
import com.travel.pojo.City;
import com.travel.pojo.MailOrder;
import com.travel.pojo.Mailconfig;
import com.travel.util.DateUtil;
import com.travel.util.ServiceUtil;
/**
 * 
 * @author lvyf
 *
 */
@Service
public class TicketJobServiceImpl implements TicketJobService {
	
	private static Logger logger = LoggerFactory
			.getLogger(TicketJobServiceImpl.class);
	
	@Autowired
	private TicketJobDao tkDao;
	
	 
	
	@Override
	public void sendOrderMail() {
		String threeDaysAgo = DateUtil.getThreeDaysAgo();
		if(tkDao == null){
			tkDao = new TicketJobDaoImpl();
		}
		List<City> cityList = tkDao.getCityName();
		List<MailOrder> emails = tkDao.getOrderEmails(threeDaysAgo);
		Mailconfig mc = new Mailconfig();
		 
		List<Mailconfig> mList = tkDao.getMailConfigList();
		logger.info("mList is ==="+mList);
		if (mList != null) {
			if (mList.size() > 0) {
				 mc = mList.get(0);
			}
		} else {
			logger.error("mailconfig is null you should set send mail sever config");
		}
		
		for(MailOrder mo : emails){
			for(City city : cityList){
				if(mo.getLeave_city().equals(city.getCityCode())){
					mo.setLeave_city(city.getChineseName());
				}
				
				if(mo.getDest_city().equals(city.getCityCode())){
					mo.setDest_city(city.getChineseName());
				}
			}
			boolean sendMailFalg = ServiceUtil.sendOrderMail(mo, mc);
			logger.info("email==="+mo.getEmail()+",sendMailFlag ==="+sendMailFalg);
		}
	}

}
