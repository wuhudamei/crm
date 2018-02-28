package com.rocoinfo.rest.customer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.rocoinfo.entity.order.OrderDto;

import com.rocoinfo.service.orderapi.NewApiDataService;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rocoinfo.Constants;
import com.rocoinfo.common.BaseController;
import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.entity.customer.ReturnOrder;
import com.rocoinfo.service.customer.ReturnOrderService;
import com.rocoinfo.service.order.OrderService;
import com.rocoinfo.shiro.ShiroUser;
import com.rocoinfo.utils.CrmApiUtil;
import com.rocoinfo.utils.WebUtils;


/**
 * 描述：订单相关控制类
 *
 * @author tony
 * @创建时间 2017-06-13 17:24
 */
@SuppressWarnings("all")
@RestController
@RequestMapping(value = "/api/order")
public class OrderController extends BaseController {

    /**
     * 订单无效申请服务
     */
    @Autowired
    private ReturnOrderService returnOrderService;

    /**
     * 订单相关服务
     */
    @Autowired
    private OrderService orderService;


    @Autowired
    private NewApiDataService newApiDataService;
    

    /**
     * 添加无效订单申请
     * @param returnOrder 无效订单相关信息
     * @return
     */
    @RequestMapping(value = "/returnOrder", method = RequestMethod.POST)
    public Object insertReturnOrder(@RequestBody ReturnOrder returnOrder) throws Exception {

        // 任务编号为必须项
        if (returnOrder.getTaskNo() == null || "".equals(StringUtils.trim(returnOrder.getTaskNo()))) {
            return StatusDto.buildFailure("任务编号不能为空");
        }

        // 订单号为必须项
        if (returnOrder.getOrderId() == null || "".equals(StringUtils.trim(returnOrder.getOrderId()))) {
            return StatusDto.buildFailure("订单号不能为空");
        }

        return returnOrderService.handleReturnOrder(returnOrder);
    }

    /**
     * 退单详情
     * @param applyNo 退单编号
     * @return
     */
    @RequestMapping(value = "/returnorderdetail/{applyno}")
    public Object findDetailByApplyNo(@PathVariable("applyno") String applyNo) {
        return StatusDto.buildSuccess(returnOrderService.findDetailByApplyNo(applyNo));
    }

    /**
     * 查询订单详情
     * @param customerNo 客户编号
     * @param orderNo 订单编号
     * @return
     */
    @RequestMapping(value = "/orderdetail")
    public Object orderDetail(@RequestParam(value = "customerNo") String customerNo,
                              @RequestParam(value = "orderNo") String orderNo) {
        return orderService.fetchOrderDetail(customerNo, orderNo);
    }

    /**
     * 针对客户下某个任务的订单列表
     * @param customerNo 客户编号
     * @param taskNo 任务编号
     * @return
     */
    @RequestMapping(value = "/orderlist")
    public Object orderList(@RequestParam(value = "customerNo") String customerNo,
                            @RequestParam(value = "taskNo") String taskNo) {
        return orderService.fetchOrderList(customerNo, taskNo);
    }
    
    /**
     * 带条件的去订单系统中 查询  订单标签
     * @param keyword 查询关键字
     * @return
     */
    @RequestMapping(value = "/ordTag/list")
    public Object ordTagList(String keyword){
    	try {
    		Map<String, Object> params = new HashMap<String, Object>();
            String storeCode = WebUtils.getLoggedUser().getStoreCode();
            params.put("keyword", keyword);
            params.put("storeCode", storeCode);
            //todo 新版接口——查询订单标签列表 queryTagList
    		//请求订单系统,并返回结果
            String tagResult = "";
            if (CrmApiUtil.shouldUseOldApi()) {
                tagResult = CrmApiUtil.post(PropertyHolder.getCrmApiUrl()
                        + Constants.ORD_TAGINFO_LIST, params);
            }
            else{
                tagResult = newApiDataService.queryTagList(params);
            }
    		return tagResult;
		} catch (Exception e) {
			e.printStackTrace();
			return StatusDto.buildFailure("查询订单标签列表失败!");
		}
    }
    
