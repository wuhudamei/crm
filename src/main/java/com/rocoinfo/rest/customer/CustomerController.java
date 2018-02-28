package com.rocoinfo.rest.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rocoinfo.entity.task.TaskDistribute;
import com.rocoinfo.service.customer.InvalidateUserApplyService;
import com.rocoinfo.service.orderapi.NewApiDataService;
import com.rocoinfo.shiro.ShiroUser;
import com.rocoinfo.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import com.google.common.collect.Maps;
import com.rocoinfo.common.BaseController;
import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.dto.page.PageTable;
import com.rocoinfo.dto.page.Pagination;
import com.rocoinfo.entity.customer.Communicate;
import com.rocoinfo.entity.customer.Customer;
import com.rocoinfo.entity.customer.CustomerHouse;
import com.rocoinfo.enumeration.CustomerStatus;
import com.rocoinfo.service.customer.CommunicateService;
import com.rocoinfo.service.customer.CustomerHouseService;
import com.rocoinfo.service.customer.CustomerService;
import com.rocoinfo.service.task.TaskDistributeService;

/**
 * 描述：客户操作相关入口
 *
 * author tony
 * 创建日期 2017-06-07 14:35
 */
@SuppressWarnings("all")
@RestController
@RequestMapping(value = "/api/customer")
public class CustomerController extends BaseController {

    /**
     * 客户服务
     */
    @Autowired
    private CustomerService customerService;

    /**
     * 沟通记录服务
     */
    @Autowired
    private CommunicateService communicateService;

    /**
     * 房屋信息服务
     */
    @Autowired
    private CustomerHouseService customerHouseService;

    /**
     * 任务派发服务
     */
    @Autowired
    private TaskDistributeService taskDistributeService;

    /**
     * 无效客户申请服务
     */
    @Autowired
    private InvalidateUserApplyService invalidateUserApplyService;


    /**
     * 新版的订单crmapi接口调用工具类
     */
    @Autowired
    private NewApiDataService newApiDataService;

