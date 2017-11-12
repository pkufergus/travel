package com.travel.service;

import com.travel.pojo.Comment;

public interface CommentService {
	Comment getCommentById(String id);
	
	Integer updateComment(String id,String description,String fromCode,String toCode,String level
			,String show,String image_url);
	
	String resetAllMenu();
}
