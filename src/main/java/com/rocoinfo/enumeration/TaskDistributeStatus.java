package com.rocoinfo.enumeration;

/**
 * 描述：任务派发 任务状态
 *
 * @author tony
 * @创建时间 2017-06-14 14:13
 */
@SuppressWarnings("all")
public enum TaskDistributeStatus {
    INVALID(0, "无效"),VALID(1, "有效"),FREEZE(2, "冻结");
    int index;
    String lable;
    TaskDistributeStatus(int index, String label) {
        this.index = index;
        this.lable = label;
    }

    public int getIndex() {
        return index;
    }

    public String getLable() {
        return lable;
    }
}
