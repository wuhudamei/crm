package com.rocoinfo.rest.employee;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rocoinfo.common.BaseController;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.entity.employee.EmployeeDataPermission;
import com.rocoinfo.entity.employee.EmployeeOrderSource;
import com.rocoinfo.service.employee.EmployeeDataPermissionService;
import com.rocoinfo.service.employee.EmployeeOrderSourceService;
import com.rocoinfo.shiro.ShiroUser;
import com.rocoinfo.utils.JsonUtils;
import com.rocoinfo.utils.MapUtils;
import com.rocoinfo.utils.WebUtils;


/**
 * <dl>
 * <dd>Description: 美得你crm 用户接单来源 controller</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/5</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@RestController
@RequestMapping(value = "/api/orderSource")
public class EmployeeOrderSourceController extends BaseController {
    @Autowired
    EmployeeOrderSourceService employeeOrderSourceService;

    @Autowired
    private EmployeeDataPermissionService employeeDataPermissionService;
    
    /**
     * 根据用户编号获取接单来源列表
     *	查数据来源表
     * @param jobNum 用户编号
     * @param dicCode 用户编号
     * @return
     */
    @RequestMapping("/list")
    public Object orderSourceList(@RequestParam(value = "jobNum", required = false) String jobNum,
                       @RequestParam(value = "dicCode", required = false) String dicCode) {
        //获取列表 查数据来源表
        List<EmployeeOrderSource> list = employeeOrderSourceService.findDataOrderSourceByEmpJobNumWithDataSource(jobNum);
        Map<String, List<EmployeeOrderSource>> sjly = MapUtils.of("接单来源", list);
        return StatusDto.buildSuccess("success", sjly);
    }

    /**
     * 根据用户编号修改用户的接单来源
     *
     * @param jobNum      用户编号
     * @param orderSources 接单来源
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateList(@RequestParam(value = "jobNum", required = false) String jobNum, @RequestParam("orderSources") String orderSources) {
        List<EmployeeOrderSource> employeeOrderSources = JsonUtils.fromJsonAsList(orderSources, EmployeeOrderSource.class);
        if (jobNum == null) {
            return StatusDto.buildFailure("员工编号为空！");
        }
        Date date = new Date();
        ShiroUser loggedUser = WebUtils.getLoggedUser();
        return employeeOrderSourceService.insertOrderSource(date, loggedUser.getId(), jobNum, employeeOrderSources);
    }




}
