package com.rocoinfo.entity.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rocoinfo.entity.IdEntity;

import java.util.Date;

/**
 * 描述：退单申请
 *
 * @author tony
 * @创建时间 2017-06-09 17:28
 */
@SuppressWarnings("all")
public class ReturnOrder extends IdEntity{

  /**
   * 订单编号
   */
  private String orderId;

  /**
   * 任务编号
   */
  private String taskNo;

  /**
   * 退单原因
   */
  private String backReason;

  /**
   * 退单说明
   */
  private String backRemark;

  /**
   * 扣款金额
   */
  private double debitAmount;

  /**
   * 实退金额
   */
  private double amount;

  /**
   * 门店
   */
  private String store;

  /**
   * 审批结果
   */
  private String approvalResult;

  /**
   * 申请编号
   */
  private String applyNo;

  /**
   * 申请人
   */
  private String applyUser;

  /**
   * 申请人名称
   */
  private String applyUserName;

  /**
   * 审批人
   */
  private String approvalUser;

  /**
   * 申请时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
  private Date applyTime;

  /**
   * 审批时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
  private Date approvalTime;

  /**
   * 申请标题
   */
  private String applyTitle;

  /**
   * 客户姓名
   */
  private String customerName;

  /**
   * 客户手机号
   */
  private String customerMobile;

  // 显示用的地址
  private String provinceName;
  private String cityName;
  private String areaName;
  private String address;

  /**
   * 是否小定
   */
  private int allotState;

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getTaskNo() {
    return taskNo;
  }

  public void setTaskNo(String taskNo) {
    this.taskNo = taskNo;
  }

  public String getBackReason() {
    return backReason;
  }

  public void setBackReason(String backReason) {
    this.backReason = backReason;
  }

  public String getBackRemark() {
    return backRemark;
  }

  public void setBackRemark(String backRemark) {
    this.backRemark = backRemark;
  }

  public double getDebitAmount() {
    return debitAmount;
  }

  public void setDebitAmount(double debitAmount) {
    this.debitAmount = debitAmount;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public String getStore() {
    return store;
  }

  public void setStore(String store) {
    this.store = store;
  }

  public String getApprovalResult() {
    return approvalResult;
  }

  public void setApprovalResult(String approvalResult) {
    this.approvalResult = approvalResult;
  }

  public String getApplyNo() {
    return applyNo;
  }

  public void setApplyNo(String applyNo) {
    this.applyNo = applyNo;
  }

  public String getApplyUser() {
    return applyUser;
  }

  public void setApplyUser(String applyUser) {
    this.applyUser = applyUser;
  }

  public String getApplyUserName() {
    return applyUserName;
  }

  public void setApplyUserName(String applyUserName) {
    this.applyUserName = applyUserName;
  }

  public String getApprovalUser() {
    return approvalUser;
  }

  public void setApprovalUser(String approvalUser) {
    this.approvalUser = approvalUser;
  }

  public Date getApplyTime() {
    return applyTime;
  }

  public void setApplyTime(Date applyTime) {
    this.applyTime = applyTime;
  }

  public Date getApprovalTime() {
    return approvalTime;
  }

  public void setApprovalTime(Date approvalTime) {
    this.approvalTime = approvalTime;
  }

  public String getApplyTitle() {
    return applyTitle;
  }

  public void setApplyTitle(String applyTitle) {
    this.applyTitle = applyTitle;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getCustomerMobile() {
    return customerMobile;
  }

  public void setCustomerMobile(String customerMobile) {
    this.customerMobile = customerMobile;
  }

  public String getProvinceName() {
    return provinceName;
  }

  public void setProvinceName(String provinceName) {
    this.provinceName = provinceName;
  }

  public String getCityName() {
    return cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  public String getAreaName() {
    return areaName;
  }

  public void setAreaName(String areaName) {
    this.areaName = areaName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getAllotState() {
    return allotState;
  }

  public void setAllotState(int allotState) {
    this.allotState = allotState;
  }
}
