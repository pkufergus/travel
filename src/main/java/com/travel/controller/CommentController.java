package com.travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.travel.service.CommentService;

@Controller
@RequestMapping(value = "/comment")
public class CommentController {
	@Autowired
	private CommentService cService;
	
	@ResponseBody
	@RequestMapping(value = { "/get_comment.json" } , method = {RequestMethod.GET}, produces = {"application/json;charset=UTF-8"})
	 public Object getCommentById(@RequestParam("id")String id){
		 return JSON.toJSONString(cService.getCommentById(id));
	 }
	
	@ResponseBody
	@RequestMapping(value = { "/update_comment.json" } , method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
	 public Object addComment(@RequestParam("id")String id,@RequestParam("description")String description,
			 @RequestParam("fromCode")String fromCode,@RequestParam("toCode")String toCode,@RequestParam("level")String level
			 ,@RequestParam("show")String show,@RequestParam("image_url")String image_url){
		 return JSON.toJSONString(cService.updateComment(id,
				 description, fromCode, toCode,level,show,image_url));
	}
	
	@ResponseBody
	@RequestMapping(value = { "/reset_menu.json" } , method = {RequestMethod.GET}, produces = {"application/json;charset=UTF-8"})
	 public Object getResetMenu(){
		 return JSON.toJSONString(cService.resetAllMenu());
	 }
}
