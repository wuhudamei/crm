package com.rocoinfo.entity.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rocoinfo.entity.IdEntity;
import com.rocoinfo.entity.customer.Communicate;
import com.rocoinfo.entity.order.SyncOrderInfo;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 描述：任务派发
 *
 * @author tony
 * @创建时间 2017-06-09 16:33
 */
@SuppressWarnings("all")
public class TaskDistribute extends IdEntity {

    /**
     * 任务编号32位UUID
     */
    private String taskNo;

    /**
     * 客户编号
     */
    private String customerNo;

    /**
     * 邀约码
     */
    private String invitationCode;

    /**
     * 来电时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date callTime;


    /**
     * 是否接通了电话：1=接通，0=未接
     */
    private String callAnswered;


    /**
     * 跟单员
     */
    private String mechandiser;
    /**
     * 跟单员名字
     */
    private String empName;

    /**
     * 门店
     */
    private String store;

    /**
     * 数据来源
     */
    private String dataSource;

    /**
     * 数据名字
     */
    private String dataSourceName;

    /**
     * 推广来源
     */
    private String promoteSource;
    /**
     * 任务类型(邀约,重复,异常,其他)
     */
    private String type;

    /**
     * 任务标签
     */
    private String taskTag;

    /**
     * 任务级别(A,B,C,D,E,F,)
     */
    private String taskLevel;

    /**
     * 派发状态(N-未派发-Y-已派发)
     */
    private String distributeStatus;

    /**
     * 任务状态(0-无效,1-有效)
     */
    private String status;

    /**
     * 派发方式(人工,系统)
     */
    private String distributeModel;

    /**
     * 派发时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date distributeTime;

    /**
     * 创建人Id
     */
    private String creator;


    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;
    /**
     * 客户姓名
     */
    private String customerName;
    /**
     * 客户电话
     */
    private String customerMobile;
    /**
     * 门店名字
     */
    private String storeName;
    /**
     * 介绍人
     */
    private String introducer;
    /**
     * 介绍人电话
     */
    private String introducerTel;

    /**
     * 介绍人Id
     */
    private String introducerId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否是可以 进店状态
     */
    private Boolean isShop;

    /**
     * 最后一次沟通记录
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date communicateTime;

    //固定电话
    private String homePhone;
    //备用电话
    private String reserveMobile;

    /**
     * 客户当前状态--来自客户任轨迹 当前状态
     */
    private String currentStatus;


    //推广来源名称
    private String promoteSourceName;
    /**
     * 报表所用 沟通记录
     */
    private List<Communicate> communicateList;
    /**
     * 报表所用 任务同步信息
     */
    private List<SyncOrderInfo> syncOrderInfoList;

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public List<Communicate> getCommunicateList() {
        return communicateList;
    }

    public void setCommunicateList(List<Communicate> communicateList) {
        this.communicateList = communicateList;
    }

    public List<SyncOrderInfo> getSyncOrderInfoList() {
        return syncOrderInfoList;
    }

    public void setSyncOrderInfoList(List<SyncOrderInfo> syncOrderInfoList) {
        this.syncOrderInfoList = syncOrderInfoList;
    }

