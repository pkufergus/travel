package com.travel.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.travel.dao.MailDao;
import com.travel.pojo.Mailconfig;

@Repository
public class MailDaoImpl implements MailDao {
	@Autowired
	private JdbcTemplate srcJdbcTemplate;
	
	@Override
	public List<Mailconfig> getMailConfigList() {
		String sql = "SELECT `id`,`server`,`port`,`username`,`password`,`from` FROM `mailconfig`";
		return srcJdbcTemplate.query(sql, new BeanPropertyRowMapper<Mailconfig>(Mailconfig.class));
	}

	@Override
	public Integer saveMailConfig(Mailconfig mail) {
		String sql = "INSERT INTO `mailconfig` (`server`,`port`,`username`,`password`,`from`) VALUES (?,?,?,?,?)";
		return srcJdbcTemplate.update(sql,mail.getServer(),mail.getPort(),mail.getUsername(),
				mail.getPassword(),mail.getFrom());
	}

	@Override
	public Integer updateMailConfig(Mailconfig mail) {
		String sql = "UPDATE `mailconfig` SET `server`=? ,`port`=?,`username`=?,`password`=?,`from`=? WHERE `id`=?";
		return srcJdbcTemplate.update(sql,mail.getServer(),mail.getPort(),mail.getUsername(),
				mail.getPassword(),mail.getFrom(),mail.getId());
	}

	@Override
	public List<Mailconfig> getMailConfigListByMail(String mail) {
		String sql = "SELECT `id`,`server`,`port`,`username`,`password`,`from` FROM `mailconfig` WHERE `email`=?";
		return srcJdbcTemplate.query(sql, new BeanPropertyRowMapper<Mailconfig>(Mailconfig.class),mail);
	}

}
