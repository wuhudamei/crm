package com.rocoinfo.utils;

import org.apache.commons.lang3.StringUtils;

import com.rocoinfo.Constants;
import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.entity.template.WechatBusinessCallReminderTemplate;

public class TaskDistributeTemplateUtil {

	/**
	 * 发送	新派单任务模板消息提醒
	 * @param jobNo	员工号
	 * @param customerName	客户姓名
	 * @param customerMobile	客户电话
	 * @return
	 */
	public static void sendTaskDistributeReminder(String jobNo, String customerName, String customerMobile) {
		WechatBusinessCallReminderTemplate wbct = new WechatBusinessCallReminderTemplate();
		wbct.setHead("派单任务提醒");
		wbct.setUrl(GetWechatDetailUrlUtil.getWechatDetailUrl("/wx/api/appindex.html"));
		wbct.setJobNo(jobNo);
		
		//混淆手机号：String.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
		String poneNoForShow = customerMobile;
		if(StringUtils.isNotBlank(customerMobile) && customerMobile.length()>7)
			poneNoForShow = customerMobile.substring(0, 3)+"****"+customerMobile.substring(7);
		
		wbct.setKeyword1("您有一个新的派单任务,姓名:" + customerName + ",电话:" + poneNoForShow + ",请尽快处理!");
		wbct.setKeyword2(DateUtils.format(DateUtils.currentDate(), "yyyy年MM月dd日"));
		HttpUtil.post(PropertyHolder.getOABaseUrl() + Constants.TASK_DISTRIBUTE_URL,
				JsonUtils.toJson(wbct));
	}
}
