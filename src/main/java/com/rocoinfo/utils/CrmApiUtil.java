package com.rocoinfo.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.rocoinfo.interceptor.sign.NewCrmApiSignatureHelper;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.shiro.ShiroUser;


/**
 * 功能描述:调用CrmAPI的工具类，整合签名等操作
 * @author liupengfei
 * 2017年6月13日
 */
public class CrmApiUtil {

	// http请求工具代理对象
	private static final OkHttpClient httpClient;

	static {
		httpClient = new OkHttpClient().newBuilder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build();
	}
	
	
	
	/**
	 * 
	 * 函数功能描述:通过post方式，调用crmapi的http接口
	 * @param url
	 * @param reqParam
	 * @return
	 * @throws IOException
	 */
	public static String post(String url,Object reqParam,String storeName){
		try {
			//根据指定的门店，匹配调用哪套crmapi接口
			Map<String, String> hostNamesMap = JsonUtils.fromJsonAsMap(PropertyHolder.getCrmApiUrlArrJson(), String.class, String.class);
			//需要调用老版的接口
			if (hostNamesMap.containsKey(storeName)) {
				String aimHostName = hostNamesMap.get(storeName);
				//为了不让该工具类(CrmApiUtil)的调用者修改代码，处理下他们传入的url
				if(url.startsWith("null")){
					url = url.replace("null", aimHostName);
				}
				else {
					url = url.replace(PropertyHolder.getCrmApiUrl(), aimHostName);
				}
			}
			else{
				return JsonUtils.toJson(StatusDto.buildFailure("无法确定当前门店调用哪套老版crmapi"));
			}

			//判断下传入的参数
			String reqJson = null;
			if (reqParam != null) {
				reqJson = JsonUtils.toJson(reqParam);
			}
			//对请求入参json串做签名
			StatusDto signStatusDto = (StatusDto) CrmApiSignatureUtil.makeSignature(reqJson);
			//判断签名结果
			if (signStatusDto.isSuccess()) {
				String secretKey = signStatusDto.getData().toString();
				FormBody body = new FormBody.Builder().add("reqJson", reqJson).add("secretKey", secretKey).build();
				Request request = new Request.Builder().url(url).post(body).build();
				Response response = httpClient.newCall(request).execute();
				if (response.isSuccessful()) {
					return response.body().string();
				} else {
					throw new IOException("Unexpected code " + response);
				}
			} else {
				return JsonUtils.toJson(StatusDto.buildFailure("调用老版数据接口返回失败"));
			}
		}
		catch (Exception httpExp){
			return JsonUtils.toJson(StatusDto.buildFailure("调用老版数据接口发生异常"));
		}
	}
	
	
	/**
	 * 
	 * 函数功能描述:调用crmapi的http接口的重载方法
	 * 未指定门店，使用当前登录用户配置的门店
	 * @param url
	 * @param reqParam
	 * @return
	 * @throws IOException
	 */
	public static String post(String url,Object reqParam) {
		/*根据当前系统用户的门店，决定调用哪套crmapi*/
		//取到当前登录的用户
		ShiroUser curUser = WebUtils.getLoggedUser();
		if(curUser == null || StringUtils.isBlank(curUser.getStoreCode())) {
			return JsonUtils.toJson(StatusDto.buildFailure("员工门店信息缺失，操作失败"));
		}
		return post(url,reqParam,curUser.getStoreCode());
	}


	/**
	 * 对接新订单系统创建订单接口，为了让选材系统可以生成新订单测试
	 * @param url
	 * @param reqParam
	 * @return
	 * @throws IOException
	 */
	public static String postToNewOrderApi(String url,Object reqParam){
		try {
			url = PropertyHolder.getCrmApiUrl() + url;
			//判断下传入的参数
			String reqJson = null;
			if (reqParam != null) {
				reqJson = JsonUtils.toJson(reqParam);
			}

			//对请求入参json串做签名
			Map<String,Object> paramMap = new HashedMap();
			paramMap.put("reqJson",reqJson);
			String secretKeyStr = NewCrmApiSignatureHelper.getKey(paramMap,"");

			Map<String,Object> requestKeyMap = new HashMap();
			requestKeyMap.put("reqJson",reqJson);
			requestKeyMap.put("key",secretKeyStr);
			String requestKeyStr = JsonUtils.toJson(requestKeyMap);

			//发起请求
			FormBody body = new FormBody.Builder().add("requestKey",requestKeyStr).build();
			Request request = new Request.Builder().url(url).post(body).build();
			Response response = httpClient.newCall(request).execute();
			if (response.isSuccessful()) {
				return response.body().string();
			} else {
				return JsonUtils.toJson(StatusDto.buildFailure("调用新版数据接口返回失败"));
			}
		}
		catch (Exception httpExp){
			httpExp.printStackTrace();
			return JsonUtils.toJson(StatusDto.buildFailure("调用新版数据接口发生异常"));
		}
	}


	/**
	 * 根据门店编号判断是否需要调用老版的crmapi接口
	 * 门店是为当前登录人配置的门店
	 * @return
	 */
	public static boolean shouldUseOldApi(String storeCode){

		//根据指定的门店，匹配调用哪套crmapi接口
		Map<String, String> hostNamesMap = JsonUtils.fromJsonAsMap(PropertyHolder.getCrmApiUrlArrJson(), String.class, String.class);
		return hostNamesMap.containsKey(storeCode);
	}

	/**
	 * 根据门店编号判断是否需要调用老版的crmapi接口,根据当前登录人的门店
	 * @return
	 */
	public static boolean shouldUseOldApi(){
		String storeCode = WebUtils.getLoggedUser().getStoreCode();
		return shouldUseOldApi(storeCode);
	}
}
