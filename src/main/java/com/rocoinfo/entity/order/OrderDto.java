package com.rocoinfo.entity.order;

/**
 * 功能描述:订单传输对象
 * @author phil
 * 2017年6月18日
 */
@SuppressWarnings("all")
public class OrderDto {

    private Order order;

    private OrdPlaceOrder placeOrder;

    private OrdRemark remark;

    /**
     * 客户编号
     */
    private String customerNo;

    /**
     * 任务编号
     */
    private String taskNo;

    /**
     * 房屋ID
     */
    private long houseId;


    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrdPlaceOrder getPlaceOrder() {
        return placeOrder;
    }

    public void setPlaceOrder(OrdPlaceOrder placeOrder) {
        this.placeOrder = placeOrder;
    }

    public OrdRemark getRemark() {
        return remark;
    }

    public void setRemark(OrdRemark remark) {
        this.remark = remark;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public long getHouseId() {
        return houseId;
    }

    public void setHouseId(long houseId) {
        this.houseId = houseId;
    }
}
