package com.travel.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.travel.service.UploadService;

@Controller
@RequestMapping(value = "/upload")
public class UploadController {
	
	@Autowired
	private UploadService uService;
	
	
	 @ResponseBody
	    @RequestMapping(value = "/upload_image.json", method = RequestMethod.POST)
	    public Object upLoadImage(@RequestParam("aaa") CommonsMultipartFile[] files,HttpServletRequest request, HttpServletResponse response){
		System.out.println(request.getSession().getServletContext().getRealPath("/asset/img/"));
		 return uService.uploadImage(files,request.getSession().getServletContext().getRealPath("/asset/img/")+"/");
	 }
}
