package com.rocoinfo.entity.customer;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rocoinfo.entity.IdEntity;
import com.rocoinfo.entity.employee.Employee;

/**
 * 描述：联系人
 *
 * @author tony
 * @创建时间 2017-06-08 18:37
 */
public class Contacts extends IdEntity {
    /**
     * 客户编号
     */
    private String customerNo;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 关系
     */
    private String relationship;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;

    /**
     * 创建人
     */
    private Employee createUser;

    
    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
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

	public Employee getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Employee createUser) {
		this.createUser = createUser;
	}
}
