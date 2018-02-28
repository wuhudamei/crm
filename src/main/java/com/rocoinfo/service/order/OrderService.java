package com.rocoinfo.service.order;

import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.entity.customer.Customer;
import com.rocoinfo.entity.customer.CustomerHouse;
import com.rocoinfo.entity.customer.CustomerTaskRmk;
import com.rocoinfo.entity.employee.Employee;
import com.rocoinfo.entity.order.OrderDto;
import com.rocoinfo.entity.order.ReturnBackMoneyApply;
import com.rocoinfo.entity.order.SendOrder;
import com.rocoinfo.entity.order.SyncOrderInfo;
import com.rocoinfo.entity.task.TaskDistribute;
import com.rocoinfo.enumeration.CustomerStatus;
import com.rocoinfo.enumeration.SwapOneAndZero;
import com.rocoinfo.repository.order.OrderInfoDao;
import com.rocoinfo.service.customer.CustomerHouseService;
import com.rocoinfo.service.customer.CustomerService;
import com.rocoinfo.service.customer.CustomerTaskRmkService;
import com.rocoinfo.service.employee.EmployeeService;
import com.rocoinfo.service.orderapi.NewApiDataService;
import com.rocoinfo.service.task.TaskDistributeService;
import com.rocoinfo.shiro.ShiroUser;
import com.rocoinfo.utils.CrmApiUtil;
import com.rocoinfo.utils.JsonUtils;
import com.rocoinfo.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 描述：订单相关服务类
 *
 * @author tony
 * @创建时间 2017-06-19 14:10
 */
@SuppressWarnings("all")
@Service
public class OrderService {
    @Autowired
    private EmployeeService employeeService;
    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 订单系统路径
     */
    private final String CRM_ORDER_API_URL = PropertyHolder.getCrmApiUrl();

    /**
     * 订单列表请求路径<br/>
     * cusIdArrStr  客户关联订单的唯一编码<br/>
     * ordNoArrStr  如: 12334,23432
     * "http://rocolpf.rocozxb.cn/crmapi/api/ord/updOrd";
     */
    private final String ORDER_LIST = CRM_ORDER_API_URL + "/api/ord/simpOrds";

    /**
     * 订单详情请求路径<br/>
     * cusIdStr  客户关联订单的唯一编码<br/>
     * ordNoStr 订单编号
     */
    private final String ORDER_DETAIL = CRM_ORDER_API_URL + "/api/ord/ordDtl";

    /**
     * 同步客户  客户在订单流转系统中的客户id
     */
    private final String CUSTOMER_SYNC = CRM_ORDER_API_URL + "/api/cus/syncCus";

    /**
     * 添加/编辑订单
     */
    private final String NEW_ORDER = CRM_ORDER_API_URL + "/api/ord/newOrd";
    private final String NEW_ORDER_IN_NEWAPI = CRM_ORDER_API_URL+"/api/crm/createproject";

    private final String EDIT_ORDER = CRM_ORDER_API_URL + "/api/ord/updOrd";

    /**
     * 同意退单申请路径<br/>
     */
    private final String APPLY_RET_MONEY = CRM_ORDER_API_URL + "/api/ord/applyRetMoney";

    /**
     * 查询指定的多个订单交付定金情况
     */
    private final String SIMP_ORDS = CRM_ORDER_API_URL + "/api/ord/ordsFinanceState";

    /**
     * 客户服务
     */
    @Autowired
    private CustomerService customerService;

    /**
     * 房屋服务
     */
    @Autowired
    private CustomerHouseService customerHouseService;

    /**
     * 任务轨迹服务
     */
    @Autowired
    private CustomerTaskRmkService customerTaskRmkService;

    /**
     * 任务派发服务
     */
    @Autowired
    private TaskDistributeService taskDistributeService;

    @Autowired
    private OrderInfoDao orderInfoDao;

    @Autowired
    private NewApiDataService newApiDataService;

