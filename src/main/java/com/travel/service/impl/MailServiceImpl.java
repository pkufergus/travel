package com.travel.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.dao.MailDao;
import com.travel.pojo.Mailconfig;
import com.travel.service.MailService;

@Service
public class MailServiceImpl implements MailService {

	private static Logger logger = LoggerFactory
			.getLogger(MailServiceImpl.class);

	@Autowired
	private MailDao mDao;

	@Override
	public Mailconfig getMailList() {
		Mailconfig mc = null;
		List<Mailconfig> mList = mDao.getMailConfigList();
		if(mList != null){
			if(mList.size()>0){
				mc = mList.get(0);
			}else {
				return mc;
			}
		}else {
			return mc;
		}
		return mc;
	}

	@Override
	public Integer editMail(String id, String server, String port,
			String username, String password, String from) {
		Mailconfig mail = new Mailconfig();
		if(!"".equals(id) && id!=null){
		mail.setId(Integer.parseInt(id));
		}
		mail.setServer(server);
		mail.setFrom(from);
		mail.setPassword(password);
		mail.setPort(Integer.parseInt(port));
		mail.setUsername(username);
		Integer flag = 0;

		try {
			if(!"".equals(id)){
			flag = mDao.updateMailConfig(mail);
			}else{
			flag = mDao.saveMailConfig(mail);
			}
			logger.info("id==="+id+",flag===" + flag);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return flag;
		}
		return flag;
	}

	@Override
	public Integer saveMailConfig(String id, String server, String port,
			String username, String password, String from) {
		Mailconfig mail = new Mailconfig();
		mail.setId(Integer.parseInt(id));
		mail.setServer(server);
		mail.setFrom(from);
		mail.setPassword(password);
		mail.setPort(Integer.parseInt(port));
		mail.setUsername(username);
		Integer flag = 0;

		try {
			flag = mDao.saveMailConfig(mail);
			logger.info("flag===" + flag);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return flag;
		}
		return flag;
	}

}
