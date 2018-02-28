package com.rocoinfo.service.customer;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rocoinfo.entity.order.Order;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.dto.page.PageTable;
import com.rocoinfo.dto.page.Pagination;
import com.rocoinfo.entity.customer.Communicate;
import com.rocoinfo.entity.customer.Customer;
import com.rocoinfo.entity.customer.CustomerHouse;
import com.rocoinfo.entity.customer.CustomerTaskRmk;
import com.rocoinfo.entity.customer.InvalidateUserApply;
import com.rocoinfo.entity.order.OrdPlaceOrder;
import com.rocoinfo.entity.order.OrderDto;
import com.rocoinfo.entity.schedule.Schedule;
import com.rocoinfo.entity.task.TaskDistribute;
import com.rocoinfo.enumeration.CustomerStatus;
import com.rocoinfo.enumeration.ScheduleSourceType;
import com.rocoinfo.enumeration.SwapOneAndZero;
import com.rocoinfo.enumeration.TaskDistributeStatus;
import com.rocoinfo.repository.customer.CustomerDao;
import com.rocoinfo.service.order.OrderService;
import com.rocoinfo.service.schedule.ScheduleService;
import com.rocoinfo.service.task.TaskDistributeService;
import com.rocoinfo.shiro.ShiroUser;
import com.rocoinfo.utils.GenerateCodeUtil;
import com.rocoinfo.utils.WebUtils;

/**
 * 描述：客户相关
 *
 * @author tony
 * 2017-06-07 14:28
 */
@SuppressWarnings("all")
@Service
public class CustomerService extends CrudService<CustomerDao, Customer>{

    /**
     * 沟通记录服务
     */
    @Autowired
    private CommunicateService communicateService;

    /**
     * 任务轨迹
     */
    @Autowired
    private CustomerTaskRmkService customerTaskRmkService;

    /**
     * 无效客户申请服务
     */
    @Autowired
    private InvalidateUserApplyService invalidateUserApplyService;

    /**
     * 任务派发服务
     */
    @Autowired
    private TaskDistributeService taskDistributeService;

    /**
     * 日程服务
     */
    @Autowired
    private ScheduleService scheduleService;

    /**
     * 订单服务
     */
    @Autowired
    private OrderService orderService;

    /**
     * 房屋服务
     */
    @Autowired
    private CustomerHouseService customerHouseService;
    
    //客户dao
    @Autowired
    private CustomerDao customerDao;

    public List<Customer> getCustomerByConditions(List<String> list) {
        return  this.entityDao.getCustomers(list);
    }

    /**
     * 添加/更新沟通记录
     * @param communicate 沟通记录对象
     * @return 处理结果—
     */
    @Transactional
    public StatusDto handleCommunicate(Communicate communicate) throws Exception {

        String invalidateStr = communicate.getInvalidFlag();

        // 如果是无效客户需要判断一下该任务是否已生成订单，如果已生成订单则不能置为无效客户
        if (SwapOneAndZero.ZERO.getLabel().equals(invalidateStr)) {
            List<CustomerHouse> list = customerHouseService.findOrderListByTaskNo(communicate.getTaskNo());
            if (list.size() > 0) {
                return StatusDto.buildFailure("已生成订单客户不能置为无效");
            }
        }

        String resultStr = "";
        // 如果Id值不为空，则是更新，否则为添加
        // 添加
        if(communicate.getId() == null) {
            // 创建时间
            communicate.setCreateTime(new Date());
            // 创建人
            communicate.setCreateUser(WebUtils.getLoggedUserId().intValue());
            communicateService.insert(communicate);
            resultStr = "添加成功";
        }
        // 更新
        else {
            communicateService.update(communicate);
            resultStr = "修改成功";
        }

        // 如果客户标签不为空，修改客户表的客户标签
        if (communicate.getCustomerTag() != null && !"".equals(StringUtils.trim(communicate.getCustomerTag()))) {
            Customer   customer = new Customer();
            customer.setCustomerNo(communicate.getCustomerNo());
            customer.setCustomerTag(communicate.getCustomerTag());
            this.entityDao.update(customer);
        }



        // 处理任务轨迹
        handlerTaskRmk(communicate);

        // 如果是邀约成功生成邀约码，发短信; 或者如果是无效申请的话，则修改状态为无效; 或者客户级别不为空
        if (CustomerStatus.TALKSUCCESS.name().equals(communicate.getCurrentStatus())
                || SwapOneAndZero.ZERO.getLabel().equals(invalidateStr)
                || (communicate.getCustomerIntention() != null && !"".equals(StringUtils.trim(communicate.getCustomerIntention())))) {
            handlerInvitation(communicate);
        }

        // 如果沟通结果为无效，则还需要发起无效审批申请
        if (SwapOneAndZero.ZERO.getLabel().equals(invalidateStr)) {
            handlerInvalideUserApply(communicate);
        }

        // 如果是有效沟通的话，还需要生成日程
        if (SwapOneAndZero.ONE.getLabel().equals(invalidateStr)) {
            handlerSchedule(communicate);
        }

        return StatusDto.buildSuccess(resultStr);
    }