    /**
     * 订单列表
     * @param customerNo 客户编号
     * @param taskNo 任务编号
     * @return
     */
    public StatusDto fetchOrderList(String customerNo, String taskNo) {
        String result = null;
        try {
            // 获取客户数据
            Customer customer = customerService.getCustomerByCustomerNo(customerNo);

            // 跟这个任务有关的房屋
            List<CustomerHouse> houses = customerHouseService.findByTaskNo(taskNo);

            Map map = new HashMap();
            Map<String, Integer> orderStatus = new HashMap<String, Integer>();
            StringBuffer ordersSb = new StringBuffer();
            StringBuffer orderUuidsSb = new StringBuffer();
            for (int i = 0; i < houses.size(); i++) {
                CustomerHouse customerHouse = houses.get(i);

                // 如果没有订单号则不处理
                if (customerHouse.getOrderNum() == null || "".equals(StringUtils.trim(customerHouse.getOrderNum()))
                        || "".equals(StringUtils.trim(customerHouse.getOrderId()))) {
                    continue;
                }

                ordersSb.append(customerHouse.getOrderNum()).append(",");
                orderUuidsSb.append(customerHouse.getOrderId()).append(",");

                if (customerHouse.getOrderId() != null && !"".equals(StringUtils.trim(customerHouse.getOrderId()))) {
                    orderStatus.put(StringUtils.lowerCase(customerHouse.getOrderId()), customerHouse.getOrderStatus());
                }
            }
            String orders = ordersSb.toString();
            String orderUuidsStr = orderUuidsSb.toString();
            // 如果所有的房屋都没有生成订单，则直接返回一个空的列表，不再同步客户信息
            if ("".equals(orders)) {
                Map tMap = new HashMap();
                tMap.put("orders", Collections.EMPTY_LIST);
                return StatusDto.buildSuccess(tMap);
            }
            else {
                orders = orders.substring(0, orders.length() - 1);
                orderUuidsStr = orderUuidsStr.substring(0, orderUuidsStr.length() - 1);
            }

            // 同步客户信息，新版接口无需执行该操作
            syncCustomerIdFromOrder(customer);

            //FIXME 新版接口——查询订单列表 queryOrdersInfo
            //要调用老的
            if(CrmApiUtil.shouldUseOldApi()) {
                map.put("cusIdArrStr", customer.getCustomerIdinord());
                map.put("ordNoArrStr", orders);
                result = CrmApiUtil.post(ORDER_LIST, map);
            }
            else{
                result = newApiDataService.queryOrdersInfo(orderUuidsStr);
            }
            logger.error("订单数据列表字符串：" + result);

            JSONObject jsonObject = new JSONObject(result);
            String code = jsonObject.getString("code");

            // 请求失败
            if ("0".equals(code)) {
                return StatusDto.buildFailure(jsonObject.getString("message"));
            }

            String dataStr = jsonObject.getString("data");
            Map _map = new HashMap();
            _map.put("orders", dataStr);
            _map.put("ordderStatus", orderStatus);

            return StatusDto.buildSuccess(_map);
        }
        catch (Exception e) {
            logger.error("请求订单数据失败", e);
            return StatusDto.buildFailure(e.getMessage());
        }
    }

    /**
     * 如果CRM系统中还没有订单系统中的客户ID，则先去请求回来并保存到 CRM系统
     * @param customer
     */
    private void syncCustomerIdFromOrder(Customer customer) throws Exception {
        //FIXME 新版接口中无需执行该流程——同步客户信息
        if(!CrmApiUtil.shouldUseOldApi()){
            return;
        }
        String cusTIdInOrder = null;

        // 看看客户有没有生成订单服务需要的ID, 如果没有则需要先生成一个
        if (customer.getCustomerIdinord() == null || "".equals(StringUtils.trim(customer.getCustIdInOrder()))) {

            try {
                String custIdStr = updateCustomer(customer, null);

                logger.error("同步客户订单系统字符串：" + custIdStr);

                JSONObject custIdJsonObject = new JSONObject(custIdStr);

                String code = custIdJsonObject.getString("code");

                // 请求失败
                if ("0".equals(code)) {
                    throw new Exception("客户信息缺失，无法创建订单");
                }

                cusTIdInOrder = custIdJsonObject.getString("data");
            } catch (Exception e) {
                e.printStackTrace();
            }

            customer.setCustIdInOrder(cusTIdInOrder);
            customerService.update(customer);
        }
    }

