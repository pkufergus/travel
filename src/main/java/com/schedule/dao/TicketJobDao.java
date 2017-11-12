package com.schedule.dao;

import java.util.List;

import com.travel.pojo.City;
import com.travel.pojo.MailOrder;
import com.travel.pojo.Mailconfig;

public interface TicketJobDao {
	List<MailOrder> getOrderEmails(String threeDaysAgo);

	List<Mailconfig> getMailConfigList();
	
	List<City> getCityName();
}
