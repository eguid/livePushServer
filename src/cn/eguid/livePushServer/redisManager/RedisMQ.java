package cn.eguid.livePushServer.redisManager;

import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class RedisMQ {
	/**
	 * 从jedis连接池获取jedis操作实例
	 * @return
	 */
	public static Jedis getJedis() {
		return RedisPoolManager.getJedis();
	}

	/**
	 * 推入消息到redis消息通道
	 * 
	 * @param String
	 *            channel
	 * @param String
	 *            message
	 */
	public static void publish(String channel, String message) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.publish(channel, message);
		} finally {
			jedis.close();
		}
	}

	/**
	 * 推入消息到redis消息通道
	 * 
	 * @param byte[]
	 *            channel
	 * @param byte[]
	 *            message
	 */
	public void publish(byte[] channel, byte[] message) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.publish(channel, message);
		} catch (Exception e) {

		} finally {
			jedis.close();
		}

	}

	/**
	 * 监听消息通道
	 * @param jedisPubSub - 监听任务
	 * @param channels - 要监听的消息通道
	 */
	public static void subscribe(BinaryJedisPubSub jedisPubSub, byte[]... channels) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.subscribe(jedisPubSub, channels);
		} catch (Exception e) {

		} finally {
			jedis.close();
		}
	}

	/**
	 * 监听消息通道
	 * @param jedisPubSub - 监听任务
	 * @param channels - 要监听的消息通道
	 */
	public static void subscribe(JedisPubSub jedisPubSub, String... channels) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.subscribe(jedisPubSub, channels);
		} finally {
			jedis.close();
		}
	}

}


