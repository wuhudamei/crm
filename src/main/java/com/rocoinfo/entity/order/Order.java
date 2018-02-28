package com.rocoinfo.entity.order;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 功能描述:订单流转系统中的订单实体类
 * @author phil
 * 2017年6月17日
 */
@SuppressWarnings("all")
public class Order {

    private String orderId;
    private String orderNo;
    private String customerId;

    //新增标签信息，标签ID，添加标签人ID，删除标签人ID，标签添加删除时间
    private String tagId;
    private String addTagUserId;
    private String deleteTagUserId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date changeTagTime;

    private Integer roomNum;
    private Integer hallNum;
    private Integer toiletNum;
    private BigDecimal floorArea;
    private Integer isNew;
    private Integer isLift;
    private Integer isFDH;
    private String province;
    private String provinceCode;
    private String city;
    private String cityCode;
    private String district;
    private String districtCode;
    private String address;
    private String activity;
    private String discount;
    private Integer planDecorateYear;
    private Integer planDecorateMonth;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date reMeasureCreateTime;

    private Integer sJPDStage;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date sJPDStageFinishTime;

    private Integer sJStage;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date sJStageFinishTime;

    private Integer ySStage;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date ySSTageFinishTime;

    private Integer qYStage;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date qYStageFinishTime;

    private String serviceUserId;
    private String serviceName;
    private String serviceMobile;
    private String serviceDepartment;
    private String stylistName;
    private String stylistMobile;
    private String stylistUserId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date stylistAllotTime;

    private String allotGroupId;
    private String courier;
    private String courierUserId;
    private String courierMobile;
    private String supervisorName;
    private String supervisorMobile;
    private String supervisorUserId;
    private String contractor;
    private String contact;
    private String contractNo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date contractStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date contractCompleteTime;

    private Integer contractDays;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date actualStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date actualCompletionTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date placeOrderTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date signFinishTime;

    private Integer isTest;
    private Integer selectMaterialStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date selectMaterialTime;

    private String comboType;
    private Integer state;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    private String creatorUserId;
    private String creator;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    private Integer allotState;
    private Integer isMobile;
    private Integer crmHouseId;
    private Integer houseType;
    private Integer stylistType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date riseTime;

    private String lng;
    private String lat;
    private Integer isOrder;
    private Integer isMediumReminders;
    private Integer isFinalReminders;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date precisionTime;

    /**
     * 门店编号
     */
    private String areaFlag;


    /**
     * 辅助查询，数据库里没有对应的字段
     */
    private String payStatus;//新增财务状态
    private Integer customerLevel;//客户级别
    private String tagName;//串单标签名称

    private String dataSource;    //数据来源
    private String promoteWay;    //推广方式

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getAddTagUserId() {
        return addTagUserId;
    }

    public void setAddTagUserId(String addTagUserId) {
        this.addTagUserId = addTagUserId;
    }

    public String getDeleteTagUserId() {
        return deleteTagUserId;
    }

    public void setDeleteTagUserId(String deleteTagUserId) {
        this.deleteTagUserId = deleteTagUserId;
    }

    public Date getChangeTagTime() {
        return changeTagTime;
    }

    public void setChangeTagTime(Date changeTagTime) {
        this.changeTagTime = changeTagTime;
    }

