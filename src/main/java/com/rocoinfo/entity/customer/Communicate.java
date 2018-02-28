package com.rocoinfo.entity.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rocoinfo.entity.IdEntity;

import java.util.Date;

/**
 * 描述：沟通记录
 *
 * @author tony
 * @date 2017-06-07 10:43
 */
@SuppressWarnings("all")
public class Communicate extends IdEntity{

    /**
     * 客户编号
     */
    private String customerNo;

    /**
     * 任务编号
     */
    private String taskNo;

    /**
     * 沟通方式
     */
    private String communicateMode;

    /**
     * 沟通类型
     */
    private String communicateType;

    /**
     * 是否无效客户 0-无效 1-有效
     */
    private String invalidFlag;

    /**
     * 邀约到店
     */
    private String invitationStore;

    /**
     * 到店时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH", timezone = "GMT+08:00")
    private Date storeTime;

    /**
     * 量房时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+08:00")
    private Date amontRoomTime;

    /**
     * 客户意向
     */
    private String customerIntention;

    /**
     * 客户标签
     */
    private String customerTag;

    /**
     * 沟通内容
     */
    private String remark;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;

    /**
     * 当前任务的最新状态
     */
    private String currentStatus;

    /**
     * 创建人
     */
    private Integer createUser;
    
    /**
     * 创建人姓名(用于数据封装)
     */
    private String createUserName;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单编号
     */
    private String orderNum;

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

    public String getCommunicateMode() {
        return communicateMode;
    }

    public void setCommunicateMode(String communicateMode) {
        this.communicateMode = communicateMode;
    }

    public String getCommunicateType() {
        return communicateType;
    }

    public void setCommunicateType(String communicateType) {
        this.communicateType = communicateType;
    }

    public String getInvalidFlag() {
        return invalidFlag;
    }

    public void setInvalidFlag(String invalidFlag) {
        this.invalidFlag = invalidFlag;
    }

    public String getInvitationStore() {
        return invitationStore;
    }

    public void setInvitationStore(String invitationStore) {
        this.invitationStore = invitationStore;
    }

    public Date getStoreTime() {
        return storeTime;
    }

    public void setStoreTime(Date storeTime) {
        this.storeTime = storeTime;
    }

    public Date getAmontRoomTime() {
        return amontRoomTime;
    }

    public void setAmontRoomTime(Date amontRoomTime) {
        this.amontRoomTime = amontRoomTime;
    }

    public String getCustomerIntention() {
        return customerIntention;
    }

    public void setCustomerIntention(String customerIntention) {
        this.customerIntention = customerIntention;
    }

    public String getCustomerTag() {
        return customerTag;
    }

    public void setCustomerTag(String customerTag) {
        this.customerTag = customerTag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
}
