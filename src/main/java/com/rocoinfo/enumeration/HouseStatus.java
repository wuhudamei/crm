package com.rocoinfo.enumeration;

/**
 * 描述：房屋状态
 *
 * @author tony
 * @创建时间 2017-06-28 17:02
 */
@SuppressWarnings("all")
public enum HouseStatus {
    /**
     * 退单通过
     */
    APPLYAGREE(0, "退单通过"),
    /**
     * 正常订单/房屋
     */
    NORMAL(1, "正常"),
    /**
     * 退单审批中
     */
    APPLYING(2, "审批中");

    int index;
    String label;

    HouseStatus(int index, String label) {
        this.index = index;
        this.label = label;
    }
    public int getIndex() {
        return index;
    }

    public String getLabel() {
        return label;
    }
}