    /**
     * 添加日程数据
     * @param communicate 沟通记录对象
     */
    private void handlerSchedule(Communicate communicate) throws Exception {
        // 如果量房时间和邀约到店时间都没有的话直接返回，不添加日程
        if (communicate.getAmontRoomTime() == null && communicate.getStoreTime() == null) {
            return;
        }

        // 如果房屋已经生成订单，还需要同步预约量房时间到订单系统
        if (communicate.getAmontRoomTime() != null && communicate.getOrderId() != null && communicate.getOrderId() != "") {
            Order order = new Order();
            order.setOrderNo(communicate.getOrderNum());
            order.setOrderId(communicate.getOrderId());
            OrdPlaceOrder ordPlaceOrder = new OrdPlaceOrder();
            ordPlaceOrder.setOrderId(communicate.getOrderId());
            ordPlaceOrder.setPlanMeasureTime(communicate.getAmontRoomTime());
            OrderDto sendOrder = new OrderDto();
            sendOrder.setOrder(order);
            sendOrder.setPlaceOrder(ordPlaceOrder);
            orderService.createOrder(sendOrder);
        }

        Schedule schedule = new Schedule();
        schedule.setCreateTime(new Date());
        schedule.setGenerateModel(SwapOneAndZero.ZERO.getLabel());
        schedule.setJobNum(WebUtils.getLoggedUser().getUsername());

        // 获取客户数据
        Customer customer = this.entityDao.getCustomerByCustomerNo(communicate.getCustomerNo());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 如果是预约量房
        if (communicate.getAmontRoomTime() != null) {
            schedule.setTitle(customer.getCustomerName() + "日预约量房客户关怀");
            schedule.setScheduleType(ScheduleSourceType.CUSTOMERCARE.name());
            schedule.setContent(customer.getCustomerName() + " 手机：" + customer.getCustomerMobile() + "，预约" + sdf.format(communicate.getAmontRoomTime()) + " 日量房，请做好客户关怀。");

            // 当前时间
            LocalDateTime nowDateTime = LocalDateTime.now();


            // 预约量房时间生成日程逻辑：从量房日期开始向前推，33日一次，45日一次，60日一次，90日一次。其他类型：客户关怀；
            Instant instant = communicate.getAmontRoomTime().toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDateTime amontRoomDateTime = LocalDateTime.ofInstant(instant, zoneId);

            // 量房时间减33天
            LocalDateTime thirtyThreeDays = amontRoomDateTime.minusDays(33);

            // 如果量房时间减33天小于当前时间，则不添加日程
            if (thirtyThreeDays.compareTo(nowDateTime) < 0) {
                return;
            }

            ShiroUser shiroUser = WebUtils.getLoggedUser();
            // 如果量房时间减33天大于当前时间，则删除该任务该客服当天以后的日程提醒
            scheduleService.deleteAfterNow(shiroUser.getUsername(), communicate.getTaskNo(), new Date());

            // 插入 倒数 33 天的日程
            Date ttDays = Date.from(thirtyThreeDays.atZone(ZoneId.systemDefault()).toInstant());

            schedule.setScheduleTime(ttDays);
            scheduleService.insert(schedule);

            // 量房时间减 45 天
            LocalDateTime fourFiveDay = amontRoomDateTime.minusDays(45);
            // 如果量房时间减45天大于当前时间，则添加日程
            if (fourFiveDay.compareTo(nowDateTime) >= 0) {
                ttDays = Date.from(fourFiveDay.atZone(ZoneId.systemDefault()).toInstant());

                schedule.setScheduleTime(ttDays);
                scheduleService.insert(schedule);
            }

            // 量房时间减 60 天
            LocalDateTime sixtyDay = amontRoomDateTime.minusDays(60);
            // 如果量房时间减60天大于当前时间，则添加日程
            if (sixtyDay.compareTo(nowDateTime) >= 0) {
                ttDays = Date.from(sixtyDay.atZone(ZoneId.systemDefault()).toInstant());

                schedule.setScheduleTime(ttDays);
                scheduleService.insert(schedule);
            }

            // 量房时间减 90 天
            LocalDateTime ninetyDay = amontRoomDateTime.minusDays(90);
            // 如果量房时间减90天大于当前时间，则添加日程
            if (ninetyDay.compareTo(nowDateTime) >= 0) {
                ttDays = Date.from(ninetyDay.atZone(ZoneId.systemDefault()).toInstant());

                schedule.setScheduleTime(ttDays);
                scheduleService.insert(schedule);
            }
        }
        // 如果是邀约到店
        else if (communicate.getStoreTime() != null) {
            schedule.setScheduleTime(communicate.getStoreTime());
            schedule.setTitle(customer.getCustomerName() + " 预约到店提醒");
            schedule.setScheduleType(ScheduleSourceType.INVITATIONSTORE.name());
            schedule.setContent(customer.getCustomerName() + ", 手机：" + customer.getCustomerMobile() + ", 预约" + sdf.format(communicate.getStoreTime()) + " 到店，请做好接待准备！");
            scheduleService.insert(schedule);
        }
    }

