package com.rocoinfo.entity.employee;

import com.rocoinfo.entity.IdEntity;

/**
 * <dl>
 * <dd>Description: 美得你crm 用户实体类</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/5</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */

public class Employee extends IdEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 用户编号
     */
    private String jobNum;
    /**
     * 部门码
     */
    private String depCode;
    /**
     * 集团码
     */
    private String orgCode;
    /**
     * 用户名字
     */
    private String empName;
    /**
     * 用户电话
     */
    private String mobile;
    /**
     * 用户是否自动接单   Y-是 N-否
     */
    private String autoOrder;
    /**
     * 用户的忙碌阈值
     */
    private String busyThreshold;
    /**
     * 门店编号
     */
    private String storeCode;
    /**
     * 门店名字
     */
    private String orgName;
    /**
     * 岗位
     */
    private String position;
    /**
     * 上级 编号
     */
    private String parentId;
    /**
     * 上级名字
     */
    private String parentName;
    /**
     * 状态  1 启用  0 禁用
     */
    private String status;
    
    
    /**
     * 订单流转系统中员工的uuid
     */
    private String empIdinord;
    

    /**
     * 排序
     */
    private Long sort;
    /**
     * 当日接单数
     */
    private long orderNum;



    public long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(long orderNum) {
        this.orderNum = orderNum;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAutoOrder() {
        return autoOrder;
    }

    public void setAutoOrder(String autoOrder) {
        this.autoOrder = autoOrder;
    }

    public String getBusyThreshold() {
        return busyThreshold;
    }

    public void setBusyThreshold(String busyThreshold) {
        this.busyThreshold = busyThreshold;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

	public String getEmpIdinord() {
		return empIdinord;
	}
	
	public String getDepCode() {
		return depCode;
	}

	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public void setEmpIdinord(String empIdinord) {
		this.empIdinord = empIdinord;
	}
    
}
