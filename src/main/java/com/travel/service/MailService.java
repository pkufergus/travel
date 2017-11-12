package com.travel.service;


import com.travel.pojo.Mailconfig;

public interface MailService {
	Mailconfig getMailList();
	
	Integer editMail(String id , String server,String port,String username,String password,String 
			from);
	
	Integer saveMailConfig(String id , String server,String port,String username,String password,String 
			from);
}
