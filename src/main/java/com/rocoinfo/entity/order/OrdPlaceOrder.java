package com.rocoinfo.entity.order;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 功能描述:订单下单表
 * @author phil
 * 2017年6月17日
 */
@SuppressWarnings("all")
public class OrdPlaceOrder {

    private String placeOrderId;
    private String orderId;
    private Integer isSighContract;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date reservedSighTime;

    private String executor;
    private String voucherNo;
    private String comboType;
    private String comboTypeId;
    private BigDecimal removingRepairFee;
    private BigDecimal remotingFee;
    private BigDecimal carryFee;
    private BigDecimal budgetAmount;
    private BigDecimal imprest;
    private Integer isImprest;
    private BigDecimal receivable;
    private BigDecimal amountPaid;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date paymentTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date placeOrderTime;

    private Integer placeOrderStatus;
    private String placeOrderUserId;
    private String placeOrderName;
    private String placeOrderMobile;
    private String placeOrderDepartment;
    private String stylistUserId;
    private String stylistName;
    private String stylistMobile;
    private String stylistDepartment;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date stylistAllotTime;

    /**
     * 预约装修时间 是个靠谱的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date renovationTime;
    /**
     * 预约量房时间 是个靠谱的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date planMeasureTime;
    private String courier;
    private String courierUserId;
    private String courierMobile;
    private Integer state;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    private String partTimes;
    private Integer isContractFile;

    public Date getRenovationTime() {
        return renovationTime;
    }

    public void setRenovationTime(Date renovationTime) {
        this.renovationTime = renovationTime;
    }

    public String getPlaceOrderId() {
        return placeOrderId;
    }

    public void setPlaceOrderId(String placeOrderId) {
        this.placeOrderId = placeOrderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getIsSighContract() {
        return isSighContract;
    }

    public void setIsSighContract(Integer isSighContract) {
        this.isSighContract = isSighContract;
    }

    public Date getReservedSighTime() {
        return reservedSighTime;
    }

    public void setReservedSighTime(Date reservedSighTime) {
        this.reservedSighTime = reservedSighTime;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getComboType() {
        return comboType;
    }

    public void setComboType(String comboType) {
        this.comboType = comboType;
    }

    public String getComboTypeId() {
        return comboTypeId;
    }

    public void setComboTypeId(String comboTypeId) {
        this.comboTypeId = comboTypeId;
    }

    public BigDecimal getRemovingRepairFee() {
        return removingRepairFee;
    }

    public void setRemovingRepairFee(BigDecimal removingRepairFee) {
        this.removingRepairFee = removingRepairFee;
    }

    public BigDecimal getRemotingFee() {
        return remotingFee;
    }

    public void setRemotingFee(BigDecimal remotingFee) {
        this.remotingFee = remotingFee;
    }

    public BigDecimal getCarryFee() {
        return carryFee;
    }

    public void setCarryFee(BigDecimal carryFee) {
        this.carryFee = carryFee;
    }

    public BigDecimal getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(BigDecimal budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public BigDecimal getImprest() {
        return imprest;
    }

    public void setImprest(BigDecimal imprest) {
        this.imprest = imprest;
    }

    public Integer getIsImprest() {
        return isImprest;
    }

    public void setIsImprest(Integer isImprest) {
        this.isImprest = isImprest;
    }

    public BigDecimal getReceivable() {
        return receivable;
    }

    public void setReceivable(BigDecimal receivable) {
        this.receivable = receivable;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getPlaceOrderTime() {
        return placeOrderTime;
    }

    public void setPlaceOrderTime(Date placeOrderTime) {
        this.placeOrderTime = placeOrderTime;
    }

    public Integer getPlaceOrderStatus() {
        return placeOrderStatus;
    }

    public void setPlaceOrderStatus(Integer placeOrderStatus) {
        this.placeOrderStatus = placeOrderStatus;
    }

    public String getPlaceOrderUserId() {
        return placeOrderUserId;
    }

    public void setPlaceOrderUserId(String placeOrderUserId) {
        this.placeOrderUserId = placeOrderUserId;
    }

    public String getPlaceOrderName() {
        return placeOrderName;
    }

    public void setPlaceOrderName(String placeOrderName) {
        this.placeOrderName = placeOrderName;
    }

    public String getPlaceOrderMobile() {
        return placeOrderMobile;
    }

    public void setPlaceOrderMobile(String placeOrderMobile) {
        this.placeOrderMobile = placeOrderMobile;
    }

    public String getPlaceOrderDepartment() {
        return placeOrderDepartment;
    }

    public void setPlaceOrderDepartment(String placeOrderDepartment) {
        this.placeOrderDepartment = placeOrderDepartment;
    }

    public String getStylistUserId() {
        return stylistUserId;
    }

    public void setStylistUserId(String stylistUserId) {
        this.stylistUserId = stylistUserId;
    }

    public String getStylistName() {
        return stylistName;
    }

    public void setStylistName(String stylistName) {
        this.stylistName = stylistName;
    }

    public String getStylistMobile() {
        return stylistMobile;
    }

    public void setStylistMobile(String stylistMobile) {
        this.stylistMobile = stylistMobile;
    }

    public String getStylistDepartment() {
        return stylistDepartment;
    }

    public void setStylistDepartment(String stylistDepartment) {
        this.stylistDepartment = stylistDepartment;
    }

    public Date getStylistAllotTime() {
        return stylistAllotTime;
    }

    public void setStylistAllotTime(Date stylistAllotTime) {
        this.stylistAllotTime = stylistAllotTime;
    }

    public Date getPlanMeasureTime() {
        return planMeasureTime;
    }

    public void setPlanMeasureTime(Date planMeasureTime) {
        this.planMeasureTime = planMeasureTime;
    }

    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    public String getCourierUserId() {
        return courierUserId;
    }

    public void setCourierUserId(String courierUserId) {
        this.courierUserId = courierUserId;
    }

    public String getCourierMobile() {
        return courierMobile;
    }

    public void setCourierMobile(String courierMobile) {
        this.courierMobile = courierMobile;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPartTimes() {
        return partTimes;
    }

    public void setPartTimes(String partTimes) {
        this.partTimes = partTimes;
    }

    public Integer getIsContractFile() {
        return isContractFile;
    }

    public void setIsContractFile(Integer isContractFile) {
        this.isContractFile = isContractFile;
    }
}

