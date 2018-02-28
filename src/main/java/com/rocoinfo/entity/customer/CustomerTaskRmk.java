package com.rocoinfo.entity.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rocoinfo.entity.IdEntity;

import java.util.Date;

/**
 * 描述：客户轨迹信息
 *
 * @author tony
 * @date 2017-06-07 10:09
 */
@SuppressWarnings("all")
public class CustomerTaskRmk extends IdEntity {

    /**
     * 客户编号
     */
    private String customerNo;

    /**
     * 任务Id
     */
    private String taskNo;

    /**
     * 是否最新 0-否 1-是 主要是用于针对该任务的状态是否是最新的一条，以免还得根据时间先过滤过最近的一条
     */
    private String newFlag;

    /**
     * 当前状态 @link(com.rocoinfo.enumeration.CustomerStatus)
     */
    private String currentStatus;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;

    /**
     * 插入这个条记录的时候的跟单员
     */
    private String mechandiser;

    /**
     * 创建人
     */
    private Long createUser;


    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getNewFlag() {
        return newFlag;
    }

    public void setNewFlag(String newFlag) {
        this.newFlag = newFlag;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMechandiser() {
        return mechandiser;
    }

    public void setMechandiser(String mechandiser) {
        this.mechandiser = mechandiser;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }
}
