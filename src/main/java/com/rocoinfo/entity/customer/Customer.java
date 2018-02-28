package com.rocoinfo.entity.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rocoinfo.entity.IdEntity;
import com.rocoinfo.entity.task.TaskDistribute;

import java.util.Date;

/**
 * 描述：客户表
 *
 * @author tony
 * @创建时间 2017-06-07 09:42
 */
@SuppressWarnings("all")
public class Customer extends IdEntity{

    /**
     * 客户编号
     */
    private String customerNo;
    
    //客户在订单系统中的id(与客户编号一一对应),去订单系统中查询时  需要使用该字段,代替客户编号
    private String custIdInOrder;

    /**
     * 客户姓名
     */
    private String customerName;

    /**
     * 客户标签
     */
    private String customerTag;
    /**
     * 客户标签名字
     */
    private String customerTagName;

    /**
     * 客户手机号
     */
    private String customerMobile;

    /**
     * 家庭固话
     */
    private String homePhone;

    /**
     * 备用电话
     */
    private String reserveMobile;

    /**
     * 客户在订单流转系统中的客户id
     */
    private String customerIdinord;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date updateTime;

    /**
     * 客户轨迹信息
     */
    private CustomerTaskRmk customerTaskRmk;

    /**
     * 客户沟通记录
     */
    private Communicate communicate;

    /**
     * 任务派发
     */
    private TaskDistribute taskDistribute;

    public String getCustomerTagName() {
        return customerTagName;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerTag() {
        return customerTag;
    }

    public void setCustomerTag(String customerTag) {
        this.customerTag = customerTag;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getReserveMobile() {
        return reserveMobile;
    }

    public void setReserveMobile(String reserveMobile) {
        this.reserveMobile = reserveMobile;
    }

    public String getCustomerIdinord() {
        return customerIdinord;
    }

    public void setCustomerIdinord(String customerIdinord) {
        this.customerIdinord = customerIdinord;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public CustomerTaskRmk getCustomerTaskRmk() {
        return customerTaskRmk;
    }

    public void setCustomerTaskRmk(CustomerTaskRmk customerTaskRmk) {
        this.customerTaskRmk = customerTaskRmk;
    }

    public Communicate getCommunicate() {
        return communicate;
    }

    public void setCommunicate(Communicate communicate) {
        this.communicate = communicate;
    }

	public String getCustIdInOrder() {
		return custIdInOrder;
	}

	public void setCustIdInOrder(String custIdInOrder) {
		this.custIdInOrder = custIdInOrder;
	}

    public TaskDistribute getTaskDistribute() {
        return taskDistribute;
    }

    public void setTaskDistribute(TaskDistribute taskDistribute) {
        this.taskDistribute = taskDistribute;
    }
}
