package cn.eguid.livePushServer.redisManager;

import java.util.Map;

import redis.clients.jedis.Jedis;

/**
 * redis的持久化操作
 * 
 * @author wangliang
 *
 */
public class RedisStore {
	/**
	 * 从jedis连接池获取jedis操作实例
	 * 
	 * @return
	 */
	public  Jedis getJedis() {
		return RedisPoolManager.getJedis();
	}

	public  void set(String key, String values) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.set(key, values);
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}

	public  void set(byte[] key, byte[] values) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.set(key, values);
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}

	public  String get(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.get(key);
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}

	public  byte[] get(byte[] key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.get(key);
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}
	public boolean isHave(String key)
	{
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.exists(key);
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}
	public boolean isHave(byte[] key)
	{
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.exists(key);
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}
	public Map<String, String> getAll(String key){
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.hgetAll(key);
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}
	public Map<byte[], byte[]> getAll(byte[] key){
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.hgetAll(key);
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}
}
