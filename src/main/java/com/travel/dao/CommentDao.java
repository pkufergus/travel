package com.travel.dao;

import java.util.List;

import com.travel.pojo.Comment;

public interface CommentDao {
	List<Comment> getCommentById(String id);
	
	Integer updateComment(String id,String description);
	
	Integer updateLevel(String level,String id,String show,String image_url);
}
