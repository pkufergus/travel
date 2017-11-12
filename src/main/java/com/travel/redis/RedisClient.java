package com.travel.redis;

import com.travel.util.ServiceUtil;

import redis.clients.jedis.Jedis;

public class RedisClient {
	
	public static Jedis getRedisClient(){
		Jedis jedis = null;
		jedis = new Jedis(ServiceUtil.getRedisHost(), ServiceUtil.getRedisPort());
		return jedis;
	}
	
	public static void main(String[] args) {
		Jedis jedis = getRedisClient();
		 System.out.println(jedis.exists("age")+","+jedis.get("age"));
		 System.out.println(jedis.set("lvyf", "Man"));
		 jedis.append("lvyf", "\nReal");
		 System.out.println(jedis.get("lvyf"));
		 System.out.println(jedis.randomKey());
	}
}