    /**
     * 查询微信端首页的新客户、沟通中、 订单、其它
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/query")
    public Object getCustomersByConditions(@RequestParam("type") String type,
                                           @RequestParam(value = "keywork", defaultValue = "") String keywork,
                                           @RequestParam(value = "limit", defaultValue = "10") int limit,
                                           @RequestParam(value = "offset", defaultValue = "0") int offset) {
        if (type == null || "".equals(StringUtils.trim(type))) {
            return StatusDto.buildFailure("类型不能为空");
        }

        // 取得当前用户号也就是跟单员
        String mechandiser = WebUtils.getLoggedUser().getUsername();

        List<String> typeList = new ArrayList<String >();

        // 新客户
        if (type.equals("NEWCUSTOMER")) {
            typeList.add(CustomerStatus.NEWCUSTOMER.name());
        }
        // 沟通中
        else if (type.equals("TALKING")) {
            typeList.add(CustomerStatus.TALKING.name());
            typeList.add(CustomerStatus.TALKSUCCESS.name());
            typeList.add(CustomerStatus.INTOSHOP.name());
        }
        // 订单
        else if (type.equals("ORDER")) {
            typeList.add(CustomerStatus.ORDERSUCCESS.name());
        }
        // 其它
        else if (type.equals("OTHER")) {
            typeList.add(CustomerStatus.BACKORDER.name());
            typeList.add(CustomerStatus.INVALID.name());
            typeList.add(CustomerStatus.FREEZE.name());
        }
        // 最近联系人
        else if (type.equals("CONTACT")) {
            List<Map> list = taskDistributeService.recentContact(WebUtils.getLoggedUserId());
            return StatusDto.buildSuccess(list);
        }
        // 搜索
        else if (type.equals("SEARCH")) {
            Map<String, List> map = new HashMap<String, List>();
            // 新客户
            typeList.add(CustomerStatus.NEWCUSTOMER.name());
            map.put("NEWCUSTOMER", taskDistributeService.getCustomersTask(mechandiser, typeList, keywork));

            typeList = new ArrayList<String>();
            typeList.add(CustomerStatus.TALKING.name());
            typeList.add(CustomerStatus.TALKSUCCESS.name());
            typeList.add(CustomerStatus.INTOSHOP.name());
            map.put("TALKING", taskDistributeService.getCustomersTask(mechandiser, typeList, keywork));

            typeList = new ArrayList<String>();
            typeList.add(CustomerStatus.ORDERSUCCESS.name());
            map.put("ORDER", taskDistributeService.getCustomersTask(mechandiser, typeList, keywork));

            typeList = new ArrayList<String>();
            typeList.add(CustomerStatus.BACKORDER.name());
            typeList.add(CustomerStatus.INVALID.name());
            typeList.add(CustomerStatus.FREEZE.name());
            map.put("OTHER", taskDistributeService.getCustomersTask(mechandiser, typeList, keywork));

            return StatusDto.buildSuccess(map);
        }

        if (typeList.size() == 0) {
            return StatusDto.buildFailure("类型不正确");
        }

        List<Map> list = taskDistributeService.getCustomersTask(mechandiser, typeList, keywork);
        return StatusDto.buildSuccess(list);
    }

    /**
     * 查询微信端首页的新客户、沟通中、 订单、其它
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/queryForPage")
    public Object getCustomersByConditionsForPage(@RequestParam("type") String type,
                                           @RequestParam(value = "keywork", defaultValue = "") String keywork,
                                           @RequestParam(value = "limit", defaultValue = "10") int limit,
                                           @RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex) {
        if (type == null || "".equals(StringUtils.trim(type))) {
            return StatusDto.buildFailure("类型不能为空");
        }

        // 取得当前用户号也就是跟单员
        String mechandiser = WebUtils.getLoggedUser().getUsername();

        List<String> typeList = new ArrayList<String >();

        // 新客户
        if (type.equals("NEWCUSTOMER")) {
            typeList.add(CustomerStatus.NEWCUSTOMER.name());
        }
        // 沟通中
        else if (type.equals("TALKING")) {
            typeList.add(CustomerStatus.TALKING.name());
            typeList.add(CustomerStatus.TALKSUCCESS.name());
            typeList.add(CustomerStatus.INTOSHOP.name());
        }
        // 订单
        else if (type.equals("ORDER")) {
            typeList.add(CustomerStatus.ORDERSUCCESS.name());
        }
        // 其它
        else if (type.equals("OTHER")) {
            typeList.add(CustomerStatus.BACKORDER.name());
            typeList.add(CustomerStatus.INVALID.name());
            typeList.add(CustomerStatus.FREEZE.name());
        }
        // 最近联系人
        else if (type.equals("CONTACT")) {
            List<Map> list = taskDistributeService.recentContact(WebUtils.getLoggedUserId());
            return StatusDto.buildSuccess(list);
        }
        // 搜索
        else if (type.equals("SEARCH")) {
            Map<String, List> map = new HashMap<String, List>();
            // 新客户
            typeList.add(CustomerStatus.NEWCUSTOMER.name());
            map.put("NEWCUSTOMER", taskDistributeService.getCustomersTask(mechandiser, typeList, keywork));

            typeList = new ArrayList<String>();
            typeList.add(CustomerStatus.TALKING.name());
            typeList.add(CustomerStatus.TALKSUCCESS.name());
            typeList.add(CustomerStatus.INTOSHOP.name());
            map.put("TALKING", taskDistributeService.getCustomersTask(mechandiser, typeList, keywork));

            typeList = new ArrayList<String>();
            typeList.add(CustomerStatus.ORDERSUCCESS.name());
            map.put("ORDER", taskDistributeService.getCustomersTask(mechandiser, typeList, keywork));

            typeList = new ArrayList<String>();
            typeList.add(CustomerStatus.BACKORDER.name());
            typeList.add(CustomerStatus.INVALID.name());
            typeList.add(CustomerStatus.FREEZE.name());
            map.put("OTHER", taskDistributeService.getCustomersTask(mechandiser, typeList, keywork));

            return StatusDto.buildSuccess(map);
        }

        if (typeList.size() == 0) {
            return StatusDto.buildFailure("类型不正确");
        }

        PageTable page = taskDistributeService.getCustomersTaskForPage(mechandiser, typeList, keywork, limit, pageIndex);
        return StatusDto.buildSuccess(page);
    }

    /**
     * 插入/更新 沟通记录
     * @param communicate 沟通记录对象
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/communicate")
    public Object handleCommunicate(@RequestBody Communicate communicate) throws Exception {
        // 校验客户编号
        if (communicate.getCustomerNo() == null || "".equals(StringUtils.trim(communicate.getCustomerNo()))) {
            return StatusDto.buildFailure("缺少客户编号");
        }
        // 校验任务编号
        if (communicate.getTaskNo() == null || "".equals(StringUtils.trim(communicate.getTaskNo()))) {
            return StatusDto.buildFailure("缺少任务编号");
        }

        return customerService.handleCommunicate(communicate);
    }

    /**
     * 根据任务号查询该任务的沟通记录列表
     * @param taskNo 任务号
     * @return
     */
    @RequestMapping(value = "/findByTaskNo")
    public Object findByTaskNo(@RequestParam("taskNo") String taskNo) {
        if(taskNo == null || "".equals(StringUtils.trim(taskNo))) {
            return StatusDto.buildFailure("任务号不能为空");
        }

        return StatusDto.buildSuccess(communicateService.findByTaskNo(taskNo));
    }

