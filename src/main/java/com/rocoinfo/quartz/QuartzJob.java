package com.rocoinfo.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.rocoinfo.Constants;
import com.rocoinfo.service.core.TaskDistributeCore;

/**
 * 定时任务
 * 2017-6-17 20:55:24
 * @author Andy
 */
public class QuartzJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		TaskDistributeCore taskDistributeCore = (TaskDistributeCore) dataMap.get(Constants.TASK_SERVICE_NAME);
		taskDistributeCore.execute();
	}
	
}
