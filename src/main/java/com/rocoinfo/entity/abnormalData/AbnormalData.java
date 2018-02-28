package com.rocoinfo.entity.abnormalData;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rocoinfo.entity.IdEntity;

import java.util.Date;

/**
 * <dl>
 * <dd>Description: 美得你crm 异常数据实体</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/7</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */

public class AbnormalData extends IdEntity {
    /**
     * 异常信息
     */
    private String abnormalContent;
    /**
     * 异常类型,(手机号无效,重复.)
     */
    private String abnormalTypeName;
    /**
     * 异常类型名称(手机号无效,重复.)
     */
    private String abnormalType;
    /**
     * 来源接口代码
     */
    private String sourceCode;
    /**
     * 来源接口名字
     */
    private String sourceName;
    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;
    /**
     * 状态(0-未处理,1-已处理)
     */
    private String status;
    /**
     * 备注
     */
    private String remark;

    public String getAbnormalTypeName() {
        return abnormalTypeName;
    }

    public void setAbnormalTypeName(String abnormalTypeName) {
        this.abnormalTypeName = abnormalTypeName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getAbnormalContent() {
        return abnormalContent;
    }

    public void setAbnormalContent(String abnormalContent) {
        this.abnormalContent = abnormalContent;
    }

    public String getAbnormalType() {
        return abnormalType;
    }

    public void setAbnormalType(String abnormalType) {
        this.abnormalType = abnormalType;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
