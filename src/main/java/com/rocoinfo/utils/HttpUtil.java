package com.rocoinfo.utils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {

	// http请求工具代理对象
	private static final OkHttpClient httpClient;

	static {
		httpClient = new OkHttpClient();
	}

	/**
	 * 发送HTTP请求获取用户信息
	 * @param url	请求url
	 * @param appid	应用appid
	 * @param secret	应用秘钥
	 * @param code	认证中心返回的code
	 * @return
	 * @throws IOException
	 */
	public static String post(String url, String appid, String secret, String code,boolean socpe) throws IOException {
		FormBody body = new FormBody.Builder().add("appid", appid).add("secret", secret).add("code", code).add("scope", "true").build();

		Request request = new Request.Builder().url(url).post(body).build();

		Response response = httpClient.newCall(request).execute();
		if (response.isSuccessful()) {
			return response.body().string();
		} else {
			throw new IOException("Unexpected code " + response);
		}
	}
	
	/**
	 * 发送HTTP请求退出
	 * @param url	请求url
	 * @param appid	应用appid
	 * @param secret	应用秘钥
	 * @param jobNo 用户名
	 * @return
	 * @throws IOException
	 */
	public static String post(String url, String appid, String secret, String jobNo) throws IOException {
		FormBody body = new FormBody.Builder().add("appid", appid).add("secret", secret).add("username", jobNo).build();

		Request request = new Request.Builder().url(url).post(body).build();

		Response response = httpClient.newCall(request).execute();
		if (response.isSuccessful()) {
			return response.body().string();
		} else {
			throw new IOException("Unexpected code " + response);
		}
	}
	
	/**
	 * 发送数据到crm接口创建任务
	 * @param url
	 * @param reqJson
	 * @param callId
	 * @return
	 * @throws IOException
	 */
	public static String post(String url, String reqJson, String callId) throws IOException {
		FormBody body = new FormBody.Builder().add("reqJson", reqJson).add("callId", callId).build();

		Request request = new Request.Builder().url(url).post(body).build();

		Response response = httpClient.newCall(request).execute();
		if (response.isSuccessful()) {
			return response.body().string();
		} else {
			throw new IOException("Unexpected code " + response);
		}
	}
	
    public static String post(String url, String data) {
    	MediaType CONTENT_TYPE_FORM =
                MediaType.parse("application/json");
    	
        RequestBody body = RequestBody.create(CONTENT_TYPE_FORM, data);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new RuntimeException("Unexpected code" + response);
            }
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
