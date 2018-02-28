package wechat;

import java.io.IOException;

import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.utils.HttpUtil;

public class TestGenerateTask {

	
	public static void main(String[] args) {
		String reqJson="[{\"customerName\":\"张三\",\"customerMobile\":\"13111111111\","
							+ "\"callTime\":null,\"callAnswered\":null,\"store\":\"BJ\",\"promoteSource\":\"官网\"},"
					  + "{\"customerName\":\"李四\",\"customerMobile\":\"13222222222\","
							+ "\"callTime\":null,\"callAnswered\":null,\"store\":\"BJ\",\"promoteSource\":\"朋友圈\"},"
					  + "{\"customerName\":\"王五\",\"customerMobile\":\"13333333333\","
							+ "\"callTime\":null,\"callAnswered\":null,\"store\":\"BJ\",\"promoteSource\":\"朋友圈\"},"
					  + "{\"customerName\":\"马六\",\"customerMobile\":\"13444444444\","
							+ "\"callTime\":null,\"callAnswered\":null,\"store\":\"BJ\",\"promoteSource\":\"朋友圈\"},"
					  + "{\"customerName\":\"猴七\",\"customerMobile\":\"13555555555\","
							+ "\"callTime\":null,\"callAnswered\":null,\"store\":\"BJ\",\"promoteSource\":\"朋友圈\"},"
					  + "{\"customerName\":\"鬼八\",\"customerMobile\":\"13666666666\","
							+ "\"callTime\":null,\"callAnswered\":null,\"store\":\"BJ\",\"promoteSource\":\"朋友圈\"}]";
		try {
			String url = "http://crmtest.mdni.net.cn/open/api/task/newTask";
//			String url = "http://localhost:14089/open/api/task/newTask";
			String mess = HttpUtil.post(url, reqJson, "jiy789667_yhlpfaq3");
			
			System.out.println(mess);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
