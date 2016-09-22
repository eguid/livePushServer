package cn.eguid.livePushServer.util;

import org.json.JSONObject;

import cc.eguid.livepush.entity.LivePushEntity;

public class JsonUtil {
public static String ObjtoString(LivePushEntity liveInfo){
	JSONObject jsonObj=null;
	try{
	jsonObj=new JSONObject(liveInfo);
	return jsonObj.toString();
	}finally{
		jsonObj=null;
	}
	
}
}
