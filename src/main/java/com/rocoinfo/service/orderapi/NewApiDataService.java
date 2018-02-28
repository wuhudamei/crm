package com.rocoinfo.service.orderapi;

import com.rocoinfo.entity.customer.CustomerHouse;
import com.rocoinfo.entity.order.ReturnBackMoneyApply;
import com.rocoinfo.entity.order.SendOrder;
import com.rocoinfo.repository.customer.CustomerHouseDao;
import com.rocoinfo.utils.CrmApiUtil;
import com.rocoinfo.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liupengfei
 * @Description 调用新订单选材系统提供的接口的service
 * 后期如果有需求可以改成多态
 * @Date Created in 2017/12/21 13:33
 */
@Service
public class NewApiDataService {

    /* 创建订单 */
    private final String ORDER_ADD_URL = "/api/crm/createproject";

    /* 查询多个订单信息 */
    private final String ORDER_LIST_URL = "/api/crm/proj/info/list";


    /* 查询多个订单信息 */
    private final String ORDER_DETAIL_URL = "/api/crm/proj/info/detaillist";


    /* 创建退单申请*/
    private final String ORDER_CLOSEAPPLY_URL = "/api/crm/proj/close/save";

    /* 新增串单标签*/
    private final String ORDER_ADDTAG_URL = "/api/crm/proj/singletag/saveorupdate";

    /* 查询串单标签信息*/
    private final String ORDER_TAGINFO_URL = "/api/crm/proj/singletag/list";

    /* 给订单打标签(串单)*/
    private final String ORDER_SINGLETAG_URL = "/api/crm/proj/singletag/singlestring";


    @Autowired
    private CustomerHouseDao orderDao;

    /**
     * 创建订单
     *
     * @param orderInfo 订单信息
     * @return
     */
    public String createNewOrder(SendOrder orderInfo) {
        return CrmApiUtil.postToNewOrderApi(ORDER_ADD_URL, orderInfo);
    }


    /**
     * 批量查询订单的简要信息，根据uuid
     *
     * @return
     */
    public String queryOrdersInfo(String uuidsStr) {
        Map<String, Object> queryParam = new HashMap<String, Object>();
        queryParam.put("contractUuidStr", uuidsStr);
        return CrmApiUtil.postToNewOrderApi(ORDER_LIST_URL, queryParam);
    }


    /**
     * 查询订单详情
     *
     * @param customerNo 客户编号
     * @param orderNo    订单编号
     * @return
     */
    public String getOrderDetailInfo(String customerNo, String orderNo) {
        Map<String, Object> queryParam = new HashMap<String, Object>();
        queryParam.put("customerNo", customerNo);
        queryParam.put("orderNo", orderNo);

        String contractUuid = "";
        CustomerHouse order = null;
        List<CustomerHouse> orderList = orderDao.search(queryParam);
        if (orderList != null || orderList.size() > 0) {
            order = orderList.get(0);
            if (order != null) {
                contractUuid = order.getOrderId();
            }
        }

        Map<String, Object> requestParam = new HashMap<String, Object>();
        requestParam.put("contractUuid", contractUuid);
        return CrmApiUtil.postToNewOrderApi(ORDER_DETAIL_URL, requestParam);
    }


    /**
     * 创建退单申请
     *
     * @param returnBackMoneyApply
     * @return
     */
    public String createCloseOrderApply(ReturnBackMoneyApply returnBackMoneyApply) {
        //构造新版接口需要的参数
        Map<String, Object> queryParam = new HashMap<String, Object>();
        queryParam.put("contractNo", returnBackMoneyApply.getOrderNo());
        queryParam.put("contractUuid", returnBackMoneyApply.getOrderId());
        queryParam.put("applyerNo", returnBackMoneyApply.getApplyerId());
        queryParam.put("applyer", returnBackMoneyApply.getApplyerName());
        queryParam.put("applyerType", "SERVICE_APPLY");
        queryParam.put("checker", String.format("%s（%s）", WebUtils.getLoggedUser().getName(),
                WebUtils.getLoggedUser().getOrgCode()));
        queryParam.put("closeReason", returnBackMoneyApply.getCancelOrderReson());
        queryParam.put("expectRefundAmount", returnBackMoneyApply.getReturnMoneyAmount());
        queryParam.put("expectDeductAmount", returnBackMoneyApply.getDeductMoneyAmount());

        return CrmApiUtil.postToNewOrderApi(ORDER_CLOSEAPPLY_URL, queryParam);
    }


    /**
     * 推送新维护的计划量房时间
     *
     * @param orderNum 订单号
     * @param date     新的计划量房时间
     * @return
     */
    public String syncPlanMesureRoomTime(String orderNum, Date date) {
        //todo 后期实现计划量房时间
        return "";
    }


    /**
     * 新增串单标签
     *
     * @return
     */
    public String addTagInfo(String tagName,String tagContent,String storeCode,String userName){
        Map<String,Object> queryParam = new HashMap<String,Object>();
        queryParam.put("tagName",tagName);
        queryParam.put("tagContent",tagContent);
        queryParam.put("storeCode",storeCode);
        queryParam.put("userName",userName);
        return CrmApiUtil.postToNewOrderApi(ORDER_ADDTAG_URL,queryParam);
    }


    /**
     * 查询订单标签列表
     *
     * @return
     */
    public String queryTagList(Map<String, Object> queryParam) {
        return CrmApiUtil.postToNewOrderApi(ORDER_TAGINFO_URL,queryParam);
    }


    /**
     * 为订单打标签
     *
     * @return
     */
    public String addTagForOrder(String orderId,String tagId) {
        Map<String,Object> queryParam = new HashMap<String,Object>();
        queryParam.put("orderId",orderId);
        queryParam.put("tagId",tagId);
        return CrmApiUtil.postToNewOrderApi(ORDER_SINGLETAG_URL,queryParam);
    }

    /**
     * 在新版接口中统计员工当日的数据
     *
     * @return
     */
    public Map<String, Object> getEmpDayInfo() {
        //todo 调新接口，需要的时候实现
        return null;
    }


    /**
     * 查询某段时间内某员工订单信息
     * @return
     */
    public Map<String, Object> getEmpPerioudInfo(){
        //todo 调新接口，需要的时候实现
        return null;
    }


    /**
     * 查询某员工一个月内订单相关的信息
     * @return
     */
    public Map<String, Object> getEmpMonthInfo(){
        //todo 调新接口，需要的时候实现
        return null;
    }
}



