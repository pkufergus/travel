package com.travel.service;

public interface AjaxRequestService {
	String getURLContent(String urlStr);

	String SendGET(String url);
	
	Object autoComplete(String prefix);
	
	Object autoCompleteAir(String mode,String prefix);
}
