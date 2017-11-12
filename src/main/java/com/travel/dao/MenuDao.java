package com.travel.dao;

import java.util.List;

import com.travel.pojo.City;
import com.travel.pojo.Comment;
import com.travel.pojo.Menu;

public interface MenuDao {
	List<Menu> getMenuList(Integer pageNow , Integer pageSize);
	
	Integer addMenu(String srcPlace,String destPlace , String descripton,String country,String level
			,String show,String image_url);
	
	Integer addDescription(Integer id , String description,String href);
	
	Integer getId(String srcPlace , String destPlace);
	
	List<Menu> getMenuList(String country);
	String getCityNameByCode(String cityCode);
	
	List<Menu> getComment(String fromCode,String toCode);
	
	Integer deleteMenu(String id);
	
	Integer deleteComment(String id);
	
	Integer addComment(String id ,String comment);
	
	List<Comment> getComments(String id);
	
	Integer deleteCommentByCid(String cid);
	
	List<Comment> getComments(String fromCode,String toCode);
	
	List<Menu> isMenuExists(String fromCode, String toCode,
			String country);
	
	City getCityByCode(String cityCode);
	
	City getFromCityByCid(String cid);
	
	City getToCityByCid(String cid);
	
	List<Menu> getMenuListNotChina();
}
