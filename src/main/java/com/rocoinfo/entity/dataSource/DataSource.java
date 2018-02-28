package com.rocoinfo.entity.dataSource;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rocoinfo.entity.IdEntity;

import java.util.Date;

/**
 * <dl>
 * <dd>Description: 美得你crm 来源管理的 实体</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/7</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */

public class DataSource extends IdEntity {
    /**
     * 来源名称
     */
    private String sourceName;
    /**
     * 来源编码
     */
    private String sourceCode;
    /**
     * 自动派发(Y-是,N-否)
     */
    private String autoDistribute;
    /**
     * 状态(0-禁用,1-启用)
     */
    private String status;
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
     * 备注
     */
    private String remark;
    
    
    /**
     * 调用者id
     */
    private String callId;
    
    
    /**
     * 调用者密钥
     */
    private String callSecret;

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getAutoDistribute() {
        return autoDistribute;
    }

    public void setAutoDistribute(String autoDistribute) {
        this.autoDistribute = autoDistribute;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getCallId() {
		return callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public String getCallSecret() {
		return callSecret;
	}

	public void setCallSecret(String callSecret) {
		this.callSecret = callSecret;
	}
    
}
