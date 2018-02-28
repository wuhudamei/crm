package com.rocoinfo.rest.task;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rocoinfo.common.BaseController;
import com.rocoinfo.entity.callCentre.CustomerCallInLog;
import com.rocoinfo.entity.task.TaskDistribute;
import com.rocoinfo.service.callCentre.CallCentreService;
import com.rocoinfo.service.task.TaskDistributeService;


/**
 * 功能描述:供老话务中心把任务推送到新CRM用的接口
 * @author phil
 * 2017年6月26日
 */

@RestController
@RequestMapping("/oldhw/api/task")
public class TaskFromBJCallCentreApiController extends BaseController {

	
	@Autowired
	private CallCentreService callCenrtreService;

	
	@RequestMapping(value="/receiveTask",method=RequestMethod.POST)
	public Object receiveTaskFromOldCallCentre(HttpServletRequest request){
		String reqListStr = request.getParameter("list");
		JSONObject rtJson = callCenrtreService.saveTaskFromOldCallCentre(reqListStr);
		return rtJson.toString();
	}

	
}
