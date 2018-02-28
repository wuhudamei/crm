package com.rocoinfo.entity.taskrules;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rocoinfo.entity.IdEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <dl>
 * <dd>Description: 美得你智装  任务自动派发规则实体</dd>
 * <dd>@date：2017/10/23  12:54</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
public class TaskAllowautoRules extends IdEntity{

    /**
     * 门店编号
     */
    private String storeCode;

    /**
     * 数据来源编号
     */
    private String dataSourceCode;

    /**
     * 推广渠道编号
     */
    private String promoteCode;

    /**
     * 创建人id
     */
    private String creatorId;

    /**
     * 创建人姓名
     */
    private String creatorName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;
    /**
     * 多个推广渠道
     */
    private List<String> promoteCodes;

    public List<TaskAllowautoRules> getInsertBatch() {
        ArrayList<TaskAllowautoRules> taskAllowautoRules = new ArrayList<>();
        if(promoteCodes!=null) {
            for (String pro : promoteCodes) {
                if (!"".equals(pro)) {
                    TaskAllowautoRules taskAllowautoRules1 = new TaskAllowautoRules();
                    taskAllowautoRules1.setStoreCode(storeCode);
                    taskAllowautoRules1.setDataSourceCode(dataSourceCode);
                    taskAllowautoRules1.setPromoteCode(pro);
                    taskAllowautoRules.add(taskAllowautoRules1);
                }
            }
        }
        return  taskAllowautoRules;

    }
    public List<String> getPromoteCodes() {
        return promoteCodes;
    }

    public void setPromoteCodes(List<String> promoteCodes) {
        this.promoteCodes = promoteCodes;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getDataSourceCode() {
        return dataSourceCode;
    }

    public void setDataSourceCode(String dataSourceCode) {
        this.dataSourceCode = dataSourceCode;
    }

    public String getPromoteCode() {
        return promoteCode;
    }

    public void setPromoteCode(String promoteCode) {
        this.promoteCode = promoteCode;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null){
            return false;
        }
        if (!(obj instanceof TaskAllowautoRules)){
            return false;
        }
        TaskAllowautoRules obj1 = (TaskAllowautoRules) obj;
        if(!obj1.getStoreCode().equals(storeCode)||
                !obj1.getDataSourceCode().equals(dataSourceCode)||
                !obj1.getPromoteCode().equals(promoteCode)){
            return false;
        }
        return true;
    }
}