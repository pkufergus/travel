package com.travel.test;

import redis.clients.jedis.Jedis;





public class RedisClient {
	
	public static  void redisTest(){
		@SuppressWarnings("resource")
		Jedis jedis  = new Jedis("127.0.0.1", 6379);
		System.out.println("查看键为aa的是否存在："+jedis.exists("age"));  
	}
	
	public static void main(String[] args) {
		redisTest();
	}
}