package com.travel.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static Timestamp currentTimeStamp(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(new Date());
		Timestamp ts = Timestamp.valueOf(time); 
		return ts;
	}
	public static String getCurrentDate(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(new Date());
		return time;
	}
	public static Date StringToDate(String time){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = df.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	public static String getThreeDaysAgo(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - 3);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(cal.getTime());
	}
	
	public static String getMonthFirstDay(int flag){
		Calendar c = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		c.set(Calendar.MONTH, c.get(Calendar.MONTH)+flag);
		c.set(Calendar.DAY_OF_MONTH, 1);
		 
		return format.format(c.getTime());
	}
	
	public static String getMonthLastDay(int flag){
		Calendar c = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		c.set(Calendar.MONTH, c.get(Calendar.MONTH)+flag+1);
		c.set(Calendar.DAY_OF_MONTH, 0);
		
		return format.format(c.getTime());
	}
	
	public static String getMonthLastDay(int flag,int day){
		Calendar c = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		c.set(Calendar.MONTH, c.get(Calendar.MONTH)+flag+1);
		c.set(Calendar.DAY_OF_MONTH, day);
		
		return format.format(c.getTime());
	}
	
	public static String getMonthFirstDay(int flag,int day){
		Calendar c = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		c.set(Calendar.MONTH, c.get(Calendar.MONTH)+flag);
		c.set(Calendar.DAY_OF_MONTH, day);
		 
		return format.format(c.getTime());
	}
	
	public static String getNextMonthFirstDay(int month){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, month);
        return sdf.format(calendar.getTime());
	}
	
	public static String getNextMonthFirstDay(int month,int days){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, days);
        calendar.add(Calendar.MONTH, month);
        return sdf.format(calendar.getTime());
	}
	
	public static String getNextMonthLastDay(int month){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();  
		calendar.add(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return sdf.format(calendar.getTime());
	}
	
	public static String getNextMonthLastDay(int month,int days){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();  
		calendar.add(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH)+days);
        return sdf.format(calendar.getTime());
	}
	
	public static String nextMonth(int month){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();  
		calendar.add(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		String monthTemp = "";
		monthTemp = sdf.format(calendar.getTime()).split("-")[1];
        return  monthTemp;
	}
	
	public static String currentYear(int month){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();  
		calendar.add(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		String monthTemp = "";
		monthTemp = sdf.format(calendar.getTime()).split("-")[0];
        return  monthTemp;
	}
	
	public static void main(String[] args) {
//		String fromD1 = DateUtil.getNextMonthFirstDay(1);//下个月1号
//		String toD1 = DateUtil.getNextMonthLastDay(1);//下月31号
//		String fromD1S = DateUtil.getNextMonthFirstDay(1,14);//下个月1号加14
//		String toD1S = DateUtil.getNextMonthLastDay(1,14);//下月31号加14
//		System.out.println(fromD1);
//		System.out.println(toD1);
//		System.out.println(fromD1S);
		System.out.println(getCurrentDate());
	}
}
