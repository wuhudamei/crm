package com.rocoinfo.quartz;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocoinfo.Constants;
import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.entity.schedule.Schedule;
import com.rocoinfo.entity.template.WechatBusinessCallReminderTemplate;
import com.rocoinfo.enumeration.ScheduleSourceType;
import com.rocoinfo.service.schedule.ScheduleService;
import com.rocoinfo.utils.DateUtils;
import com.rocoinfo.utils.GetWechatDetailUrlUtil;
import com.rocoinfo.utils.HttpUtil;
import com.rocoinfo.utils.JsonUtils;

/**
 * 日程提醒定时任务
 * 
 * @author Andy 2017-6-20 13:29:04
 */
@Service("scheduleRemind")
public class ScheduleRemind {

	@Autowired
	private ScheduleService scheduleService;

	/**
	 * 定时任务调用方法
	 */
	public void executor() {
		List<Schedule> scheduleList = scheduleService.findScheduleByDate(new Date());
		Map<String, String> contentMap = new HashMap<String, String>();
		if (scheduleList != null && scheduleList.size() > 0) {
			List<WechatBusinessCallReminderTemplate> templateList = new ArrayList<WechatBusinessCallReminderTemplate>();
			scheduleList.forEach(schedule -> {
				if (ScheduleSourceType.INVITATIONSTORE.name().equals(schedule.getScheduleType())) { // 邀约到店
					setMap(schedule.getJobNum(), "【" + schedule.getScheduleNum() + "个邀约到店】", contentMap);
				} else if (ScheduleSourceType.ROOMRESERVATIONS.name().equals(schedule.getScheduleType())) { // 预约量房
					setMap(schedule.getJobNum(), "【" + schedule.getScheduleNum() + "个预约量房】", contentMap);
				} else { // 其他
					setMap(schedule.getJobNum(), "【" + schedule.getScheduleNum() + "个其他】", contentMap);
				}
			});
			for (Map.Entry<String, String> entry : contentMap.entrySet()) {
				WechatBusinessCallReminderTemplate wbct = new WechatBusinessCallReminderTemplate();
				wbct.setHead("您今天的日程安排");
				wbct.setUrl(GetWechatDetailUrlUtil.getWechatDetailUrl("/wx/api/calendar.html"));
				wbct.setJobNo(entry.getKey());
				wbct.setKeyword1("今日有" + entry.getValue() + "的日程需要处理,请做好准备工作");
				wbct.setKeyword2(DateUtils.format(new Date(), DateUtils.DATE_SMALL_STR) + " 9:00至18:00");
				templateList.add(wbct);
			}
			HttpUtil.post(PropertyHolder.getOABaseUrl() + Constants.SCHEDULE_REMINDER_URL, JsonUtils.toJson(templateList));
		}
	}

	/**
	 * 拼接模板消息内容
	 * 
	 * @param jobNum
	 *            员工编号
	 * @param name
	 *            日程类型名称
	 * @param contantMap
	 *            内容map
	 * @return
	 */
	private Map<String, String> setMap(String jobNum, String name, Map<String, String> contantMap) {
		if (contantMap.get(jobNum) == null) {
			contantMap.put(jobNum, name);
		} else {
			contantMap.put(jobNum, contantMap.get(jobNum) + name);
		}
		return contantMap;
	}
}
