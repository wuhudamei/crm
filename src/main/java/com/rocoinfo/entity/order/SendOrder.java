package com.rocoinfo.entity.order;

import com.rocoinfo.entity.customer.Customer;

/**
 * 功能描述:订单传输对象
 * @author phil
 * 2017年6月18日
 */
@SuppressWarnings("all")
public class SendOrder {

    private Order order;

    private OrdPlaceOrder placeOrder;

    private OrdRemark remark;

    private Customer customer;

    public SendOrder(Order order, OrdPlaceOrder placeOrder, OrdRemark remark, Customer customer) {
        this.order = order;
        this.placeOrder = placeOrder;
        this.remark = remark;
        this.customer = customer;
    }

    public SendOrder(Order order, OrdPlaceOrder placeOrder, OrdRemark remark) {
        this.order = order;
        this.placeOrder = placeOrder;
        this.remark = remark;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

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
}