    /**
     * 插入/更新 房屋信息
     * @param customerHouse 前端传来的房屋信息
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/house")
    public Object handleCustomerHouse(@RequestBody CustomerHouse customerHouse) {
        String resultStr = "";
        ShiroUser user = WebUtils.getLoggedUser();
        // 根据房屋Id是否存在判断是房屋添加还是房屋更新
        // 添加
        if(customerHouse.getId() == null) {
            customerHouse.setCreateUser(user.getId());
            customerHouse.setCreateTime(new Date());
            customerHouseService.insert(customerHouse);
            resultStr = "添加成功";
        }
        // 更新
        else {
            customerHouseService.update(customerHouse);
            resultStr = "更新成功";
        }
        return StatusDto.buildSuccess(resultStr);
    }

    /**
     * 根据房屋Id获取房屋详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/housedetail")
    public Object getHouseDetail(@RequestParam("id") long id) {
        return StatusDto.buildSuccess(customerHouseService.getById(id));
    }

    /**
     * 根据任务编号查找房屋列表信息
     * @param taskNo 任务编号
     * @return 房屋列表
     */
    @RequestMapping(value = "/houseListByTaskNo")
    public Object findHouseDetail(@RequestParam("taskNo") String taskNo) {
        if(taskNo == null || "".equals(StringUtils.trim(taskNo))) {
            return StatusDto.buildFailure("任务编号不能为空");
        }
        return StatusDto.buildSuccess(customerHouseService.findByTaskNo(taskNo));
    }

    @RequestMapping(value = "/orderListWithHouse")
    public Object findHouseHasOrder(@RequestParam("taskNo") String taskNo) {
        if(taskNo == null || "".equals(StringUtils.trim(taskNo))) {
            return StatusDto.buildFailure("任务编号不能为空");
        }
        return StatusDto.buildSuccess(customerHouseService.findOrderListByTaskNo(taskNo));
    }

