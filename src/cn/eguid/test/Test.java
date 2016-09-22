package cn.eguid.test;

import cc.eguid.livepush.entity.LivePushEntity;
import cn.eguid.livePushServer.redisManager.RedisMQ;
import cn.eguid.livePushServer.util.JsonUtil;

public class Test {
public static void main(String[] args) throws InterruptedException
{
	LivePushEntity liveInfo=new LivePushEntity("test2", "rtsp://admin:admin@192.168.2.236:37779/cam/realmonitor?channel=1&subtype=0", "rtmp://192.168.30.21/live/", "h264", "flv", "25", "720x480", "2");
	RedisMQ.publish("push",JsonUtil.ObjtoString(liveInfo));
	Thread.sleep(60*1000);
	RedisMQ.publish("close", JsonUtil.ObjtoString(liveInfo));
}
}
