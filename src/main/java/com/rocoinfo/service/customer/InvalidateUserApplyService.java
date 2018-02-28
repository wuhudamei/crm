package com.rocoinfo.service.customer;

import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.entity.customer.Customer;
import com.rocoinfo.entity.customer.InvalidateUserApply;
import com.rocoinfo.entity.task.TaskDistribute;
import com.rocoinfo.enumeration.TaskDistributeStatus;
import com.rocoinfo.repository.customer.InvalidateUserApplyDao;
import com.rocoinfo.service.task.TaskDistributeService;
import com.rocoinfo.utils.GenerateCodeUtil;
import com.rocoinfo.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 描述：无效客户申请
 *
 * @author tony
 * @创建时间 2017-06-13 14:58
 */
@SuppressWarnings("all")
@Service
public class InvalidateUserApplyService extends CrudService<InvalidateUserApplyDao, InvalidateUserApply> {

    /**
     * 客户服务
     */
    @Autowired
    private CustomerService customerService;

    /**
     * 任务派发任务
     */
    @Autowired
    private TaskDistributeService taskDistributeService;

    /**
     * 查找待审批列表数据
     * @param map
     *   {
     *       mechandiser 跟单号
     *       position 岗位
     *   }
     * @return 待审批列表
     */
    public List<Map> searchApplys(Map map) {
        return this.entityDao.searchApplys(map);
    }

    /**
     * 根据申请申请查询申请详情
     * @param applyNo 申请编号
     * @return
     */
    public InvalidateUserApply findDetailByApplyNo(String applyNo) {
        return this.entityDao.findDetailByApplyNo(applyNo);
    }

    /**
     * 无效客户申请
     * @param customerNo
     * @param taskNo
     */
    @Transactional
    public void invalidateUserApply(String customerNo, String taskNo) {
        // 冻结任务
        TaskDistribute taskDistribute = new TaskDistribute();
        taskDistribute.setTaskNo(taskNo);
        taskDistribute.setStatus(TaskDistributeStatus.FREEZE.getIndex() + "");
        taskDistributeService.updateByIdOrTaskNo(taskDistribute);

        // 添加申请
        InvalidateUserApply invalidateUserApply = new InvalidateUserApply();
        Customer customer = customerService.getCustomerByCustomerNo(customerNo);
        invalidateUserApply.setApplyNo(GenerateCodeUtil.generateInvalidateApplyCode());
        invalidateUserApply.setApplyTitle("无效客户 " + customer.getCustomerName() + "申请");
        invalidateUserApply.setTaskNo(taskNo);
        invalidateUserApply.setCustomerNo(customerNo);
        invalidateUserApply.setApplyReson("经沟通 " + customer.getCustomerName() + " 为无效客户");
        invalidateUserApply.setApplyUser(WebUtils.getLoggedUser().getUsername());
        invalidateUserApply.setApprovalUser(WebUtils.getLoggedUser().getParentId());
        invalidateUserApply.setApplyTime(new Date());
        this.entityDao.insert(invalidateUserApply);
    }
}
