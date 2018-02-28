package com.rocoinfo.rest.employee;

import com.google.common.collect.Maps;
import com.rocoinfo.common.BaseController;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.dto.page.PageTable;
import com.rocoinfo.dto.page.Pagination;
import com.rocoinfo.dto.page.Sort;
import com.rocoinfo.entity.employee.EmployeeOrderReward;
import com.rocoinfo.service.employee.EmployeeOrderRewardService;
import com.rocoinfo.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;


/**
 * <dl>
 * <dd>Description: 美得你crm 用户奖励 controller</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/26</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@RestController
@RequestMapping(value = "/api/orderReward")
public class EmployeeOrderRewardController extends BaseController {
    @Autowired
    EmployeeOrderRewardService employeeOrderRewardService;

    /**
     * 根据用户编号获取奖励单数列表
     *
     * @param jobNum 用户编号
     * @return
     */
    @RequestMapping("/list")
    public Object list(@RequestParam(value = "jobNum", required = false) String jobNum,
                       @RequestParam(value = "offset", defaultValue = "0") int offset,
                       @RequestParam(value = "limit", defaultValue = "20") int pageSize,
                       @RequestParam(value = "sortOrder", defaultValue = "DESC") String orderSort) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("jobNo", jobNum);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "create_time"));
        PageTable pageTable = employeeOrderRewardService.searchScrollPage(params, new Pagination(offset, pageSize, sort));
        return StatusDto.buildSuccess(pageTable);
    }
    /**
     * 添加奖励
     *
     * @param employeeOrderReward
     * @return 操作情况
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object insert(@RequestBody EmployeeOrderReward employeeOrderReward) {
        employeeOrderReward.setCreateTime(new Date());
        employeeOrderReward.setCreateUser(WebUtils.getLoggedUser().getId());
        employeeOrderRewardService.insert(employeeOrderReward);
        return StatusDto.buildSuccess("操作成功");
    }

    /**
     * 删除奖励
     *
     * @param id
     * @return 操作情况
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object delete(@RequestParam Long id) {
        employeeOrderRewardService.deleteById(id);
        return StatusDto.buildSuccess("操作成功");
    }


}
