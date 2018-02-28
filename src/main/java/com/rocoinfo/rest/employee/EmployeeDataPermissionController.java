package com.rocoinfo.rest.employee;

import com.rocoinfo.common.BaseController;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.entity.employee.EmployeeDataPermission;
import com.rocoinfo.service.employee.EmployeeDataPermissionService;
import com.rocoinfo.shiro.ShiroUser;
import com.rocoinfo.utils.JsonUtils;
import com.rocoinfo.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;


/**
 * <dl>
 * <dd>Description: 美得你crm 用户数据权限 controller</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/5</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@RestController
@RequestMapping(value = "/api/dataPermission")
public class EmployeeDataPermissionController extends BaseController {
    @Autowired
    EmployeeDataPermissionService employeeDataPermissionService;
    
    /**
     * 根据用户编号获取数据权限列表
     *	查询数据字典表
     * @param jobNum 用户编号
     * @return
     */
    @RequestMapping("/list")
    public Object list(@RequestParam(value = "jobNum", required = false) String jobNum) {
        //获取列表
        List<EmployeeDataPermission> dataPermissionByEmpId = employeeDataPermissionService.findDataPermissionByEmpId(jobNum);
        //根据类别分组
        Map<String, List<EmployeeDataPermission>> map = dataPermissionByEmpId.stream().collect(groupingBy(EmployeeDataPermission::getModule));
        return StatusDto.buildSuccess("success", map);
    }
    
    /**
     * 根据用户编号获取数据权限列表
     *	查询数据来源表
     * @param jobNum 用户编号
     * @return
     */
    @RequestMapping("/listWithDataSource")
    public Object listWithDataSource(@RequestParam(value = "jobNum", required = false) String jobNum) {
        //获取列表
        List<EmployeeDataPermission> dataPermissionByEmpId = employeeDataPermissionService.findDataPermissionByEmpIdWithDataSource(jobNum);
        //根据类别分组
        Map<String, List<EmployeeDataPermission>> map = dataPermissionByEmpId.stream().collect(groupingBy(EmployeeDataPermission::getModule));
        return StatusDto.buildSuccess("success", map);
    }

    /**
     * 根据用户编号修改用户的数据权限
     *
     * @param jobNum      用户编号
     * @param permissions 用户权限
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object list(@RequestParam(value = "jobNum", required = false) String jobNum, @RequestParam("permissions") String permissions) {
        List<EmployeeDataPermission> employeeDataPermissions = JsonUtils.fromJsonAsList(permissions, EmployeeDataPermission.class);
        if (jobNum == null)
            return StatusDto.buildFailure("员工编号为空！");
        Date date = new Date();
        ShiroUser loggedUser = WebUtils.getLoggedUser();
        return employeeDataPermissionService.insertDataPermission(date, loggedUser.getId(), jobNum, employeeDataPermissions);
    }

    /**
     * 根据当前用户编号和数据权限类型获取权限列表（用于下拉框） 传入编号查询编号下  没有传就查当前人的
     *  查询数据字典表
     * @param permissionType 权限类型
     * @param jobNum 用户编号
     * @return
     */
    @RequestMapping(value = "/empDataPermission", method = RequestMethod.POST)
    public Object empDataPermissionlist(@RequestParam("permissionType") String permissionType,@RequestParam("jobNum") String jobNum) {
        if(StringUtils.isEmpty(jobNum)){
            jobNum=WebUtils.getLoggedUser().getUsername();
        }
        List<EmployeeDataPermission> list = employeeDataPermissionService.findEmpDataPermissionlist(permissionType, jobNum);
        return StatusDto.buildSuccess(list);
    }

    /**
     * 根据当前用户编号和数据权限类型获取权限列表（用于下拉框）
     *  查询数据来源表
     * @param jobNum 用户编号
     * @return
     */
    @RequestMapping(value = "/empDataPermissionWithDataSource", method = RequestMethod.POST)
    public Object empDataPermissionWithDataSource(@RequestParam("jobNum") String jobNum) {
        List<EmployeeDataPermission> list = employeeDataPermissionService.empDataPermissionWithDataSource(jobNum);
        return StatusDto.buildSuccess(list);
    }
    
}
