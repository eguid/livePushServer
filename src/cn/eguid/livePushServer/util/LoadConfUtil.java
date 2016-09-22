package cn.eguid.livePushServer.util;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 * 读取配置
 * 
 * @author wangliang
 *
 */
public class LoadConfUtil {
	private final static String projectRootPath = LoadConfUtil.class.getResource("/").getPath();//项目根路径
	private static volatile boolean isTrue;// 是否成功读取文件
	private static volatile boolean isSucc;//是否成功加载配置
	/**
	 * 读取文件流,相对于项目根目录
	 * @param confPath - 路径
	 * @param reader - 文件容器，会自动创建FileReader实例
	 * @return
	 */
	public static Reader loadFile(String confPath) {
		Reader reader=null;
		try {
			String loadPath = projectRootPath + confPath;
			System.out.println("加载配置路径："+loadPath);
			reader = new FileReader(loadPath);
			isTrue = true;
		} catch (FileNotFoundException e) {
			isTrue = false;
		}
		return reader;
	}

	/**
	 * 加载properties配置文件
	 * 
	 * @param confPath - 路径
	 * @param pro -配置
	 * @return
	 */
	public static boolean loadPro(String confPath, Properties pro) {
		Reader reader = null;
		try {
			reader=loadFile(confPath);
			if (isTrue) {
				pro.load(reader);
			}
			isSucc = true;
		} catch (FileNotFoundException e) {
			isSucc = false;
		} catch (IOException e) {
			isSucc=false;
		} finally {
			close(reader);
		}
		return isSucc;
	}
	public static Set<Entry<Object, Object>> loadProSet(String confPath){
		Properties pro=new Properties();
		if(loadPro(confPath,pro))
			{
			return pro.entrySet();
			}
		return null;
	}

	/**
	 * 是否加载文件成功
	 * 
	 * @return
	 */
	public static boolean isTrue() {
		return isTrue;
	}
	/**
	 * 是否加载配置成功
	 * @return
	 */
	public static boolean isSucc(){
		return isSucc;
	}
	public static void close(Closeable closeer) {
		try {
			if (closeer != null) {
				closeer.close();
			}
		} catch (IOException e) {
			System.out.println("关闭文件流时发生一个异常：" + e.getMessage());
		}
		closeer = null;
	}
}
