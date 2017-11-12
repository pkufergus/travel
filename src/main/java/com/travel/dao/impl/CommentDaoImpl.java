package com.travel.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.travel.dao.CommentDao;
import com.travel.pojo.Comment;

@Repository
public class CommentDaoImpl implements CommentDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static Logger logger = LoggerFactory
			.getLogger(CommentDaoImpl.class);

	@Override
	public List<Comment> getCommentById(String id) {
		final String sql = "SELECT t1.*,t2.level,t2.`show`,t2.image_url FROM `comment` t1,aie_citypair t2 WHERE t1.id=t2.id AND  t1.`id`=?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Comment>(Comment.class),id);
	}

	@Override
	public Integer updateComment(String id, String description) {
		final String sql = "UPDATE `comment` SET `description` = ? WHERE `id` = ?";
		return jdbcTemplate.update(sql,description,id);
	}

	@Override
	public Integer updateLevel(String level, String id,String show,String image_url) {
		 String sql = "UPDATE `aie_citypair` SET level=? , `show`=?,`image_url`=? WHERE id=?";
		 logger.info("level="+level+",id="+id+",show="+show+",image_url="+image_url);
		 if("".equals(image_url)){
			sql = "UPDATE `aie_citypair` SET level=? , `show`=? WHERE id=?";
			logger.info(sql);
			return jdbcTemplate.update(sql,level,show,id);
		}
		logger.info(sql);
		return jdbcTemplate.update(sql,level,show,image_url,id);
	}
}
