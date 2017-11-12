package com.travel.service;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface UploadService {
	public Object uploadImage(CommonsMultipartFile[] files,String fileDir);
}
