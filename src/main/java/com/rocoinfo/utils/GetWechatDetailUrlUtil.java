package com.rocoinfo.utils;

import com.rocoinfo.common.PropertyHolder;

/**
 * 获取微信模板消息详情url
 * 
 * @author Andy 2017-6-26 15:41:44
 *
 */
public class GetWechatDetailUrlUtil {

	/**
	 * 获取微信模板消息详情url
	 * @param redirectUrl	模板详情需要跳转的url
	 * @return
	 */
	public static String getWechatDetailUrl(String redirectUrl) {
		StringBuilder wechatUrl = new StringBuilder();
		wechatUrl.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=").append(PropertyHolder.getWechatAppid())
				 .append("&redirect_uri=http%3A%2F%2F").append(PropertyHolder.getOauthCenterUrl())
				 .append("%2Foauth%2Fmenu%2Fcode%3Fappid%3D").append(PropertyHolder.getOauthCenterAppid())
				 .append("%26redirect_url%3Dhttp%3A%2F%2F").append(PropertyHolder.getBaseUrl())
				 .append("%2FoauthCallBack&response_type=code&scope=snsapi_base&state=").append(redirectUrl)
				 .append("#wechat_redirect");
		return wechatUrl.toString();
	}
}
