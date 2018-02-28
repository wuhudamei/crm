package com.rocoinfo.enumeration;

/**
 * 任务类型
 * @author Andy
 * @time 2017-6-14 11:10:00
 **/
public enum TaskTypeStatus {

    INVITATION("邀约"), REPEAT("重复"), ABNORMAL("异常"), OTHER("其他");

    TaskTypeStatus(String label) {
        this.label = label;
    }

    private String label;

    public String getLabel() {
        return label;
    }
}
