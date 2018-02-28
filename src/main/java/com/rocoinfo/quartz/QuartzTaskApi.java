package com.rocoinfo.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;

import com.rocoinfo.Constants;
import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.service.core.TaskDistributeCore;
import com.rocoinfo.utils.DateUtils;

import ch.qos.logback.classic.Logger;

/**
 * <dl>
 * <dd>Description: 定时任务</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-6-17 19:06:32</dd>
 * <dd>@author：Andy</dd>
 * </dl>
 */
@Service
public class QuartzTaskApi {

	/**
	 * 创建 scheduler对象
	 */
	private static final SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();

	/** 定时任务名称前缀 **/
	private static final String JOBDETAIL_NAME = "JOBDETAIL";
	/** 定时任务组名称前缀 **/
	private static final String JOBDETAIL_GROUP_NAME = "JOBDETAIL_GROUP";
	/** 触发器名称前缀 **/
	private static final String TRIGGER_NAME = "TRIGGER";
	/** 触发器组名称前缀 **/
	private static final String TRIGGER_GROUP = "TRIGGER_GROUP";
	
	/** 定时任务执行的结束时间 **/
	private static final String END_TIME = "2099-12-31 23:59:59";

	/**
	 * 创建quartz定时任务,任务暂停后再次回复忽略暂停期间未执行的次数
	 * 
	 * @param jobClass
	 *            需要执行任务的类(需要自己实现)
	 * @param jobName
	 *            创建的定时任务名称(自定义任何字符串)
	 * @param startTime
	 *            开始时间(非必须)
	 * @param endTime
	 *            结束时间(非必须)
	 * @param executeSecond
	 *            间隔多少秒执行一次
	 * @throws Exception
	 */
	public static void createQuartz(TaskDistributeCore taskDistributeCore) throws Exception {
		Scheduler scheduler = null;
		// 通过schedulerFactory获取一个调度器
		scheduler = gSchedulerFactory.getScheduler();
		JobDetail job = JobBuilder.newJob(QuartzJob.class).withIdentity(JOBDETAIL_NAME, JOBDETAIL_GROUP_NAME)
				.build();
		job.getJobDataMap().put(Constants.TASK_SERVICE_NAME, taskDistributeCore);
		SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity(TRIGGER_NAME, TRIGGER_GROUP)
				.endAt(DateUtils.parse(END_TIME, "yyyy-MM-dd HH:mm:ss"))
				.withSchedule(SimpleScheduleBuilder.simpleSchedule()
						.withIntervalInSeconds(PropertyHolder.getDistributeSeconds()).repeatForever()
						.withMisfireHandlingInstructionNextWithRemainingCount())
				.build();

		// 把作业和触发器注册到任务调度中
		scheduler.scheduleJob(job, trigger);
		// 启动调度
		scheduler.start();
	}

	/**
	 * 查询所有任务组,触发器组
	 */
	public static void getAllJob() {
		Scheduler scheduler = null;
		try {
			scheduler = gSchedulerFactory.getScheduler();
			scheduler.getJobGroupNames();
			scheduler.getTriggerGroupNames();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 暂停定时任务
	 */
	public static void pauseJob() {
		Scheduler scheduler = null;
		try {
			scheduler = gSchedulerFactory.getScheduler();
			JobKey jobKey = JobKey.jobKey(JOBDETAIL_NAME, JOBDETAIL_GROUP_NAME);
			scheduler.pauseJob(jobKey);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 恢复定时任务
	 */
	public static void resumeJob() {
		Scheduler scheduler = null;
		try {
			scheduler = gSchedulerFactory.getScheduler();
			JobKey jobKey = JobKey.jobKey(JOBDETAIL_NAME, JOBDETAIL_GROUP_NAME);
			scheduler.resumeJob(jobKey);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 暂停所有任务
	 */
	public static void pauseAllJob() {
		Scheduler scheduler = null;
		try {
			scheduler = gSchedulerFactory.getScheduler();
			scheduler.pauseAll();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 恢复所有任务
	 */
	public static void resumeAllJob() {
		Scheduler scheduler = null;
		try {
			scheduler = gSchedulerFactory.getScheduler();
			scheduler.resumeAll();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 删除定时任务
	 */
	@Deprecated
	public static void removeJob() {
		Scheduler scheduler = null;
		try {
			scheduler = gSchedulerFactory.getScheduler();
			JobKey jobKey = JobKey.jobKey(JOBDETAIL_NAME, JOBDETAIL_GROUP_NAME);
			scheduler.pauseJob(jobKey);
			scheduler.deleteJob(jobKey);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 立即执行(只在服务启动后调用一次,之后便按正常执行)
	 */
	public static void nowStart() {
		Scheduler scheduler = null;
		try {
			scheduler = gSchedulerFactory.getScheduler();
			JobKey jobKey = JobKey.jobKey(JOBDETAIL_NAME, JOBDETAIL_GROUP_NAME);
			scheduler.triggerJob(jobKey);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 启动所有定时任务
	 */
	public static void startJobs() {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			sched.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 关闭所有定时任务
	 */
	public static void shutdownJobs() {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			if (!sched.isShutdown()) {
				sched.shutdown();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
