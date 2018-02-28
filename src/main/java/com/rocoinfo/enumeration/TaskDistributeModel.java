package com.rocoinfo.enumeration;

/**
 * 任务派发方式
 * @author Andy
 * @time 2017-6-14 11:10:00
 **/
public enum TaskDistributeModel {

    HAND("人工"), SYSTEM("系统");

    TaskDistributeModel(String label) {
        this.label = label;
    }

    private String label;

    public String getLabel() {
        return label;
    }
}
