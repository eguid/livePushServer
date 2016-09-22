package cn.eguid.livePushServer;

import cc.eguid.livepush.PushManager;
import cc.eguid.livepush.PushManagerImpl;
import cn.eguid.livePushServer.redisManager.RedisMQHandler;

/**
 * 推流器server端服务
 * @author wangliang
 *
 */
public class RunServer {
	private static PushManager pushManager= new PushManagerImpl();
	private static Thread t1 = new Thread(new RedisMQHandler (pushManager, "push"));
	private static Thread t2 = new Thread(new RedisMQHandler (pushManager, "close"));
	private static  Thread t3 = new Thread(new RedisMQHandler (pushManager, "clear"));
	public static  void runPushServer(){
		t1.start();
	}
	public static void runClosePushServer(){
		t2.start();
	}
	public static void runClearPushServer(){
		t3.start();
	}
	public static void closeRun(int num){
		if(num==1)
		{
			t1.destroy();
		}else if(num==2)
		{
			t2.destroy();
		}else if(num==3){
			t3.destroy();
		}
	}
	public static void main(String[] args) throws InterruptedException
	{
		runPushServer();
		runClosePushServer();
		runClearPushServer();
//		Thread.sleep(60*1000);
//		closeRun(1);
//		closeRun(2);
//		closeRun(3);
	}
}
