package com.travel.pojo;

import java.util.List;

public class MasterCountWeb {
	private String fromP;
	
	private String toP;
	
	private String min_price;
	
	private String price_type;
	
	private List<PriceNew> objList;
	
	private String url;
	
	private String update_time;
	
	

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getFromP() {
		return fromP;
	}

	public void setFromP(String fromP) {
		this.fromP = fromP;
	}

	public String getToP() {
		return toP;
	}

	public void setToP(String toP) {
		this.toP = toP;
	}

	public String getMin_price() {
		return min_price;
	}

	public void setMin_price(String min_price) {
		this.min_price = min_price;
	}

	public String getPrice_type() {
		return price_type;
	}

	public void setPrice_type(String price_type) {
		this.price_type = price_type;
	}

	public List<PriceNew> getObjList() {
		return objList;
	}

	public void setObjList(List<PriceNew> objList) {
		this.objList = objList;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