    /**
     * 添加沟通记录的时候针对任务轨迹的处理
     * @param communicate 沟通记录对象
     */
    private void handlerTaskRmk(Communicate communicate) {
        // 添加任务轨迹
        // 把该任务轨迹中对应的 是否最新记录(0-旧状态,1-新状态)  更新为旧状态
        // 如果当前状态为 生单状态，则该状态一直保持为最新的状态
        Map map = new HashMap();
        map.put("taskNo", communicate.getTaskNo());
        map.put("newFlag", SwapOneAndZero.ONE.getLabel());
        List<CustomerTaskRmk> customerTaskRmks = customerTaskRmkService.search(map);

        String str = SwapOneAndZero.ONE.getLabel();

        // 如果是已生单，则该状态轨迹一直都为最新的
        if (customerTaskRmks != null
                && customerTaskRmks.size() > 0
                && CustomerStatus.ORDERSUCCESS.name().equals(customerTaskRmks.get(0).getCurrentStatus())){
            str = SwapOneAndZero.ZERO.getLabel();
        }
        else {
            CustomerTaskRmk customerTaskRmkUpdate = new CustomerTaskRmk();
            customerTaskRmkUpdate.setTaskNo(communicate.getTaskNo());
            customerTaskRmkUpdate.setNewFlag(SwapOneAndZero.ZERO.getLabel());
            customerTaskRmkService.updateByTaskNo(customerTaskRmkUpdate);
        }

        // 插入一条新的任务轨迹
        CustomerTaskRmk customerTaskRmkInsert = new CustomerTaskRmk();
        customerTaskRmkInsert.setNewFlag(str);
        customerTaskRmkInsert.setCreateTime(new Date());
        customerTaskRmkInsert.setCurrentStatus(communicate.getCurrentStatus());
        customerTaskRmkInsert.setCustomerNo(communicate.getCustomerNo());
        customerTaskRmkInsert.setTaskNo(communicate.getTaskNo());

        TaskDistribute taskDistribute = taskDistributeService.getDistributeTaskByTaskNo(communicate.getTaskNo());
        customerTaskRmkInsert.setMechandiser(taskDistribute.getMechandiser());
        customerTaskRmkInsert.setCreateUser(WebUtils.getLoggedUserId());

        customerTaskRmkService.insert(customerTaskRmkInsert);
    }

