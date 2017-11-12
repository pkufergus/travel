package com.travel.service;


public interface MailOrderService {
	Integer insertMailOrder(String email,String leave_date,String back_date,String round_trip,
			String leave_city,String dest_city,String max_price);
}
