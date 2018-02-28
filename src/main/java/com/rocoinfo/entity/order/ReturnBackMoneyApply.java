package com.rocoinfo.entity.order;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 功能描述:退款申请实体类
 *
 * @author phil
 *         2017年7月7日
 */
@SuppressWarnings("all")
public class ReturnBackMoneyApply {

    /**
     * 申请记录id
     */
    private int applyId;

    /**
     * 申请人id
     */
    private String applyerId;

    /**
     * 申请人姓名
     */
    private String applyerName;

    /**
     * 申请人类型：KF(客服)、SJS(设计师)
     */
    private String applyerType;

    /**
     * 审核人姓名
     */
    private String checkerName;

    /**
     * 退款类型：1(退单退款)，2(特殊退款)
     */
    private String returnBackType;

    /**
     * 退单原因
     */
    private String cancelOrderReson;

    /**
     * 退款申请生成时间
     */
    private Date createTime;

    /**
     * 要退的订单单号
     */
    private String orderNo;

    /**
     * 要退的订单uuid
     */
    private String orderId;

    /**
     * 应扣除的金额
     */
    private BigDecimal deductMoneyAmount;

    /**
     * 应退的金额
     */
    private BigDecimal returnMoneyAmount;

    /**
     * 退款执行状态
     */
    private String executeStatus;

    /**
     * 辅助变量，在数据库表里没有对应字段
     */
    //客户在订单系统里的Id
    private String customerIdInOrd;

    public int getApplyId() {
        return applyId;
    }


    public void setApplyId(int applyId) {
        this.applyId = applyId;
    }


    public String getApplyerId() {
        return applyerId;
    }


    public void setApplyerId(String applyerId) {
        this.applyerId = applyerId;
    }


    public String getApplyerName() {
        return applyerName;
    }


    public void setApplyerName(String applyerName) {
        this.applyerName = applyerName;
    }


    public String getApplyerType() {
        return applyerType;
    }


    public void setApplyerType(String applyerType) {
        this.applyerType = applyerType;
    }


    public String getCheckerName() {
        return checkerName;
    }


    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }


    public String getReturnBackType() {
        return returnBackType;
    }


    public void setReturnBackType(String returnBackType) {
        this.returnBackType = returnBackType;
    }


    public String getCancelOrderReson() {
        return cancelOrderReson;
    }


    public void setCancelOrderReson(String cancelOrderReson) {
        this.cancelOrderReson = cancelOrderReson;
    }


    public Date getCreateTime() {
        return createTime;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public String getOrderNo() {
        return orderNo;
    }


    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }


    public BigDecimal getDeductMoneyAmount() {
        return deductMoneyAmount;
    }


    public void setDeductMoneyAmount(BigDecimal deductMoneyAmount) {
        this.deductMoneyAmount = deductMoneyAmount;
    }


    public BigDecimal getReturnMoneyAmount() {
        return returnMoneyAmount;
    }


    public void setReturnMoneyAmount(BigDecimal returnMoneyAmount) {
        this.returnMoneyAmount = returnMoneyAmount;
    }


    public String getCustomerIdInOrd() {
        return customerIdInOrd;
    }


    public void setCustomerIdInOrd(String customerIdInOrd) {
        this.customerIdInOrd = customerIdInOrd;
    }


    public String getExecuteStatus() {
        return executeStatus;
    }


    public void setExecuteStatus(String executeStatus) {
        this.executeStatus = executeStatus;
    }


    public String getOrderId() {
        return orderId;
    }


    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
