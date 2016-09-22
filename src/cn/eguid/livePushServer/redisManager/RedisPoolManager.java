package cn.eguid.livePushServer.redisManager;

import java.util.Set;
import java.util.Map.Entry;

import cn.eguid.livePushServer.util.LoadConfUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 管理redis
 * 
 * @author wangliang
 *
 */
public class RedisPoolManager {
	private static JedisPool jedisPool = null;
	static {
		init("redisConf.properties");
		if(!isOpen())
		{
			init("localRedisConf.properties");
		}
	}

	public static void init(String path) {
		String host = null;
		int port = 6379;
		int timeout = 5000;
		String password = null;
		int database = 1;
		boolean ssl = false;
		JedisPoolConfig poolConfig = null;
		poolConfig = new JedisPoolConfig();
		
		Set<Entry<Object, Object>> set = LoadConfUtil.loadProSet(path);
		
		for (Entry<Object, Object> e : set) {
			System.out.println(e.getKey() + "," + e.getValue());
			if ("host".equals(e.getKey())) {
				host = (String) e.getValue();
			} else if ("port".equals(e.getKey())) {
				port = Integer.valueOf((String) e.getValue());
			} else if ("timeout".equals(e.getKey())) {
				timeout = Integer.valueOf((String) e.getValue());
			} else if ("password".equals(e.getKey())) {
				String a = (String) e.getValue();
				if (a == null || "".equals(a) || "".equals(a.trim())) {
					password = null;
				} else {
					password = a;
				}
			} else if ("database".equals(e.getKey())) {
				database = Integer.valueOf((String) e.getValue());
			} else if ("ssl".equals(e.getKey())) {
				ssl = Boolean.valueOf((String) e.getValue());
			}
		}
		jedisPool = new JedisPool(poolConfig, host, port, timeout, password, database, ssl);
		
	}
	/**
	 * 连接池是否已经关闭
	 * @return
	 */
	public static synchronized boolean isClosed()
	{
		return jedisPool.isClosed();
	}
	/**
	 * 是否打开连接池是否能够开启连接
	 * @return true-开启正常，false-无法连接
	 */
	public static synchronized boolean isOpen(){
		Jedis jedis=null;
		try{
		jedis=jedisPool.getResource();
		return true;
		}catch(Exception e){
			return false;
		}finally{
			jedis.close();
		}
	}
	public static synchronized void close() {
		jedisPool.destroy();
	}

	public static Jedis getJedis() {
		return jedisPool.getResource();
	}
}
