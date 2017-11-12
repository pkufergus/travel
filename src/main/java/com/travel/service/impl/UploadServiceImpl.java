package com.travel.service.impl;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.travel.service.UploadService;

@Service
public class UploadServiceImpl implements UploadService {
	
	private static Logger logger = LoggerFactory
			.getLogger(UploadServiceImpl.class);

	@Override
	public Object uploadImage(CommonsMultipartFile[] files,String fileDir) {
		 for (CommonsMultipartFile file : files) {
			 try{
			  String fileName = file.getOriginalFilename();
			 
			  String filePath = fileDir+fileName;
			  logger.info("fileName="+fileName+",filePath="+filePath);
			  File localFile = new File(filePath);
		        try {
					file.transferTo(localFile);
				} catch (IllegalStateException | IOException e) {
					logger.error(e.getLocalizedMessage());
				}
			 }catch(Exception e){
				 logger.error(e.getLocalizedMessage());
			 }
		 }
		return null;
	}

}
