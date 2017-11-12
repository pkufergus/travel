package com.travel.service;

import java.util.List;

import com.travel.pojo.Comment;
import com.travel.pojo.Menu;

public interface MenuService {
	List<Menu> getMenuList(Integer pageNow , Integer pageSize);
	
	Object addMenu(String fileDir , String srcPlace,String destPlace , String descripton,String href,String country
			,String level,String show,String image_url);
	
	List<Menu> webMenuList(String country);
	
	List<Menu> getComment(String fromCode,String toCode);
	
	Integer deleteMenu(String id);
	
	Integer addComment(String id ,String comment,String fromCode,String toCode);
	
	List<Comment> getComments(String id);
	
	Integer deleteCommentByCid(String cid);
	
	List<Comment> getComments(String fromCode,String toCode);
	
}
