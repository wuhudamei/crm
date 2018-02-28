package com.rocoinfo.service.customer;

import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.entity.customer.ReturnOrder;
import com.rocoinfo.entity.employee.Employee;
import com.rocoinfo.entity.order.ReturnBackMoneyApply;
import com.rocoinfo.entity.task.TaskDistribute;
import com.rocoinfo.enumeration.ApplyResult;
import com.rocoinfo.enumeration.HouseStatus;
import com.rocoinfo.enumeration.TaskDistributeStatus;
import com.rocoinfo.repository.customer.ReturnOrderDao;
import com.rocoinfo.service.employee.EmployeeService;
import com.rocoinfo.service.order.OrderService;
import com.rocoinfo.service.task.TaskDistributeService;
import com.rocoinfo.shiro.ShiroUser;
import com.rocoinfo.utils.GenerateCodeUtil;
import com.rocoinfo.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 描述：订单无效申请
 *
 * @author tony
 * @创建时间 2017-06-13 17:18
 */
@SuppressWarnings("all")
@Service
public class ReturnOrderService extends CrudService<ReturnOrderDao, ReturnOrder> {

    /**
     * 任务派发服务
     */
    @Autowired
    private TaskDistributeService taskDistributeService;

    /**
     * 员工服务
     */
    @Autowired
    private EmployeeService employeeService;

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

    /**
     * 根据退单申请编号查询退单详情
     * @param applyNo 申请编号
     * @return
     */
    public ReturnOrder findDetailByApplyNo(String applyNo) {
        return this.entityDao.findDetailByApplyNo(applyNo);
    }

    @Transactional
    public StatusDto handleReturnOrder(ReturnOrder returnOrder) throws Exception {
        ShiroUser shiroUser = WebUtils.getLoggedUser();
        // 该订单退订的时候提交给相应的上级人员，存岗位
        // 2017-07-07 修改为 退单申请的时候不指定上级岗位，查询审批列表的时候查找登录人的下级员工提交的申请
//        Employee employee = employeeService.getOneByJobNum(shiroUser.getParentId());
//        if (shiroUser.getParentId() == null
//                || "".equals(StringUtils.trim(shiroUser.getParentId()))
//                || employee == null) {
//            return StatusDto.buildFailure("请先指定上级领导");
//        }

        // 申请编号
        returnOrder.setApplyNo(GenerateCodeUtil.generateInvalidateApplyCode());
        // 需要从当前登录人获取
        returnOrder.setApplyUser(shiroUser.getUsername());

        returnOrder.setApplyTime(new Date());
        returnOrder.setApplyTitle("退单申请");

//        returnOrder.setApprovalUser(employee.getPosition());

        int status = HouseStatus.APPLYING.getIndex();

        // 如果金额太小，则直接设置审批时间为当前时间，不需要上级人员审批
        if (returnOrder.getAllotState() == 0) {
            ReturnBackMoneyApply returnBackMoneyApply = new ReturnBackMoneyApply();
            returnBackMoneyApply.setCustomerIdInOrd(taskDistributeService.findCustomerIdInOrdByTaskNo(returnOrder.getTaskNo()));
            // 订单ID
            returnBackMoneyApply.setOrderId(returnOrder.getOrderId());
            // 申请人
            returnBackMoneyApply.setApplyerId(shiroUser.getEmpIdinord());
            // 申请人名
            returnBackMoneyApply.setApplyerName(shiroUser.getName());
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

            // 审批结果为通过
            returnOrder.setApprovalResult(ApplyResult.AGREE.getLabel());
            // 审批时间为当前申请时间
            returnOrder.setApprovalTime(new Date());

            status = HouseStatus.APPLYAGREE.getIndex();
        }

        this.entityDao.insert(returnOrder);

        // 房屋状态变成退订申请中
        customerHouseService.updateOrderStatusByOrderId(status, returnOrder.getOrderId());

        return StatusDto.buildSuccess("退单申请成功");
    }
}
