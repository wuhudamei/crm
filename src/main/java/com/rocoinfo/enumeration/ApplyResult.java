package com.rocoinfo.enumeration;

/**
 * 描述：审批结果 主要用于无效客户申请 ，退单申请
 *
 * @author tony
 * @创建时间 2017-06-16 11:23
 */
@SuppressWarnings("all")
public enum ApplyResult {
    /**
     * 同意
     */
    AGREE("同意"),
    /**
     * 拒绝
     */
    REFUSE("拒绝"),
    /**
     * 转发
     */
    FORWARDING("转发");

    String label;

    ApplyResult(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
