package com.rocoinfo.entity.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rocoinfo.entity.IdEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 描述：同步订单系统的订单数据
 *
 * @author tony
 * @创建时间 2017-07-17 15:11
 */
public class SyncOrderInfo extends IdEntity {
    /**客户编号*/
    private String customerNo;
    private String taskNo;
    private String orderNum;
    private String orderId;
    private Long houseId;

    /**大定是否完成 0：未完成 1：完成*/
    private String depositFinish;
    /**定金是否可以退 0:不可；1：可以*/
    private String depositAbleback;
    /**已交定金金额*/
    private BigDecimal depositAmount;
    /**门店编号*/
    private String storeCode;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String customerIdinord;

    /**
     * 交定金的时间，转为大定后不在覆盖更新该时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date depositTime;
    /**
     * 订单关闭时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderCloseTime;
    /**
     * 订单是否关闭，0否1是
     */
    private Boolean orderClosed;

    public Date getDepositTime() {
        return depositTime;
    }

    public void setDepositTime(Date depositTime) {
        this.depositTime = depositTime;
    }

    public Date getOrderCloseTime() {
        return orderCloseTime;
    }

    public void setOrderCloseTime(Date orderCloseTime) {
        this.orderCloseTime = orderCloseTime;
    }

    public Boolean getOrderClosed() {
        return orderClosed;
    }

    public void setOrderClosed(Boolean orderClosed) {
        this.orderClosed = orderClosed;
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

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public String getDepositFinish() {
        return depositFinish;
    }

    public void setDepositFinish(String depositFinish) {
        this.depositFinish = depositFinish;
    }

    public String getDepositAbleback() {
        return depositAbleback;
    }

    public void setDepositAbleback(String depositAbleback) {
        this.depositAbleback = depositAbleback;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCustomerIdinord() {
        return customerIdinord;
    }

    public void setCustomerIdinord(String customerIdinord) {
        this.customerIdinord = customerIdinord;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }
}
