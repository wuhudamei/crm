package com.rocoinfo.enumeration;

/**
 * 描述：客户轨迹用户当前状态
 *
 * @author tony
 * @创建时间 2017-06-07 09:22
 */
@SuppressWarnings("all")
public enum CustomerStatus {

    /**
     * 新客户：待邀约
     * 沟通中：邀约中  -> 邀约成功  -> 进店
     * 订单： (已生单)    小定 -> 大定 -> 签约 -> 开工
     * 其它： 退单 无效 冻结
     */
    NEWCUSTOMER("待邀约"),TALKING("邀约中"),TALKSUCCESS("邀约成功"),
    INTOSHOP("进店"), ORDERSUCCESS("已生单"), BACKORDER("退单"),
    INVALID("无效"), FREEZE("冻结");

    CustomerStatus(String label) {
        this.label = label;
    }

    private String label;

    public String getLabel() {
        return label;
    }
}
