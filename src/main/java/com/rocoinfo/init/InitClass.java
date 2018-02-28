package com.rocoinfo.init;

import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.quartz.QuartzTaskApi;
import com.rocoinfo.service.core.EmployeeQueueCore;
import com.rocoinfo.service.core.TaskDistributeCore;
import com.rocoinfo.service.core.TaskQueueCore;
import com.rocoinfo.service.dataScource.DataSourceService;

public class InitClass extends HttpServlet {

	/** **/
	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(InitClass.class); // 日志记录

	/**
	 * web服务启动后加载的方法.
	 */
	public void init() throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		TaskDistributeCore taskDistributeCoreService = context.getBean(TaskDistributeCore.class);// 获取任务派发核心service
		TaskQueueCore taskQueueCoreService = context.getBean(TaskQueueCore.class);// 获取任务队列相关service
		EmployeeQueueCore employeeQueueCoreService = context.getBean(EmployeeQueueCore.class);// 获取员工队列相关service

		//初始化任务队列
		boolean taskQueue = taskQueueCoreService.taskQueueIsEmpty();
		if (taskQueue) {
			taskDistributeCoreService.initTaskToQueue();
		} else {
			logger.info("【任务队列不为空!】");
		}
		//初始化当前生效的派发规则的编号
		taskDistributeCoreService.initDistributeRule();

		// 初始化跟单员相关参数包括:跟单员队列,跟单员接单规则,跟单员奖励
		boolean empQueueEmpty = employeeQueueCoreService.getEmployeeQueueIsEmpty();
		if (empQueueEmpty) {
			taskDistributeCoreService.initEmployeeParamToQueue(false,null);
		} else {
			logger.info("【跟单员队列不为空!】");
		}

		//创建定时任务
		try {
			QuartzTaskApi.createQuartz(taskDistributeCoreService);
			logger.info("Create task distribute job success !");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("创建定时任务失败!" + e.getMessage());
		}
		//初始化公司内部开放接口调用凭证map
		try{
			initCallCertificateMap(context);
		}catch (Exception exp){
			exp.printStackTrace();
			logger.error("初始化公司内部开放接口调用凭证map失败!" + exp.getMessage());
		}

	}
	
	
	
	/**
	 * 
	 * 函数功能描述:初始化保存外围系统调用本系统open接口的callId和secret的map
	 * @param context
	 */
	private void initCallCertificateMap(WebApplicationContext context){
		PropertyHolder.callCertificateMap = new HashMap<String, String>();
		
		DataSourceService dsService = context.getBean(DataSourceService.class);
		dsService.syncCallCertificateMap();
	}
}
