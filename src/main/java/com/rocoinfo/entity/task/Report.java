package com.rocoinfo.entity.task;

import com.rocoinfo.entity.IdEntity;

/**
 * <dl>
 * <dd>Description: 美得你crm 报表实体</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/7/17</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */

public class Report extends IdEntity {
    /**
     * 来源名称
     */
    private String sourceName;
    /**
     * 来源编号
     */
    private String sourceCode;
    /**
     * 渠道名称
     */
    private String promoteName;
    /**
     * 渠道编号
     */
    private String promoteCode;
    /**
     * 进线数量
     */
    private Long countLine;
    /**
     * 进店数量
     */
    private Long countShop;

    /**
     * 大定数量
     */
    private Long bigSet;

    /**
     * 邀约率
     */
    private  String invitationRate;
    /**
     * 线索转化率
     */
    private  String transformationRate;
    /**
     * 进店转化率
     */
    private  String goShopRate;
    /**
     *来源贡献率
     */
    private  String sourceRate;
    /**
     * 渠道贡献率
     */
    private  String channelRate;

    /**
     * 个人报表 名字
     */
    private  String empName;
    /**
     * 个人报表累计派单量
     */
    private  String cumulativeDispatch;
    /**
     *个人报表当前待邀约量
     */
    private  String currentInvitations;
    /**
     *个人报表累计已邀约量
     */
    private  String cumulativeUnsolicitedQuantity;
    /**
     *个人报表累计接待客户数
     */
    private  String totalCustomersReceived;
    /**
     *个人报表累计小定数
     */
    private  String cumulativeSmallNumber;
    /**
     *个人报表累计大定数
     */
    private  String cumulativeBigNumber;
    /**
     *个人报表累计退单数
     */
    private  String cumulativeBack;

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getCumulativeDispatch() {
        return cumulativeDispatch;
    }

    public void setCumulativeDispatch(String cumulativeDispatch) {
        this.cumulativeDispatch = cumulativeDispatch;
    }

    public String getCurrentInvitations() {
        return currentInvitations;
    }

    public void setCurrentInvitations(String currentInvitations) {
        this.currentInvitations = currentInvitations;
    }

    public String getCumulativeUnsolicitedQuantity() {
        return cumulativeUnsolicitedQuantity;
    }

    public void setCumulativeUnsolicitedQuantity(String cumulativeUnsolicitedQuantity) {
        this.cumulativeUnsolicitedQuantity = cumulativeUnsolicitedQuantity;
    }

    public String getTotalCustomersReceived() {
        return totalCustomersReceived;
    }

    public void setTotalCustomersReceived(String totalCustomersReceived) {
        this.totalCustomersReceived = totalCustomersReceived;
    }

    public String getCumulativeSmallNumber() {
        return cumulativeSmallNumber;
    }

    public void setCumulativeSmallNumber(String cumulativeSmallNumber) {
        this.cumulativeSmallNumber = cumulativeSmallNumber;
    }

    public String getCumulativeBigNumber() {
        return cumulativeBigNumber;
    }

    public void setCumulativeBigNumber(String cumulativeBigNumber) {
        this.cumulativeBigNumber = cumulativeBigNumber;
    }

    public String getCumulativeBack() {
        return cumulativeBack;
    }

    public void setCumulativeBack(String cumulativeBack) {
        this.cumulativeBack = cumulativeBack;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getPromoteName() {
        return promoteName;
    }

    public void setPromoteName(String promoteName) {
        this.promoteName = promoteName;
    }

    public Long getCountLine() {
        return countLine;
    }

    public void setCountLine(Long countLine) {
        this.countLine = countLine;
    }

    public Long getCountShop() {
        return countShop;
    }

    public void setCountShop(Long countShop) {
        this.countShop = countShop;
    }

    public Long getBigSet() {
        return bigSet;
    }

    public void setBigSet(Long bigSet) {
        this.bigSet = bigSet;
    }

    public String getInvitationRate() {
        return invitationRate;
    }

    public void setInvitationRate(String invitationRate) {
        this.invitationRate = invitationRate;
    }

    public String getTransformationRate() {
        return transformationRate;
    }

    public void setTransformationRate(String transformationRate) {
        this.transformationRate = transformationRate;
    }

    public String getGoShopRate() {
        return goShopRate;
    }

    public void setGoShopRate(String goShopRate) {
        this.goShopRate = goShopRate;
    }

    public String getSourceRate() {
        return sourceRate;
    }

    public void setSourceRate(String sourceRate) {
        this.sourceRate = sourceRate;
    }

    public String getChannelRate() {
        return channelRate;
    }

    public void setChannelRate(String channelRate) {
        this.channelRate = channelRate;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getPromoteCode() {
        return promoteCode;
    }

    public void setPromoteCode(String promoteCode) {
        this.promoteCode = promoteCode;
    }
}