    /**
	 * 带分页的条件查询: 房屋信息列表
	 */
	@RequestMapping("/customerHouse/list")
	public Object customerHouseList(
			@RequestParam(value = "customerNo", required = false) String customerNo,
			@RequestParam(required = false, defaultValue = "0") Integer offset,
			@RequestParam(required = false, defaultValue = "20") Integer limit,
			@RequestParam(defaultValue = "id") String orderColumn,
			@RequestParam(defaultValue = "DESC") String orderSort) {

		Map<String, Object> params = Maps.newHashMap();
		MapUtils.putNotNull(params, "customerNo", customerNo);
		params.put("sort", new Sort(Sort.Direction.valueOf(orderSort), orderColumn));

		PageTable page = customerHouseService.searchScrollPage(params, new Pagination(offset, limit));

		return StatusDto.buildSuccess(page);
	}
	
	/**
	 * 带分页的条件查询: 客户列表
	 */
	@RequestMapping("/list")
	public Object customerList(
			@RequestParam(value = "store", required = false) String store,
			@RequestParam(value = "dataSource", required = false) String dataSource,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(required = false, defaultValue = "0") Integer offset,
			@RequestParam(required = false, defaultValue = "20") Integer limit,
			@RequestParam(defaultValue = "id") String orderColumn,
			@RequestParam(defaultValue = "DESC") String orderSort) {

		Map<String, Object> params = Maps.newHashMap();
		MapUtils.putNotNull(params, "keyword", keyword);
		MapUtils.putNotNull(params, "store", store);
		MapUtils.putNotNull(params, "dataSource", dataSource);
		params.put("sort", new Sort(Sort.Direction.valueOf(orderSort), orderColumn));

		PageTable page = customerService.searchScrollPage(params, new Pagination(offset, limit));

		return StatusDto.buildSuccess(page);
	}
	
	/**
     * 根据客户编号/订单号数组/创建者  查询订单列表(请求其他系统地址)
     * 	订单系统需要三个参数:
     * 		1.客户id:custIdInOrder
     * 		2.该客户编号下的所有订单数组:orderNum (房屋表)
     * 		3.创建者:createUser 当前登录用户
     * @param customerNo 客户编号
     * @param custIdInOrder 客户id
     */
    @RequestMapping(value = "/findOrderListBycustomerNo")
    public Object findOrderListBycustomerNo( String customerNo, 
    		@RequestParam(required=false) String custIdInOrder ) {
        if(customerNo == null || custIdInOrder == null || "".equals(StringUtils.trim(customerNo))
        		|| "".equals(StringUtils.trim(custIdInOrder)) ) {
            return StatusDto.buildFailure("客户编号或客户id不能为空!");
        }
        
        List mapListJson = new ArrayList();
        try {
	        //订单号  字符串拼接
            String orderNumsStr = "";
	        List<String> orderNums = new ArrayList<>();
	        List<String> orderUuids = new ArrayList<>();
	        //通过客户编号去查询房屋表下  所有订单号orderNum 和创建者
	        List<CustomerHouse> customerHouses = customerHouseService.findCustomerHouseByCustomerNo(customerNo);
	        if(customerHouses != null && customerHouses.size() > 0){
	        	for (CustomerHouse customerHouse : customerHouses) {
                    orderNums.add(customerHouse.getOrderNum());
                    orderUuids.add(customerHouse.getOrderId());
				}
	        }
            orderNumsStr = StringUtils.join(orderNums,",");
	        PageTable page = new PageTable(new ArrayList(), 0);
	        if(StringUtils.isBlank(orderNumsStr)){
	        	//客户没有订单信息
	        	return StatusDto.buildSuccess(page);
	        }
            //todo 新版接口——crm后台查询客户订单列表 queryOrdersInf
            String retMessage = "";
            if(CrmApiUtil.shouldUseOldApi()) {
                Map<String, String> reqData = new HashMap<String, String>();
                reqData.put("cusIdArrStr", custIdInOrder);
                reqData.put("ordNoArrStr", orderNumsStr);
                retMessage = CrmApiUtil.post(PropertyHolder.getCrmApiUrl() + "/api/ord/simpOrds", reqData);
            }
            else{
                retMessage = newApiDataService.queryOrdersInfo(StringUtils.join(orderUuids,","));
            }
	        Map<String, Object> jsonMap = JsonUtils.fromJsonAsMap(retMessage, String.class, Object.class);
			if (jsonMap.get("code") == null || "0".equals(jsonMap.get("code").toString())) {
				return StatusDto.buildSuccess(page);
			}
			//将返回结果转为JSON对象
			JSONObject jsonObject = new JSONObject(retMessage);
			String data = jsonObject.get("data") + "";
			
			//将json数组转为集合
			mapListJson = JsonUtils.fromJsonAsList(data, Object.class);
	        
        } catch (Exception e) {
			e.printStackTrace();
		}
        
        PageTable page = new PageTable(mapListJson, mapListJson.size());
        return StatusDto.buildSuccess(page);
    }
    
