package com.rocoinfo.entity.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rocoinfo.entity.IdEntity;

import java.util.Date;

/**
 * <dl>
 * <dd>Description: 美得你crm 日程实体</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/7</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@SuppressWarnings("all")
public class Schedule extends IdEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 任务编号
     */
    private String taskNo;

    /**
     * 日程的标题
     */
    private String title;
    /**
     * 日程内容
     */
    private String content;

    /**
     * INVITATIONSTORE("邀约到店"),ROOMRESERVATIONS("预约量房"),OTHER("其它");
     */
    private String scheduleType;

    /**
     * 日程执行时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+08:00")
    private Date scheduleTime;
    /**
     * 日程类型创建类型
     * 类型(人工,系统)
     */
    private String generateModel;
    /**
     * 日程创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;
    /**
     * 日程创建人  对应员工编号
     */
    private String jobNum;
    
    /**
     * 当日需要处理的日程数量(只针对发送模板消息统计使用)
     */
    private Integer scheduleNum;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public Date getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(Date scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public String getGenerateModel() {
        return generateModel;
    }

    public void setGenerateModel(String generateModel) {
        this.generateModel = generateModel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

	public Integer getScheduleNum() {
		return scheduleNum;
	}

	public void setScheduleNum(Integer scheduleNum) {
		this.scheduleNum = scheduleNum;
	}
    
}
