package com.rocoinfo.entity.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rocoinfo.entity.IdEntity;

import java.util.Date;

/**
 * <dl>
 * <dd>Description: 用户奖励单 实体</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-6-15 15:00:24</dd>
 * <dd>@author：Andy</dd>
 * </dl>
 */

public class EmployeeOrderReward extends IdEntity {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 员工编号
     */
    private String jobNo;
    /**
     * 奖励单数
     */
    private Integer rewardOrderNum;
    /**
     * 实际奖励单数
     */
    private Integer actualNum;
    
    /**
     * 奖励时间
     */
    private String rewardDate;

    /**
     * 创建日期
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+08:00")
    private Date createTime;
    
    /**
     * 创建人
     */
    private Long createUser;
    

	public String getJobNo() {
		return jobNo;
	}

	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}

	public Integer getRewardOrderNum() {
		return rewardOrderNum;
	}

	public void setRewardOrderNum(Integer rewardOrderNum) {
		this.rewardOrderNum = rewardOrderNum;
	}

	public Integer getActualNum() {
		return actualNum;
	}

	public void setActualNum(Integer actualNum) {
		this.actualNum = actualNum;
	}

	public String getRewardDate() {
		return rewardDate;
	}

	public void setRewardDate(String rewardDate) {
		this.rewardDate = rewardDate;
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