    public Integer getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }

    public Integer getHallNum() {
        return hallNum;
    }

    public void setHallNum(Integer hallNum) {
        this.hallNum = hallNum;
    }

    public Integer getToiletNum() {
        return toiletNum;
    }

    public void setToiletNum(Integer toiletNum) {
        this.toiletNum = toiletNum;
    }

    public BigDecimal getFloorArea() {
        return floorArea;
    }

    public void setFloorArea(BigDecimal floorArea) {
        this.floorArea = floorArea;
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public Integer getIsLift() {
        return isLift;
    }

    public void setIsLift(Integer isLift) {
        this.isLift = isLift;
    }

    public Integer getIsFDH() {
        return isFDH;
    }

    public void setIsFDH(Integer isFDH) {
        this.isFDH = isFDH;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Integer getPlanDecorateYear() {
        return planDecorateYear;
    }

    public void setPlanDecorateYear(Integer planDecorateYear) {
        this.planDecorateYear = planDecorateYear;
    }

    public Integer getPlanDecorateMonth() {
        return planDecorateMonth;
    }

    public void setPlanDecorateMonth(Integer planDecorateMonth) {
        this.planDecorateMonth = planDecorateMonth;
    }

    public Date getReMeasureCreateTime() {
        return reMeasureCreateTime;
    }

    public void setReMeasureCreateTime(Date reMeasureCreateTime) {
        this.reMeasureCreateTime = reMeasureCreateTime;
    }

    public Integer getsJPDStage() {
        return sJPDStage;
    }

    public void setsJPDStage(Integer sJPDStage) {
        this.sJPDStage = sJPDStage;
    }

    public Date getsJPDStageFinishTime() {
        return sJPDStageFinishTime;
    }

    public void setsJPDStageFinishTime(Date sJPDStageFinishTime) {
        this.sJPDStageFinishTime = sJPDStageFinishTime;
    }

    public Integer getsJStage() {
        return sJStage;
    }

    public void setsJStage(Integer sJStage) {
        this.sJStage = sJStage;
    }

    public Date getsJStageFinishTime() {
        return sJStageFinishTime;
    }

    public void setsJStageFinishTime(Date sJStageFinishTime) {
        this.sJStageFinishTime = sJStageFinishTime;
    }

    public Integer getySStage() {
        return ySStage;
    }

    public void setySStage(Integer ySStage) {
        this.ySStage = ySStage;
    }

    public Date getySSTageFinishTime() {
        return ySSTageFinishTime;
    }

    public void setySSTageFinishTime(Date ySSTageFinishTime) {
        this.ySSTageFinishTime = ySSTageFinishTime;
    }

    public Integer getqYStage() {
        return qYStage;
    }

    public void setqYStage(Integer qYStage) {
        this.qYStage = qYStage;
    }

    public Date getqYStageFinishTime() {
        return qYStageFinishTime;
    }

    public void setqYStageFinishTime(Date qYStageFinishTime) {
        this.qYStageFinishTime = qYStageFinishTime;
    }

    public String getServiceUserId() {
        return serviceUserId;
    }

    public void setServiceUserId(String serviceUserId) {
        this.serviceUserId = serviceUserId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceMobile() {
        return serviceMobile;
    }

    public void setServiceMobile(String serviceMobile) {
        this.serviceMobile = serviceMobile;
    }

    public String getServiceDepartment() {
        return serviceDepartment;
    }

    public void setServiceDepartment(String serviceDepartment) {
        this.serviceDepartment = serviceDepartment;
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

    public String getStylistUserId() {
        return stylistUserId;
    }

    public void setStylistUserId(String stylistUserId) {
        this.stylistUserId = stylistUserId;
    }

    public Date getStylistAllotTime() {
        return stylistAllotTime;
    }

    public void setStylistAllotTime(Date stylistAllotTime) {
        this.stylistAllotTime = stylistAllotTime;
    }

    public String getAllotGroupId() {
        return allotGroupId;
    }

    public void setAllotGroupId(String allotGroupId) {
        this.allotGroupId = allotGroupId;
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

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public String getSupervisorMobile() {
        return supervisorMobile;
    }

    public void setSupervisorMobile(String supervisorMobile) {
        this.supervisorMobile = supervisorMobile;
    }

    public String getSupervisorUserId() {
        return supervisorUserId;
    }

    public void setSupervisorUserId(String supervisorUserId) {
        this.supervisorUserId = supervisorUserId;
    }

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Date getContractStartTime() {
        return contractStartTime;
    }

    public void setContractStartTime(Date contractStartTime) {
        this.contractStartTime = contractStartTime;
    }

    public Date getContractCompleteTime() {
        return contractCompleteTime;
    }

    public void setContractCompleteTime(Date contractCompleteTime) {
        this.contractCompleteTime = contractCompleteTime;
    }

    public Integer getContractDays() {
        return contractDays;
    }

    public void setContractDays(Integer contractDays) {
        this.contractDays = contractDays;
    }

    public Date getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(Date actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public Date getActualCompletionTime() {
        return actualCompletionTime;
    }

    public void setActualCompletionTime(Date actualCompletionTime) {
        this.actualCompletionTime = actualCompletionTime;
    }

    public Date getPlaceOrderTime() {
        return placeOrderTime;
    }

    public void setPlaceOrderTime(Date placeOrderTime) {
        this.placeOrderTime = placeOrderTime;
    }

    public Date getSignFinishTime() {
        return signFinishTime;
    }

    public void setSignFinishTime(Date signFinishTime) {
        this.signFinishTime = signFinishTime;
    }

    public Integer getIsTest() {
        return isTest;
    }

    public void setIsTest(Integer isTest) {
        this.isTest = isTest;
    }

    public Integer getSelectMaterialStatus() {
        return selectMaterialStatus;
    }

    public void setSelectMaterialStatus(Integer selectMaterialStatus) {
        this.selectMaterialStatus = selectMaterialStatus;
    }

    public Date getSelectMaterialTime() {
        return selectMaterialTime;
    }

    public void setSelectMaterialTime(Date selectMaterialTime) {
        this.selectMaterialTime = selectMaterialTime;
    }

    public String getComboType() {
        return comboType;
    }

    public void setComboType(String comboType) {
        this.comboType = comboType;
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

    public String getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(String creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getAllotState() {
        return allotState;
    }

    public void setAllotState(Integer allotState) {
        this.allotState = allotState;
    }

    public Integer getIsMobile() {
        return isMobile;
    }

    public void setIsMobile(Integer isMobile) {
        this.isMobile = isMobile;
    }

    public Integer getCrmHouseId() {
        return crmHouseId;
    }

    public void setCrmHouseId(Integer crmHouseId) {
        this.crmHouseId = crmHouseId;
    }

    public Integer getHouseType() {
        return houseType;
    }

    public void setHouseType(Integer houseType) {
        this.houseType = houseType;
    }

    public Integer getStylistType() {
        return stylistType;
    }

    public void setStylistType(Integer stylistType) {
        this.stylistType = stylistType;
    }

    public Date getRiseTime() {
        return riseTime;
    }

    public void setRiseTime(Date riseTime) {
        this.riseTime = riseTime;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Integer getIsOrder() {
        return isOrder;
    }

    public void setIsOrder(Integer isOrder) {
        this.isOrder = isOrder;
    }

    public Integer getIsMediumReminders() {
        return isMediumReminders;
    }

    public void setIsMediumReminders(Integer isMediumReminders) {
        this.isMediumReminders = isMediumReminders;
    }

    public Integer getIsFinalReminders() {
        return isFinalReminders;
    }

    public void setIsFinalReminders(Integer isFinalReminders) {
        this.isFinalReminders = isFinalReminders;
    }

    public Date getPrecisionTime() {
        return precisionTime;
    }

    public void setPrecisionTime(Date precisionTime) {
        this.precisionTime = precisionTime;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getCustomerLevel() {
        return customerLevel;
    }

    public void setCustomerLevel(Integer customerLevel) {
        this.customerLevel = customerLevel;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getAreaFlag() {
        return areaFlag;
    }

    public void setAreaFlag(String areaFlag) {
        this.areaFlag = areaFlag;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getPromoteWay() {
        return promoteWay;
    }

    public void setPromoteWay(String promoteWay) {
        this.promoteWay = promoteWay;
    }
}

