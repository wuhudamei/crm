package com.rocoinfo.rest.task;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.entity.customer.CustomerHouse;
import com.rocoinfo.entity.task.TaskDistribute;
import com.rocoinfo.entity.dataSource.DataSource;

import com.rocoinfo.shiro.ShiroUser;
import com.rocoinfo.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rocoinfo.common.BaseController;
import com.rocoinfo.service.dataScource.DataSourceService;
import com.rocoinfo.service.task.TaskDistributeService;
import com.rocoinfo.utils.JsonUtils;

/**
 * <dl>
 * <dd>Description: 通用的对外任务线索派发相关Controller</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-6-6 16:20:07</dd>
 * <dd>@author：liupengfei</dd>
 * </dl>
 */

@RestController
@RequestMapping(value = "/open/api/task")
public class TaskOpenApiController extends BaseController {
	
	@Autowired
	private TaskDistributeService taskService;
	
	@Autowired
	private DataSourceService dsService;
	
	/**
	 * 各个渠道通用的创建任务调用的对公接口
	 */
	@RequestMapping(value="/newTask",method=RequestMethod.POST)
	public StatusDto createTasks(HttpServletRequest request){
		try{
			String callId = request.getParameter("callId");
			String reqJsonStr = request.getParameter("reqJson");
			if(StringUtils.isBlank(reqJsonStr) || StringUtils.isBlank(callId))
				return StatusDto.buildFailure("参数缺失");
			List<TaskDistribute> taskList = JsonUtils.fromJsonAsList(reqJsonStr, TaskDistribute.class);
			for (TaskDistribute task : taskList) {
				task.setCreator("system");
			}
			
			//查询数据来源配置
			DataSource tasksDs = dsService.getDataSourceByCallId(callId);
			
			//持久化任务
			taskService.addNewTasks(taskList,tasksDs.getSourceCode());
			
			return StatusDto.buildSuccess("任务保存成功");
		}
		catch(Exception e){
			e.printStackTrace();
			return StatusDto.buildFailure("保存任务时发生异常");
		}
	}


	/**
	 * 从财务同步项目大小定信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/syncdeposit",method=RequestMethod.POST)
	public StatusDto syncOrderDepositInfo(HttpServletRequest request){
		try{

			return null;
		}catch (Exception exp){
			return null;
		}
	}
}
