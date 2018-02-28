package com.rocoinfo.quartz;


import com.rocoinfo.service.core.EmployeeQueueCore;
import com.rocoinfo.service.core.TaskDistributeCore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 初始化跟单员队列,接单规则,奖励单数
 * 
 * @author Andy 2017-6-19 11:37:29
 */
@Service("initEmployeeInfo")
public class InitEmployeeInfo {

	@Autowired
	private TaskDistributeCore taskDistributeCore;
	@Autowired
	private EmployeeQueueCore employeeQueueCore;

	
	/**
	 * 定时任务调用方法
	 */
	public void executor() {
		// 暂停任务派发定时任务
		QuartzTaskApi.pauseJob();

		// 删除员工队列是否为空标识
		employeeQueueCore.deleteEmployeeQueueIsEmpty();
		// 重新初始化接单员信息:队列,接单规则,接单奖励
		taskDistributeCore.initEmployeeParamToQueue(false,null);

		// 恢复任务派发定时任务
		QuartzTaskApi.resumeJob();
	}
}
