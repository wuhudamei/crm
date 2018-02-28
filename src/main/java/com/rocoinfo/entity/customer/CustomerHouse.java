package com.rocoinfo.entity.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rocoinfo.entity.IdEntity;

import java.util.Date;

/**
 * 描述：房屋信息
 *
 * @author tony
 * @date 2017-06-07 09:56
 */
@SuppressWarnings("all")
public class CustomerHouse extends IdEntity{

    /**
     * 客户编号
     */
    private String customerNo;

    /**
     * 任务ID
     */
    private String taskNo;

    /**
     * 订单号
     */
    private String orderNum;

    /**
     * 订单ID，主要是用于跟订单系统交互使用，在Crm系统基本不到。第一次生成订单的时候会回填这个值进来
     */
    private String orderId;

    /**
     * 房屋户型
     */
    private String houseLayout;

    /**
     * 面积
     */
    private Double houseArea;

    /**
     * 房屋类型  1-新房 0-旧房
     */
    private String houseType;

    /**
     * 是否期房 0-否 1-是
     */
    private String hoursing;

    /**
     * 有无电梯 0-无 1-有
     */
    private String elevator;

    /**
     * 装修时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+08:00")
    private Date renovationTime;

    /**
     * 省市区对应数据
     */
    private String provinceCode;
    private String provinceName;
    private String cityCode;
    private String cityName;
    private String areaCode;
    private String areaName;

    /**
     * 房屋地址
     */
    private String address;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 订单创建时间
     */
    private Date createOrderTime;

    /**
     * 订单退订时的状态。 正常：1， 退订中： 2： 退订通过：0
     */
    private Integer orderStatus;

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

    public String getHouseLayout() {
        return houseLayout;
    }

    public void setHouseLayout(String houseLayout) {
        this.houseLayout = houseLayout;
    }

    public Double getHouseArea() {
		return houseArea;
	}

	public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getHoursing() {
        return hoursing;
    }

    public void setHoursing(String hoursing) {
        this.hoursing = hoursing;
    }

    public String getElevator() {
		return elevator;
	}

	public void setElevator(String elevator) {
		this.elevator = elevator;
	}

	public void setHouseArea(Double houseArea) {
		this.houseArea = houseArea;
	}

	public Date getRenovationTime() {
        return renovationTime;
    }

    public void setRenovationTime(Date renovationTime) {
        this.renovationTime = renovationTime;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	public Date getCreateOrderTime() {
        return createOrderTime;
    }

    public void setCreateOrderTime(Date createOrderTime) {
        this.createOrderTime = createOrderTime;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
}