    /**
     * 更新订单系统客户数据 并返回订单系统的客户ID
     * @param customer
     * @return
     */
    public String updateCustomer(Customer customer, String customerLevel) {
        //FIXME 新版接口中无需执行该流程——同步客户信息
        if(!CrmApiUtil.shouldUseOldApi()){
            return JsonUtils.toJson(StatusDto.buildFailure());
        }
        Map map_ = new HashMap();
        map_.put("customerName", customer.getCustomerName());
        map_.put("mobile", customer.getCustomerMobile());
        map_.put("reserveMobile", customer.getReserveMobile());
        map_.put("homePhone", customer.getHomePhone());
        map_.put("customerId", customer.getCustomerIdinord());
        map_.put("customerType", customer.getCustomerTag());
        map_.put("customerLevel", customerLevel);

        try {
            return CrmApiUtil.post(CUSTOMER_SYNC, map_);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("更新客户信息或者请求同步客户在订单流转系统中的客户id失败", e);
        }
        return "";
    }

    /**
     * 订单详情
     * @param customerNo 客户编号
     * @param orderNo 订单编号
     * @return
     */
    public StatusDto fetchOrderDetail(String customerNo, String orderNo) {
        String result = null;
        try {
            // 获取客户数据
            Customer customer = customerService.getCustomerByCustomerNo(customerNo);

            Map map = new HashMap();
            //FIXME 新版接口——查询订单详情接口 getOrderDetailInfo
            if(CrmApiUtil.shouldUseOldApi()) {
                map.put("cusIdStr", customer.getCustomerIdinord());
                map.put("ordNoStr", orderNo);
                result = CrmApiUtil.post(ORDER_DETAIL, map);
            }
            else{
                result = newApiDataService.getOrderDetailInfo(customerNo,orderNo);
            }

            logger.error("订单详情数据字符串：" + result);

            JSONObject jsonObject = new JSONObject(result);
            String code = jsonObject.getString("code");

            // 请求失败
            if ("0".equals(code)) {
                return StatusDto.buildFailure(jsonObject.getString("message"));
            }

            String dataStr = jsonObject.getString("data");
            Map _map = new HashMap();
            _map.put("order", dataStr);

            return StatusDto.buildSuccess(_map);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return StatusDto.buildFailure("服务端异常");
    }

    /**
     * 添加/编辑 订单数据
     * @param orderDto
     * @return
     */
    @Transactional
    public StatusDto createOrder(OrderDto orderDto) throws Exception {
        // 新增订单
        if (orderDto.getOrder().getOrderNo() == null) {

            //取到当前创建订单客服
            ShiroUser currentService = WebUtils.getLoggedUser();

            // 取回客户数据
            Customer customer = customerService.getCustomerByCustomerNo(orderDto.getCustomerNo());

            //老版接口中需要先同步客户信息
            syncCustomerIdFromOrder(customer);

            JSONObject jsonObject = null;
            String ordNum = null;
            String ordId = null;
            try {

                // 取得任务的数据来源和推广方式
                TaskDistribute taskDistribute = taskDistributeService.getDistributeTaskByTaskNo(orderDto.getTaskNo());
                orderDto.getOrder().setDataSource(taskDistribute.getDataSource());
                orderDto.getOrder().setPromoteWay(taskDistribute.getPromoteSource());
                Employee oneByJobNum = employeeService.getOneByJobNum(taskDistribute.getMechandiser());
                //根据线索的门店 设置订单的门店编号
                orderDto.getOrder().setAreaFlag(taskDistribute.getStore());

                orderDto.getOrder().setCrmHouseId((int)orderDto.getHouseId());
                orderDto.getOrder().setCustomerId(customer.getCustIdInOrder());


                //FIXME 新版接口——创建订单 createNewOrder
                String ordStr = "";
                String storeCodeStr = orderDto.getOrder().getAreaFlag();
                if(CrmApiUtil.shouldUseOldApi(storeCodeStr)) {
                    orderDto.getOrder().setServiceUserId(WebUtils.getLoggedUser().getEmpIdinord());
                    SendOrder sendOrder = new SendOrder(orderDto.getOrder(), orderDto.getPlaceOrder(), orderDto.getRemark(),customer);
                    ordStr = CrmApiUtil.post(NEW_ORDER, sendOrder ,storeCodeStr);
                }
                else {
                    //客服信息
                    orderDto.getOrder().setServiceUserId(currentService.getOrgCode());
                    orderDto.getOrder().setServiceName(currentService.getName());
                    orderDto.getOrder().setServiceMobile(oneByJobNum.getMobile());
                    SendOrder sendOrder = new SendOrder(orderDto.getOrder(), orderDto.getPlaceOrder(), orderDto.getRemark(),customer);
                    ordStr = newApiDataService.createNewOrder(sendOrder);
                }
                logger.error("添加订单成功返回字符串：" + ordStr);

                // 把生成的订单编号同步到房屋信息里面
                jsonObject = new JSONObject(ordStr);
                String code = jsonObject.getString("code");
                if (SwapOneAndZero.ZERO.getLabel().equals(code)) {
                    return StatusDto.buildFailure(jsonObject.getString("message"));
                }
                ordNum = jsonObject.getJSONObject("data").getString("orderNo");
                ordId = jsonObject.getJSONObject("data").getString("orderId");
            } catch (Exception e) {
                e.printStackTrace();
                return StatusDto.buildFailure(e.getMessage());
            }
            // 更新订单编号到房屋记录中
            CustomerHouse customerHouse = new CustomerHouse();
            customerHouse.setId(orderDto.getHouseId());
            customerHouse.setOrderNum(ordNum);
            customerHouse.setOrderId(ordId);
            customerHouseService.update(customerHouse);

            // 任务状态轨迹表插入状态 任务状态为  已生单
            // 把该任务轨迹中对应的 是否最新记录(0-旧状态,1-新状态)  更新为旧状态
            CustomerTaskRmk customerTaskRmkUpdate = new CustomerTaskRmk();
            customerTaskRmkUpdate.setTaskNo(orderDto.getTaskNo());
            customerTaskRmkUpdate.setNewFlag(SwapOneAndZero.ZERO.getLabel());
            customerTaskRmkService.updateByTaskNo(customerTaskRmkUpdate);

            // 插入一条新的任务轨迹
            CustomerTaskRmk customerTaskRmkInsert = new CustomerTaskRmk();
            customerTaskRmkInsert.setNewFlag(SwapOneAndZero.ONE.getLabel());
            customerTaskRmkInsert.setCreateTime(new Date());
            // 已生单
            customerTaskRmkInsert.setCurrentStatus(CustomerStatus.ORDERSUCCESS.name());
            customerTaskRmkInsert.setCustomerNo(orderDto.getCustomerNo());
            customerTaskRmkInsert.setTaskNo(orderDto.getTaskNo());

            TaskDistribute taskDistribute = taskDistributeService.getDistributeTaskByTaskNo(orderDto.getTaskNo());
            customerTaskRmkInsert.setMechandiser(taskDistribute.getMechandiser());

            customerTaskRmkService.insert(customerTaskRmkInsert);


            // 插入一条数据到订单信息表中
            SyncOrderInfo syncOrderInfo = new SyncOrderInfo();
            syncOrderInfo.setCustomerNo(orderDto.getCustomerNo());
            syncOrderInfo.setTaskNo(orderDto.getTaskNo());
            syncOrderInfo.setOrderNum(ordNum);
            syncOrderInfo.setOrderId(ordId);
            syncOrderInfo.setHouseId(orderDto.getHouseId());
            syncOrderInfo.setStoreCode(WebUtils.getLoggedUser().getStoreCode());
            syncOrderInfo.setCreateTime(new Date());

            orderInfoDao.insert(syncOrderInfo);

            return StatusDto.buildSuccess("添加成功");
        }
        // 编辑订单
        else {
            try {
                SendOrder sendOrder = new SendOrder(orderDto.getOrder(), orderDto.getPlaceOrder(), orderDto.getRemark());
                sendOrder.getPlaceOrder().setOrderId(sendOrder.getOrder().getOrderId());

                if (sendOrder.getRemark() != null) {
                    sendOrder.getRemark().setTypeId(sendOrder.getOrder().getOrderId());
                    sendOrder.getRemark().setPosition(2);
                }

                String ordStr = CrmApiUtil.post(EDIT_ORDER, sendOrder);

                logger.error("订单编辑成功返回字符串：" + ordStr);

            } catch (Exception e) {
                e.printStackTrace();
                return StatusDto.buildFailure(e.getMessage());
            }
            return StatusDto.buildSuccess("更新成功");
        }
    }

    /**
     * 如果沟通记录是计划量房的话，需要把计划量房时间同步更新到订单系统
     * @param orderNum 订单编号
     * @param date 时间
     */
    public void updateCommunicateAmountOfRoomTimeToOrder(String orderNum, Date date) {
        try {
            //FIXME 新版接口——推送新维护的计划量房时间 syncPlanMesureRoomTime
            Map map = new HashMap();
            map.put("orderNum", orderNum);
            map.put("date", date);
            if(CrmApiUtil.shouldUseOldApi()) {
                CrmApiUtil.post("", map);
            }
            else{
                newApiDataService.syncPlanMesureRoomTime(orderNum,date);
            }
        } catch (Exception e) {
            logger.error("订单(" + orderNum + ")同步预约量房时间失败", e);
            e.printStackTrace();
        }
    }

    /**
     * 退单申请通过到订单系统
     * @param returnBackMoneyApply 退单数据
     */
    @Transactional(rollbackFor = Exception.class)
    public void returnBackMoney(ReturnBackMoneyApply returnBackMoneyApply) throws Exception {
        //FIXME 新版接口——创建退单申请 createCloseOrderApply
        String result = "";
        if(CrmApiUtil.shouldUseOldApi()) {
            result = CrmApiUtil.post(APPLY_RET_MONEY, returnBackMoneyApply);
        }
        else{
            result = newApiDataService.createCloseOrderApply(returnBackMoneyApply);
        }
        JSONObject jsonObject = new JSONObject(result);
        String code = jsonObject.getString("code");

        // 请求失败
        if ("0".equals(code)) {
            throw new Exception(jsonObject.getString("message"));
        }
    }

    /**
     * 查询指定的多个订单交付定金情况
     * @param map 如：<br >
     *      {<br>
     *          "cusIdArrStr":"AF296D72-3222-4CB5-B4E0-1C7C2983812B",<br />
     *          "ordNoArrStr":"201611266269,201611261208"<br />
     *
     *      }
     * @return
     *      [{
     *          SyncOrderInfo
     *      }
     *      ...
     *      ]
     */
    public List<SyncOrderInfo> ordsFinance(Map map, String storeCode) {
        //FIXME 新版接口中无需执行该流程——财务定金同步
        if(!CrmApiUtil.shouldUseOldApi(storeCode)){
            return null;
        }
        try {
            String result = CrmApiUtil.post(SIMP_ORDS, map, storeCode);
            // 同步定金信息
            JSONObject jsonObject = new JSONObject(result);
            String code = jsonObject.getString("code");
            if (SwapOneAndZero.ZERO.getLabel().equals(code)) {
                logger.error("查询指定的多个订单交付定金情况失败:", jsonObject.getString("message"));
                return null;
            }

            JSONArray array = jsonObject.getJSONArray("data");

            // 组装返回列表
            List<SyncOrderInfo> resultList = new ArrayList<SyncOrderInfo>();
            Map<String, Object> _map = null;
            SyncOrderInfo syncOrderInfo = null;
            for (int i = 0, l = array.length(); i < l; i++) {
                JSONObject _jsonObject = array.getJSONObject(i);

                Iterator it = _jsonObject.keys();
                _map = new HashMap<String, Object>();
                while (it.hasNext()) {
                    String key = String.valueOf(it.next());
                    _map.put(key, _jsonObject.get(key));
                }

                syncOrderInfo = new SyncOrderInfo();
                syncOrderInfo.setOrderNum(String.valueOf(_map.get("OrderNo")));
                syncOrderInfo.setDepositFinish(_map.containsKey("IsImprestAmount") && _map.get("IsImprestAmount") != null
                        ? _jsonObject.getInt("IsImprestAmount") + "" : null);
                syncOrderInfo.setDepositAbleback(_map.containsKey("depositAbleBack") && _map.get("depositAbleBack") != null
                        ? _jsonObject.getString("depositAbleBack") : null);
                syncOrderInfo.setDepositAmount(_map.containsKey("ImprestAmount") && _map.get("ImprestAmount") != null
                        ? BigDecimal.valueOf(_jsonObject.getDouble("ImprestAmount")) : null);
                syncOrderInfo.setDepositTime(_map.containsKey("ImprestAmountTime") && _map.get("ImprestAmountTime") != null
                        && StringUtils.isNotBlank(_jsonObject.getString("ImprestAmountTime"))
                        ? new Date(Long.parseLong(_jsonObject.getString("ImprestAmountTime"))) : null);
                //订单是否关闭
                String allotStateStr = _map.containsKey("AllotState") && _map.get("AllotState") != null
                        ? _jsonObject.getString("AllotState") : "";
                syncOrderInfo.setOrderClosed("21".equals(allotStateStr) ? true :false);
                //订单关闭时间
                if(syncOrderInfo.getOrderClosed()){
                    syncOrderInfo.setOrderCloseTime(_map.containsKey("UpdateTime") && _map.get("UpdateTime") != null
                            && StringUtils.isNotBlank(_jsonObject.getString("UpdateTime"))
                            ? new Date(Long.parseLong(_jsonObject.getString("UpdateTime"))) : null);
                }

                resultList.add(syncOrderInfo);
            }

            return resultList;
        } catch (Exception e) {
            logger.error(storeCode+"查询指定的多个订单交付定金情况失败:", e);
        }

        return null;
    }


    /**
     * 新版的同步订单定金信息
     * @param reqMap
     * @return
     */
    public StatusDto syncOrderDepositInfo(Map<String,Object> reqMap){
        try {
            //处理下入参，有些参数可能为空
            String orderId = reqMap.get("contractUuid").toString();
            //定金已交金额
            BigDecimal depositAmount = null;
            if(reqMap.containsKey("depositTotalPayed") && reqMap.get("depositTotalPayed") != null
                    && StringUtils.isNotBlank(reqMap.get("depositTotalPayed").toString())) {
                depositAmount = new BigDecimal(reqMap.get("depositTotalPayed").toString());
            }
            //定金交款时间
            Date depositTime = null;
            if(reqMap.containsKey("depositTime") && reqMap.get("depositTime") != null
                    && StringUtils.isNotBlank(reqMap.get("depositTime").toString())) {
                depositTime = new Date(Long.parseLong(reqMap.get("depositTime").toString()));
            }
            //是否转大定
            Integer depositFinish = null;
            if(reqMap.containsKey("depositFinished") && reqMap.get("depositFinished") != null
                    && StringUtils.isNotBlank(reqMap.get("depositFinished").toString())){
                depositFinish = Integer.parseInt(reqMap.get("depositFinished").toString());
            }
            //定金是否可退字样
            Integer ableBack = null;
            if(reqMap.containsKey("depositAbleBack") && reqMap.get("depositAbleBack") != null
                    && StringUtils.isNotBlank(reqMap.get("depositAbleBack").toString())){
                ableBack = Integer.parseInt(reqMap.get("depositAbleBack").toString());
            }
            //订单是否关闭
            Boolean orderClosed = null;
            if(reqMap.containsKey("orderClosed") && reqMap.get("orderClosed") != null
                    && StringUtils.isNotBlank(reqMap.get("orderClosed").toString())){
                orderClosed = "1".equals(reqMap.get("orderClosed").toString()) ? true : false;
            }

            //订单关闭时间
            Date orderCloseTime = null;
            if(reqMap.containsKey("orderCloseTime") && reqMap.get("orderCloseTime") != null
                    && StringUtils.isNotBlank(reqMap.get("orderCloseTime").toString())) {
                orderCloseTime = new Date(Long.parseLong(reqMap.get("orderCloseTime").toString()));
            }

            SyncOrderInfo syncOrderInfo = new SyncOrderInfo();
            syncOrderInfo.setOrderId(orderId);
            syncOrderInfo.setDepositFinish(depositFinish == null ? null : depositFinish.toString());
            syncOrderInfo.setDepositAbleback(ableBack == null ? null :ableBack.toString());
            syncOrderInfo.setDepositAmount(depositAmount);
            syncOrderInfo.setDepositTime(depositTime);
            syncOrderInfo.setOrderClosed(orderClosed);
            syncOrderInfo.setOrderCloseTime(orderCloseTime);


            //更新订单
            orderInfoDao.update(syncOrderInfo);

            return StatusDto.buildSuccess();
        }catch (Exception exp){
            exp.printStackTrace();
            return StatusDto.buildFailure("同步项目定金信息失败");
        }
    }
}