    /**
     * 如果邀约成功还需要处理保存邀约码操作
     * @param communicate
     */
    private void handlerInvitation(Communicate communicate) {

        TaskDistribute taskDistribute = new TaskDistribute();
        taskDistribute.setTaskNo(communicate.getTaskNo());
        if (CustomerStatus.TALKSUCCESS.name().equals(communicate.getCurrentStatus())) {

            String generateInvitationCode = GenerateCodeUtil.generateInvitationCode(WebUtils.getLoggedUser().getStoreCode());
            taskDistribute.setInvitationCode(generateInvitationCode);
        }

        // 无效客户申请
        if (SwapOneAndZero.ZERO.getLabel().equals(communicate.getInvalidFlag())) {
            taskDistribute.setStatus(TaskDistributeStatus.INVALID.getIndex() + "");
        }

        // 任务级别
        if (communicate.getCustomerIntention() != null && !"".equals(StringUtils.trim(communicate.getCustomerIntention()))) {
            taskDistribute.setTaskLevel(communicate.getCustomerIntention());
        }

        taskDistributeService.updateByIdOrTaskNo(taskDistribute);

    }

    /**
     * 无效客户申请
     * @param communicate
     */
    private void handlerInvalideUserApply(Communicate communicate) {
        InvalidateUserApply invalidateUserApply = new InvalidateUserApply();
        invalidateUserApply.setApplyNo(GenerateCodeUtil.generateBackOrder());
        invalidateUserApply.setApplyTitle("无效客户申请");
        invalidateUserApply.setTaskNo(communicate.getTaskNo());
        invalidateUserApply.setCustomerNo(communicate.getCustomerNo());
        invalidateUserApply.setApplyReson(communicate.getRemark());

        // 这个需要从登录人里面获取
        ShiroUser user = WebUtils.getLoggedUser();
        invalidateUserApply.setApplyUser(user.getUsername());
        invalidateUserApply.setApprovalUser(user.getParentId());

        invalidateUserApply.setApplyTime(new Date());

        invalidateUserApplyService.insert(invalidateUserApply);
    }

    /**
     * 通过CustomerNo 和 任务号查询 客户信息
     */
	public Customer getCustomerByCustomerNoAndTaskNo(String customerNo, String taskNo) {
		return customerDao.getCustomerByCustomerNoAndTaskNo(customerNo, taskNo);
	}

    /**
     * 通过CustomerNo查询 客户信息
     */
    public Customer getCustomerByCustomerNo(String customerNo) {
        return customerDao.getCustomerByCustomerNo(customerNo);
    }

	/**
	 * 通过 客户编码  修改客户信息
	 * @param customer
	 * @return 更新结果
	 */
	public boolean UpdateCustomerByCustomerNo(Customer customer) {
		customer.setUpdateTime(new Date());
		int result = customerDao.update(customer);

		// 如果有任务的数据，也更新任务的级别
        if (customer.getTaskDistribute() != null
                && customer.getTaskDistribute().getTaskNo() != null
                && customer.getTaskDistribute().getTaskLevel() != null) {
            TaskDistribute taskDistribute = new TaskDistribute();
            taskDistribute.setTaskNo(customer.getTaskDistribute().getTaskNo());
            taskDistribute.setTaskLevel(customer.getTaskDistribute().getTaskLevel());
            taskDistributeService.updateByIdOrTaskNo(taskDistribute);
        }

        // 更新订单系统用户数据
        if (customer.getCustomerIdinord() != null) {
            orderService.updateCustomer(customer, customer.getTaskDistribute().getTaskLevel());
        }

		if(result <= 0){
			return false;
		}
		return true;
	}
	
	
	private static final String PAGE_SORT = "sort";
	
	/**
	 * 重写父类查询方法! 对分页对象进行不count设置,手动进行count查询
	 */
	@Override
	public PageTable searchScrollPage(Map<String, Object> params,
			Pagination page) {
		 // 设置排序条件
        params.put(PAGE_SORT, page.getSort());
        // 利用pagehelper分页查询; 此处不进行count查询,会对该sql中的order By产生错误影响!!!
        PageHelper.offsetPage(page.getOffset(), page.getLimit(),false);
        Page result =  (Page) customerDao.search(params);
        //设置总记录
        result.setTotal(customerDao.searchTotal(params));
        // 返回查询结果
        return new PageTable<T>(result.getResult(), result.getTotal());
	}

}
