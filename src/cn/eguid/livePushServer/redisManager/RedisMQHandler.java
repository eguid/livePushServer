package cn.eguid.livePushServer.redisManager;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import cc.eguid.livepush.PushManager;
import redis.clients.jedis.JedisPubSub;

public class RedisMQHandler extends JedisPubSub implements Runnable {

	private PushManager pushManager = null;// 推流器
	private String[] channel = null;// 监听的消息通道

	public RedisMQHandler(PushManager pushManager, String... channel) {
		super();
		this.pushManager = pushManager;
		this.channel = channel;
	}

	@Override
	public void run() {
		// 监听channel通道的消息
		RedisMQ.subscribe(this, channel);
	}

	@Override
	// 接收到消息后进行分发执行
	public void onMessage(String channel, String message) {
		if (message == null || "".equals(message.trim())) {
			return;
		}
		JSONObject jsonObj = new JSONObject(message);
		if ("push".equals(channel)) {
			// 开启推流
			Map<String, Object> map = jsonObj.toMap();
			System.out.println("推流消息，开启推流：" + map);
			String pushappName=(String) map.get("appName");
			if(pushManager.isHave(pushappName)){
				//已经发不过这个应用应返回一个错误消息
				return;
			}
			String appName=null;
			//开启推流
			if((appName=pushManager.push(map))!=null)
			{
				System.out.println(appName+",开启推流成功！");
				// 推流完成后还需要发布一个成功消息到返回队列
				return;
			}else{
				//推流失败返回操作消息
			}
		} else if ("close".equals(channel)) {
			// 关闭推流
			String appName = jsonObj.getString("appName");
			System.out.println("关闭消息，关闭应用：" + appName);
			//关闭应用
			if(pushManager.closePush(appName))
				{
				
				};
			
		} else if ("clear".equals(channel)) {
			System.out.println("清理消息，清除所有正在推流的应用：" + channel+","+message);
			// 清理所有推流
			Set<String> set=pushManager.viewAppName();
			Set<String> newSet=null;
			for(String name:set)
			{
				if(!pushManager.closePush(name)){
					if(newSet==null){
					newSet=new HashSet<String>();
					}
					//关闭失败记录下应用名返回错误消息
					newSet.add(name);
				}
			}
			if(newSet==null){
				//清理完毕应返回正确消息
				System.out.println("end------关闭所有应用完毕！");
			}else{
				//把关闭失败的应用名返回
				System.out.println("有部分应用未正确关闭");
			}
			
			
		}

	}
}