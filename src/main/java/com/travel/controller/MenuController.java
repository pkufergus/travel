package com.travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.travel.service.MenuService;
import com.travel.util.ConstantUtil;
import com.travel.util.PropFactory;


@Controller
@RequestMapping(value = "/menu")
public class MenuController implements ConstantUtil {
	
	@Autowired
	private MenuService mService;
	
	@ResponseBody
	@RequestMapping(value = { "/menulist.json" } , method = {RequestMethod.GET}, produces = {"application/json;charset=UTF-8"})
	 public Object getMenuList(@RequestParam("pageNow")String pageNow){
		 return JSON.toJSONString(mService.getMenuList(1, 10));
	}
	
	@ResponseBody
	@RequestMapping(value = { "/addMenu.json" } , method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
	 public Object addMenu(@RequestParam("srcPlace")String srcPlace,@RequestParam("destPlace")String destPlace,
			 @RequestParam("description")String description,@RequestParam("href")String href,@RequestParam("country")String country
			 ,@RequestParam("level")String level,@RequestParam("image_url")String image_url,@RequestParam("isHomePage")String isHomePage){
		 return JSON.toJSONString(mService.addMenu(PropFactory.getProp().getProperty(PHP_PATH),
				 srcPlace, destPlace, description,href,country,level,isHomePage,image_url));
	}
	
	@ResponseBody
	@RequestMapping(value = { "/webmenulist.json" } , method = {RequestMethod.GET}, produces = {"application/json;charset=UTF-8"})
	 public Object getWebMenuList(@RequestParam("country")String country){
		 return JSON.toJSONString(mService.webMenuList(country));
	}
	
	@ResponseBody
	@RequestMapping(value = { "/get_commnet.json" } , method = {RequestMethod.GET}, produces = {"application/json;charset=UTF-8"})
	 public Object getComment(@RequestParam("fromCode")String fromCode,@RequestParam("toCode")String toCode){
		 return JSON.toJSONString(mService.getComment(fromCode, toCode));
	}
	
	@ResponseBody
	@RequestMapping(value = { "/delete_menu.json" } , method = {RequestMethod.GET}, produces = {"application/json;charset=UTF-8"})
	 public Object deleteMenu(@RequestParam("id")String id){
		 return JSON.toJSONString(mService.deleteMenu(id));
	}
	
	@ResponseBody
	@RequestMapping(value = { "/add_comment.json" } , method = {RequestMethod.GET}, produces = {"application/json;charset=UTF-8"})
	 public Object addComment(@RequestParam("id")String id,@RequestParam("description")String description,
			 @RequestParam("fromCode")String fromCode,@RequestParam("toCode")String toCode){
		 return JSON.toJSONString(mService.addComment(id, description,fromCode,toCode));
	}
	
	@ResponseBody
	@RequestMapping(value = { "/get_comments.json" } , method = {RequestMethod.GET}, produces = {"application/json;charset=UTF-8"})
	 public Object getComments(@RequestParam("id")String id){
		 return JSON.toJSONString(mService.getComments(id));
	}
	
	@ResponseBody
	@RequestMapping(value = { "/delete_comments_by_cid.json" } , method = {RequestMethod.GET}, produces = {"application/json;charset=UTF-8"})
	 public Object deleteCommentsByCid(@RequestParam("cid")String cid){
		 return JSON.toJSONString(mService.deleteCommentByCid(cid));
	}
}
