package com.cache.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
	private static JedisPool pool=null;
	public static JedisPool getPool() {
		if(null==pool) {
			String ip="127.0.0.1";
			int port=6379;
			JedisPoolConfig config=new JedisPoolConfig();
			config.setMaxTotal(300);
			config.setMaxIdle(200);
			config.setTestOnReturn(true);
			config.setMaxWaitMillis(200);
			pool=new JedisPool(config,ip,port);
		}
		return pool;
	}

	public static void returnResource(JedisPool pool,Jedis redis) {
		if(null!=redis) {
			redis.close();
		}
	}
}
