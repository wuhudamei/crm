package com.rocoinfo.service.schedule;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.entity.customer.InvalidateUserApply;
import com.rocoinfo.entity.customer.ReturnOrder;
import com.rocoinfo.entity.employee.Employee;
import com.rocoinfo.entity.order.ReturnBackMoneyApply;
import com.rocoinfo.entity.task.TaskDistribute;
import com.rocoinfo.enumeration.ApplyResult;
import com.rocoinfo.enumeration.HouseStatus;
import com.rocoinfo.enumeration.SwapOneAndZero;
import com.rocoinfo.service.customer.CustomerHouseService;
import com.rocoinfo.service.customer.InvalidateUserApplyService;
import com.rocoinfo.service.customer.ReturnOrderService;
import com.rocoinfo.service.employee.EmployeeService;
import com.rocoinfo.service.order.OrderService;
import com.rocoinfo.service.task.TaskDistributeService;
import com.rocoinfo.utils.CrmApiUtil;
import com.rocoinfo.utils.DateUtils;

import com.rocoinfo.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.entity.schedule.Schedule;
import com.rocoinfo.repository.schedule.ScheduleDao;
import org.springframework.transaction.annotation.Transactional;

/**
 * <dl>
 * <dd>Description: 美得你crm 日程 service</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/7</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@SuppressWarnings("all")
@Service
public class ScheduleService extends CrudService<ScheduleDao, Schedule> {

    /**
     * 无效客户申请服务
     */
    @Autowired
    private InvalidateUserApplyService invalidateUserApplyService;

    /**
     * 任务派发表
     */
    @Autowired
    private TaskDistributeService taskDistributeService;

    /**
     * 退单服务
     */
    @Autowired
    private ReturnOrderService returnOrderService;

    /**
     * 房屋服务
     */
    @Autowired
    private CustomerHouseService customerHouseService;

    /**
     * 订单服务
     */
    @Autowired
    private OrderService orderService;

    @Autowired
    private EmployeeService employeeService;

    /**
     * 查询数据
     * @param map 包含日期和客服编号
     * @return 日程列表
     */
    public List<Schedule> search(Map<String, Object> map) {
        return this.entityDao.search(map);
    }
    
    /**
     * 根据日期查询日程信息
     * @param scheduleTime	日程时间 yyyy-MM-dd
     * @return
     */
    public List<Schedule> findScheduleByDate(Date scheduleTime){
    	String date = DateUtils.format(new Date(), DateUtils.DATE_SMALL_STR);
    	if(scheduleTime != null){
    		date = DateUtils.format(scheduleTime, DateUtils.DATE_SMALL_STR);
    	}
    	return this.entityDao.findScheduleByDate(date);
    }

    /**
     * 无效客户申请审批通过
     * @param id 申请ID
     * @param taskNo 任务编号
     * @return
     */
    @Transactional
    public StatusDto handleInvalidateAgree(long id, String taskNo) {
        // 处理无效客户申请表
        InvalidateUserApply invalidateUserApply = new InvalidateUserApply();
        invalidateUserApply.setId(id);
        invalidateUserApply.setApprovalResult(ApplyResult.AGREE.getLabel());
        invalidateUserApply.setApprovalTime(new Date());
        invalidateUserApplyService.update(invalidateUserApply);

        // 处理任务派发表 冻结状态修改为无效状态
//        updateTaskDistribute(taskNo, SwapOneAndZero.ZERO.getLabel());

        return StatusDto.buildSuccess("审批成功");
    }

    /**
     * 无效客户申请转发
     * @param id 审批ID
     * @param taskNo
     * @return
     */
    @Transactional
    public StatusDto handleInvalidateRefuse(long id, String taskNo) {
        // 处理无效客户申请表
        InvalidateUserApply invalidateUserApply = new InvalidateUserApply();
        invalidateUserApply.setId(id);
        invalidateUserApply.setApprovalResult(ApplyResult.FORWARDING.getLabel());
        invalidateUserApply.setApprovalTime(new Date());
        invalidateUserApplyService.update(invalidateUserApply);

        // 处理任务派发表 任务转为正常
        updateTaskDistribute(taskNo, SwapOneAndZero.ONE.getLabel());
        return StatusDto.buildSuccess("转派成功");
    }

    /**
     * 拒绝退单申请
     * @param id 申请ID
     * @param taskNo 任务编号
     * @return
     */
    @Transactional
    public StatusDto handleReturnOrderRefuse(long id, String taskNo, String orderId) {
        // 退单状态修改为拒绝
        updateReturnOrderResult(id, ApplyResult.REFUSE.getLabel());
        // 任务派发状态修改为有效
//        updateTaskDistribute(taskNo, SwapOneAndZero.ONE.getLabel());
        // 房屋订单状态修改为正常
        customerHouseService.updateOrderStatusByOrderId(HouseStatus.NORMAL.getIndex(), orderId);

        return StatusDto.buildSuccess("拒绝成功");
    }

    /**
     * 通过退单申请
     * @param id 申请ID
     * @param taskNo 任务编号
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public StatusDto handleReturnOrderAgree(long id, String taskNo, String orderId) throws Exception {
        // 退单申请状态修改为同意
        updateReturnOrderResult(id, ApplyResult.AGREE.getLabel());

        // 房屋订单状态修改为审批通过
        customerHouseService.updateOrderStatusByOrderId(HouseStatus.APPLYAGREE.getIndex(), orderId);

        // 修改订单状态
        // 根据ID获取退单详情
        ReturnOrder returnOrder = returnOrderService.getById(id);
        Employee employee = employeeService.getOneByJobNum(returnOrder.getApplyUser());

        ReturnBackMoneyApply returnBackMoneyApply = new ReturnBackMoneyApply();
        // 客户在订单系统的ID
        returnBackMoneyApply.setCustomerIdInOrd(taskDistributeService.findCustomerIdInOrdByTaskNo(taskNo));
        // 订单ID
        returnBackMoneyApply.setOrderId(returnOrder.getOrderId());
        // 申请人
        returnBackMoneyApply.setApplyerId(employee.getEmpIdinord());
        // 申请人名
        returnBackMoneyApply.setApplyerName(employee.getEmpName());
        // 申请人类型
        returnBackMoneyApply.setApplyerType("KF");
        // 退款类型
        returnBackMoneyApply.setReturnBackType("1");
        // 退单原因
        returnBackMoneyApply.setCancelOrderReson(returnOrder.getBackReason());
        // 应扣除的金额
        returnBackMoneyApply.setDeductMoneyAmount(BigDecimal.valueOf(returnOrder.getDebitAmount()));
        // 应退的金额
        returnBackMoneyApply.setReturnMoneyAmount(BigDecimal.valueOf(returnOrder.getAmount()));

        orderService.returnBackMoney(returnBackMoneyApply);

        return StatusDto.buildSuccess("通过成功");
    }

    /**
     * 根据ID修改退单申请的审批结果
     * @param id 申请Id
     * @param approvalResult 审批结果 通过  拒绝
     */
    private void updateReturnOrderResult(long id, String approvalResult) {
        ReturnOrder returnOrder = new ReturnOrder();
        returnOrder.setId(id);

        // 审批人
        returnOrder.setApprovalUser(WebUtils.getLoggedUser().getUsername());

        returnOrder.setApprovalResult(approvalResult);
        returnOrder.setApprovalTime(new Date());
        returnOrderService.update(returnOrder);
    }

    /**
     * 修改任务派发的状态
     * @param taskNo 任务编号
     * @param status 状态  有效 无效 冻结
     */
    private void updateTaskDistribute(String taskNo, String status) {
        TaskDistribute taskDistribute = new TaskDistribute();
        taskDistribute.setTaskNo(taskNo);
        taskDistribute.setStatus(status);
        taskDistributeService.updateByIdOrTaskNo(taskDistribute);
    }

    /**
     * 删除该任务该客服当天以后的日程提醒
     * @param jobNum
     * @param taskNo
     * @param date
     */
    @Transactional
    public void deleteAfterNow(String jobNum, String taskNo, Date date) {
        this.entityDao.deleteAfterNow(jobNum, taskNo, date);
    }
}
