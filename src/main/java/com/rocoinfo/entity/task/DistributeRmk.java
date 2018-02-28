package com.rocoinfo.entity.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rocoinfo.entity.IdEntity;

import java.util.Date;

/**
 * <dl>
 * <dd>Description: 美得你crm 任务派发轨迹</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/15</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */

public class DistributeRmk  extends IdEntity {
    /**
     * 任务编号
     */
    private String taskNo;

    /**
     * 原跟单员
     */
    private String oldMechandiser;

    /**
     * 转派跟单员
     */
    private String distributeMechandiser;

    /**
     * 类型(邀约,接待,签单)
     */
    private String type;

    /**
     * 转派时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;

    /**
     * 操作人
     */
    private Long createUser;

    private static final long serialVersionUID = 1L;



    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getOldMechandiser() {
        return oldMechandiser;
    }

    public void setOldMechandiser(String oldMechandiser) {
        this.oldMechandiser = oldMechandiser;
    }

    public String getDistributeMechandiser() {
        return distributeMechandiser;
    }

    public void setDistributeMechandiser(String distributeMechandiser) {
        this.distributeMechandiser = distributeMechandiser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
