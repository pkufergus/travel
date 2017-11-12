package com.travel.pojo;

import java.sql.Date;

public class MailOrder {
	
	private Integer id;
	
	private String email;
	
	private String leave_date;
	
	private String back_date;
	
	private String round_trip;
	
	private String leave_city;
	
	private String dest_city;
	
	private String max_price;
	
	private String order_date;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	 

	public String getRound_trip() {
		return round_trip;
	}

	public void setRound_trip(String round_trip) {
		this.round_trip = round_trip;
	}

	public String getLeave_city() {
		return leave_city;
	}

	public void setLeave_city(String leave_city) {
		this.leave_city = leave_city;
	}

	public String getDest_city() {
		return dest_city;
	}

	public void setDest_city(String dest_city) {
		this.dest_city = dest_city;
	}

	public String getMax_price() {
		return max_price;
	}

	public void setMax_price(String max_price) {
		this.max_price = max_price;
	}

	public String getLeave_date() {
		return leave_date;
	}

	public void setLeave_date(String leave_date) {
		this.leave_date = leave_date;
	}

	public String getBack_date() {
		return back_date;
	}

	public void setBack_date(String back_date) {
		this.back_date = back_date;
	}

	public String getOrder_date() {
		return order_date;
	}

	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}

	 

 
	
	
}
