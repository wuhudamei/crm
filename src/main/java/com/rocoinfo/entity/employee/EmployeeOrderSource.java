package com.rocoinfo.entity.employee;

import com.rocoinfo.entity.IdEntity;

import java.util.Date;

/**
 * <dl>
 * <dd>Description: 美得你crm 用户接单来源</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/13</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */

public class EmployeeOrderSource extends IdEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 员工编号
     */
    private String jobNum;
    /**
     * 接单来源(新媒体,自然进店,呼叫中心,市场部)编号
     */
    private String orderSource;
    /**
     * 接单来源(新媒体,自然进店,呼叫中心,市场部)名字
     */
    private String orderSourceName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人 id
     */
    private Long createUser;

    /**
     * 是否选中
     */
    private Boolean checked;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 所属人的门店code
     */
    private String storeCode;
    /**
     * 用户是否自动接单   Y-是 N-否
     */
    private String autoOrder;
    /**
     * 用户的忙碌阈值
     */
    private String busyThreshold;

    public String getAutoOrder() {
        return autoOrder;
    }

    public void setAotuOrder(String autoOrder) {
        this.autoOrder = autoOrder;
    }

    public String getBusyThreshold() {
        return busyThreshold;
    }

    public void setBusyThreshold(String busyThreshold) {
        this.busyThreshold = busyThreshold;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getJobNum() {
        return jobNum;
    }

    public String getOrderSourceName() {
        return orderSourceName;
    }

    public void setOrderSourceName(String orderSourceName) {
        this.orderSourceName = orderSourceName;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }
}
