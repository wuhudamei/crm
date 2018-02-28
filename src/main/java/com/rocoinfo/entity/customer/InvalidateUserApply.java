package com.rocoinfo.entity.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rocoinfo.entity.IdEntity;

import java.util.Date;

/**
 * 描述：无效客户申请
 *
 * @author tony
 * @创建时间 2017-06-09 17:28
 */
@SuppressWarnings("all")
public class InvalidateUserApply extends IdEntity{

  /**
   * 申请编号
   */
  private String applyNo;

  /**
   * 申请标题
   */
  private String applyTitle;

  /**
   * 任务编号
   */
  private String taskNo;

  /**
   * 客户编号
   */
  private String customerNo;

  /**
   * 申请原因
   */
  private String applyReson;

  /**
   * 申请人
   */
  private String applyUser;
  private String applyUserName;

  /**
   * 审批人
   */
  private String approvalUser;

  /**
   * 审批结果
   */
  private String approvalResult;

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
   * 门店
   */
  private String store;

  /**
   * 来源
   */
  private String dataSource;

  /**
   * 客户姓名
   */
  private String customerName;
  /**
   * 客户手机号
   */
  private String customerMobile;

  public String getApplyNo() {
    return applyNo;
  }

  public void setApplyNo(String applyNo) {
    this.applyNo = applyNo;
  }

  public String getApplyTitle() {
    return applyTitle;
  }

  public void setApplyTitle(String applyTitle) {
    this.applyTitle = applyTitle;
  }

  public String getTaskNo() {
    return taskNo;
  }

  public void setTaskNo(String taskNo) {
    this.taskNo = taskNo;
  }

  public String getCustomerNo() {
    return customerNo;
  }

  public void setCustomerNo(String customerNo) {
    this.customerNo = customerNo;
  }

  public String getApplyReson() {
    return applyReson;
  }

  public void setApplyReson(String applyReson) {
    this.applyReson = applyReson;
  }

  public String getApplyUser() {
    return applyUser;
  }

  public void setApplyUser(String applyUser) {
    this.applyUser = applyUser;
  }

  public String getApprovalUser() {
    return approvalUser;
  }

  public void setApprovalUser(String approvalUser) {
    this.approvalUser = approvalUser;
  }

  public String getApprovalResult() {
    return approvalResult;
  }

  public void setApprovalResult(String approvalResult) {
    this.approvalResult = approvalResult;
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

  public String getStore() {
    return store;
  }

  public void setStore(String store) {
    this.store = store;
  }

  public String getDataSource() {
    return dataSource;
  }

  public void setDataSource(String dataSource) {
    this.dataSource = dataSource;
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

  public String getApplyUserName() {
    return applyUserName;
  }

  public void setApplyUserName(String applyUserName) {
    this.applyUserName = applyUserName;
  }
}
