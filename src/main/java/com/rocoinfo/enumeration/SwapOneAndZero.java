package com.rocoinfo.enumeration;

/**
 * 描述：0 和 1 常量
 *
 * @author tony
 * @创建时间 2017-06-14 15:20
 */
@SuppressWarnings("all")
public enum SwapOneAndZero {
    /**
     * 0 无、假的、无效、人工
     */
    ZERO(0, "0"),
    /**
     * 1 有、真的、有效、系统
     */
    ONE(1, "1");

    int index;
    String label;

    SwapOneAndZero(int index, String label) {
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
