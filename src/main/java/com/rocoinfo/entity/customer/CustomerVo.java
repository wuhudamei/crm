package com.rocoinfo.entity.customer;


/**
 * 客户表的包装类  用于多表查询,展示客户列表信息
 * @author Paul
 * 2017年6月12日
 */
public class CustomerVo extends Customer{
	
	//任务级别:A/B
	private String taskLevel;
	//客户状态 1:新客户 2:沟通中 3:小定 4:大定 5:签约 6:施工 7:老客户 8:无效
    private String currentStatus;
    
	public String getTaskLevel() {
		return taskLevel;
	}
	public void setTaskLevel(String taskLevel) {
		this.taskLevel = taskLevel;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
}