    /**
     * （报表用） 邀约了
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public Communicate getInvitation(Date startDate, Date endDate) {
        Communicate backFlg = null;
        if (communicateList != null && communicateList.size() > 0) {
            backFlg = communicateList.stream().
                    filter(c -> startDate.before(c.getCreateTime()) || startDate.getTime() == (c.getCreateTime().getTime())).
                    filter(d -> endDate.after(d.getCreateTime()) || endDate.getTime() == (d.getCreateTime().getTime())).findAny().orElse(null);
        }
        return backFlg;
    }

    /**
     * （报表用） 进店
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public Communicate getOrderShop(Date startDate, Date endDate) {
        Communicate backFlg = null;
        if (communicateList != null && communicateList.size() > 0) {
            backFlg = communicateList.stream().
                    filter(a -> "JD".equals(a.getCommunicateMode())).
                    filter(b -> "IN".equals(b.getCommunicateType())).
                    filter(c -> startDate.before(c.getCreateTime()) || startDate.getTime() == (c.getCreateTime().getTime())).
                    filter(d -> endDate.after(d.getCreateTime()) || endDate.getTime() == (d.getCreateTime().getTime())).
                    findAny().orElse(null);
        }
        return backFlg;
    }

    /**
     * （报表用） 接待客户
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public Communicate getInCustomer(Date startDate, Date endDate) {
        Communicate backFlg = null;
        if (communicateList != null && communicateList.size() > 0) {
            backFlg = communicateList.stream().
                    filter(a -> "JD".equals(a.getCommunicateMode())).
                    filter(c -> startDate.before(c.getCreateTime()) || startDate.getTime() == (c.getCreateTime().getTime())).
                    filter(d -> endDate.after(d.getCreateTime()) || endDate.getTime() == (d.getCreateTime().getTime())).
                    findAny().orElse(null);
        }
        return backFlg;
    }

    /**
     * （报表用） 大定
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public long getOrderBigSet(Date startDate, Date endDate) {
        long backFlg = 0;
        if (syncOrderInfoList != null && syncOrderInfoList.size() > 0) {
            backFlg = syncOrderInfoList.stream().filter(b->b.getOrderNum()!=null).filter(a -> {
                        Boolean back = false;
                        Date depositTime = a.getDepositTime();
                        String depositFinish = a.getDepositFinish();
                        if (depositTime != null && (startDate.before(depositTime) || startDate.getTime() == (depositTime.getTime())) && (endDate.after(depositTime) || endDate.getTime() == (depositTime.getTime())) && ("1".equals(depositFinish))) {
                            back = true;
                        }
                        return back;
                    }
            ).count();
        }
        return backFlg;
    }

    /**
     * （报表用） 小定
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public long getOrderSmallSet(Date startDate, Date endDate) {
        long backFlg = 0;
        if (syncOrderInfoList != null && syncOrderInfoList.size() > 0) {
            backFlg = syncOrderInfoList.stream().filter(b->b.getOrderNum()!=null).filter(b -> b.getDepositAmount() != null).filter(a -> {
                        Boolean back = false;
                        Date depositTime = a.getDepositTime();
                        String depositFinish = a.getDepositFinish();
                        BigDecimal depositAmount = a.getDepositAmount();
                        int i = depositAmount.compareTo(BigDecimal.ZERO);
                        if (depositTime != null && (startDate.before(depositTime) || startDate.getTime() == (depositTime.getTime())) && (endDate.after(depositTime) || endDate.getTime() == (depositTime.getTime())) && ("0".equals(depositFinish) || depositFinish == null) && (i == 1)) {
                            back = true;
                        }
                        return back;
                    }
            ).count();
        }
        return backFlg;
    }

    /**
     * （报表用） 退单
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public long getOrderBack(Date startDate, Date endDate) {
        long backFlg = 0;
        if (syncOrderInfoList != null && syncOrderInfoList.size() > 0) {
            backFlg = syncOrderInfoList.stream().filter(b->b.getOrderNum()!=null).filter(a -> {
                        Boolean back = false;
                        Date orderCloseTime = a.getOrderCloseTime();
                        Boolean orderClosed = a.getOrderClosed();
                        if (orderClosed != null && orderClosed && (orderCloseTime != null && (startDate.before(orderCloseTime) || startDate.getTime() == (orderCloseTime.getTime())) && (endDate.after(orderCloseTime) || endDate.getTime() == (orderCloseTime.getTime())))) {
                            back = true;
                        }
                        return back;
                    }
            ).count();
        }
        return backFlg;
    }

    public String getIntroducer() {
        return introducer;
    }

    public void setIntroducer(String introducer) {
        this.introducer = introducer;
    }

    public String getIntroducerTel() {
        return introducerTel;
    }

    public void setIntroducerTel(String introducerTel) {
        this.introducerTel = introducerTel;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
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

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public Date getCallTime() {
        return callTime;
    }

    public void setCallTime(Date callTime) {
        this.callTime = callTime;
    }

    public String getMechandiser() {
        return mechandiser;
    }

    public void setMechandiser(String mechandiser) {
        this.mechandiser = mechandiser;
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

    public String getPromoteSource() {
        return promoteSource;
    }

    public void setPromoteSource(String promoteSource) {
        this.promoteSource = promoteSource;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTaskTag() {
        return taskTag;
    }

    public void setTaskTag(String taskTag) {
        this.taskTag = taskTag;
    }

    public String getTaskLevel() {
        return taskLevel;
    }

    public void setTaskLevel(String taskLevel) {
        this.taskLevel = taskLevel;
    }

    public String getDistributeStatus() {
        return distributeStatus;
    }

    public void setDistributeStatus(String distributeStatus) {
        this.distributeStatus = distributeStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Date getDistributeTime() {
        return distributeTime;
    }

    public void setDistributeTime(Date distributeTime) {
        this.distributeTime = distributeTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDistributeModel() {
        return distributeModel;
    }

    public void setDistributeModel(String distributeModel) {
        this.distributeModel = distributeModel;
    }

    public String getCallAnswered() {
        return callAnswered;
    }

    public void setCallAnswered(String callAnswered) {
        this.callAnswered = callAnswered;
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getReserveMobile() {
        return reserveMobile;
    }

    public void setReserveMobile(String reserveMobile) {
        this.reserveMobile = reserveMobile;
    }

    public Boolean getIsShop() {
        return isShop;
    }

    public void setIsShop(Boolean isShop) {
        this.isShop = isShop;
    }

    public Boolean getShop() {
        return isShop;
    }

    public void setShop(Boolean shop) {
        isShop = shop;
    }

    public Date getCommunicateTime() {
        return communicateTime;
    }

    public void setCommunicateTime(Date communicateTime) {
        this.communicateTime = communicateTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getIntroducerId() {
        return introducerId;
    }

    public void setIntroducerId(String introducerId) {
        this.introducerId = introducerId;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getPromoteSourceName() {
        return promoteSourceName;
    }

    public void setPromoteSourceName(String promoteSourceName) {
        this.promoteSourceName = promoteSourceName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