    /**
     * 去订单系统中 新增订单标签
     * @param tagName 标签名称
     * @param tagContent 标签内容
     * @return
     */
    @RequestMapping(value = "/ordTag/add")
    public Object addTagInfo(String tagName, String tagContent){
    	if(StringUtils.isBlank(tagName)){
    		return StatusDto.buildFailure("标签名称不能为空!");
    	}
    	try {
            String result = "";
            //todo 新版接口——新增订单标签 addTagInfo
    	    if(CrmApiUtil.shouldUseOldApi()) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("tagName", tagName);
                params.put("tagContent", tagContent);
                params.put("createTime", new Date());
                //获取正在登录的员工对象
                ShiroUser loggedUser = WebUtils.getLoggedUser();
                params.put("createUserId", loggedUser.getEmpIdinord());
                params.put("createUserName", loggedUser.getName());

                //post请求,订单系统
                result = CrmApiUtil.post(PropertyHolder.getCrmApiUrl()
                        + Constants.ORD_TAGINFO_ADD, params);
            }
            else{
                String storeCode = WebUtils.getLoggedUser().getStoreCode();
                String name = WebUtils.getLoggedUser().getName();
                String orgCode = WebUtils.getLoggedUser().getOrgCode();
                StringBuffer stringBuffer = new StringBuffer();
                String userName = stringBuffer.append(name).append("（").append(orgCode).append("）").toString();
                result = newApiDataService.addTagInfo(tagName,tagContent,storeCode,userName);
            }
    		
    		JSONObject jsonObject = new JSONObject(result);
            String code = jsonObject.getString("code");
            //返回处理结果消息
            // 请求失败
            if ("0".equals(code)) {
                return StatusDto.buildFailure(jsonObject.getString("message"));
            }
            return StatusDto.buildSuccess(jsonObject.getString("message"));
		} catch (Exception e) {
			e.printStackTrace();
			return StatusDto.buildFailure("新增订单标签失败!");
		}
    }
    
    /**
     * 去订单系统中 给订单添加标签
     * @param orderId 订单id
     * @param tagId 标签id
     * @param addTagUserId 标签创建者id
     * @return
     */
    @RequestMapping(value = "/updateOrdWithTag")
    public Object updateOrdWithTag(String orderId, String tagId, String addTagUserId){
    	if(StringUtils.isBlank(orderId) || StringUtils.isBlank(tagId) || StringUtils.isBlank(addTagUserId)){
    		return StatusDto.buildFailure("订单id或标签参数不能为空!");
    	}
    	try {
    	    //todo 新版接口——给订单添加标签 addTagForOrder
    		Map<String, Object> params = new HashMap<String, Object>();
    		params.put("orderId", orderId);
    		params.put("tagId", tagId);
    		params.put("addTagUserId", addTagUserId);
    		
    		//post请求,订单系统
            String result = "";
            if(CrmApiUtil.shouldUseOldApi()){
                result = CrmApiUtil.post(PropertyHolder.getCrmApiUrl()
                        + Constants.ORDER_WITH_TAG_UPDATE, params);
            }
            else{
                result = newApiDataService.addTagForOrder(orderId,tagId);
            }

    		JSONObject jsonObject = new JSONObject(result);
            String code = jsonObject.getString("code");
            //返回处理结果消息
            // 请求失败
            if ("0".equals(code)) {
                return StatusDto.buildFailure(jsonObject.getString("message"));
            }
            return StatusDto.buildSuccess(jsonObject.getString("message"));
            
		} catch (Exception e) {
			e.printStackTrace();
			return StatusDto.buildFailure("订单设置标签失败!");
		}
    }

    /**
     * 添加/编辑生成订单
     * @param orderDto 订单数据
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Object orderAdd(@RequestBody OrderDto orderDto) throws Exception {
        if (orderDto.getCustomerNo() == null) {
            return StatusDto.buildFailure("客户编号不能为空");
        }
        if (orderDto.getTaskNo() == null) {
            return StatusDto.buildFailure("任务编号不能为空");
        }

        return orderService.createOrder(orderDto);
    }

}