    /**
	 * 通过CustomerNo查询 客户信息
	 */
	@RequestMapping("/getCustomerByCustNo")
	public Object getCustomerByCustomerNo(String customerNo, String taskNo) {
	    if (customerNo == null || "".equals(StringUtils.trim(customerNo))) {
	        return StatusDto.buildFailure("缺少客户编号");
        }

        if (taskNo == null || "".equals(StringUtils.trim(taskNo))) {
	        return StatusDto.buildFailure("缺少任务编号");
        }

		Customer customer = customerService.getCustomerByCustomerNoAndTaskNo(customerNo, taskNo);
		return StatusDto.buildSuccess(customer);
	}
	/**
	 * 通过customerNo 更新客户信息
	 */
	@RequestMapping("/updateCustomer")
	public Object updateCustomerByCustomerNo(@RequestBody Customer customer) {
		if(StringUtils.isBlank(customer.getCustomerNo())){
			return StatusDto.buildFailure("客户编码不能为空!");
		}

		boolean result = customerService.UpdateCustomerByCustomerNo(customer);

		if(result){
			return StatusDto.buildSuccess("编辑客户信息成功!");
		}
		return StatusDto.buildFailure("编辑客户信息失败!");
	}


    /**
     * 通过CustomerNo查询 客户信息
     */
    @RequestMapping("/getCustomerByCustNoPc")
    public Object getCustomerByCustomerNoPc(String customerNo) {
        if (customerNo == null || "".equals(StringUtils.trim(customerNo))) {
            return StatusDto.buildFailure("缺少客户编号");
        }

        Customer customer = customerService.getCustomerByCustomerNo(customerNo);
        return StatusDto.buildSuccess(customer);
    }

    /**
     * 根据房屋ID删除相应的房屋数据
     * @param id 房屋ID
     * @return
     */
	@RequestMapping(value = "/deletehouse/{id}")
	public Object deleteHouseById(@PathVariable(value = "id") long id) {
	    customerHouseService.deleteById(id);
	    return StatusDto.buildSuccess("删除成功");
    }

    // Title：无效客户 *** 申请 内容： 经沟通客户 *** 为无效客户！

    /**
     * 客户无效申请
     * @param customerNo 客户编号
     * @param taskNo 任务编号
     * @return
     */
    @RequestMapping(value = "/invalidateuserapply")
    public Object invalidateUser(@RequestParam("customerNo") String customerNo, @RequestParam("taskNo") String taskNo) {
        if (customerNo == null || "".equals(StringUtils.trim(customerNo))) {
            return StatusDto.buildFailure("客户编号不能为空");
        }
        if (taskNo == null || "".equals(StringUtils.trim(taskNo))) {
            return StatusDto.buildFailure("任务编号不能为空");
        }

        invalidateUserApplyService.invalidateUserApply(customerNo, taskNo);
	    return StatusDto.buildSuccess("申请成功");
    }
}
