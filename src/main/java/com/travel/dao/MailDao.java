package com.travel.dao;

import java.util.List;

import com.travel.pojo.Mailconfig;

public interface MailDao {
	List<Mailconfig> getMailConfigList();
	
	Integer saveMailConfig(Mailconfig mail);
	
	Integer updateMailConfig(Mailconfig mail);
	
	List<Mailconfig> getMailConfigListByMail(String mail);
}
