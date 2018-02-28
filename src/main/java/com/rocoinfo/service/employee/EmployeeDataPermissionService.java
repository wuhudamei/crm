package com.rocoinfo.service.employee;

import com.rocoinfo.Constants;
import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.entity.employee.Employee;
import com.rocoinfo.entity.employee.EmployeeDataPermission;
import com.rocoinfo.repository.employee.EmployeeDao;
import com.rocoinfo.repository.employee.EmployeeDataPermissionDao;
import com.rocoinfo.weixin.util.CollectionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <dl>
 * <dd>Description: 美得你crm 用户数据权限Service</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/9</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@Service
public class EmployeeDataPermissionService extends CrudService<EmployeeDataPermissionDao, EmployeeDataPermission> {

    @Autowired
    private EmployeeDao employeeDao;

    /**
     * 根据 用户编号 获取相应的数据权限列表
     * 1:查询数据字典表,取出门店数据
     * 2:查询数据来源表,取出数据来源
     *
     * @param jobNum 员工编号
     * @return
     */
    public List<EmployeeDataPermission> findDataPermissionByEmpId(String jobNum) {
        //查询数据字典表
        List<EmployeeDataPermission> listWithDict = entityDao.findDataPermissionByEmpId(jobNum);
        //查询数据来源表
        List<EmployeeDataPermission> listWithDataSource = entityDao.findDataPermissionByEmpIdWithDataSource(jobNum);
        //给数据来源中添加module和moduleCode
        for (EmployeeDataPermission employeeDataPermission : listWithDataSource) {
            employeeDataPermission.setModule(Constants.DATASOURCE);
            employeeDataPermission.setModuleCode(Constants.DATASOURCE_CODE);
        }
        //合并集合数据
        listWithDict.addAll(listWithDataSource);
        //返回总数据
        return listWithDict;
    }

    /**
     * 数据来源权限
     * 根据当前用户编号和数据权限类型获取权限列表
     * 查询数据来源表
     *
     * @param jobNum 员工编号
     * @return
     */
    public List<EmployeeDataPermission> findDataPermissionByEmpIdWithDataSource(String jobNum) {
        List<EmployeeDataPermission> list = entityDao.findDataPermissionByEmpIdWithDataSource(jobNum);
        //给modual和modualCode附默认值
        if (list != null && list.size() > 0) {
            for (EmployeeDataPermission permission : list) {
                permission.setModule(Constants.DATASOURCE);
                permission.setModuleCode(Constants.DATASOURCE_CODE);
            }
        }
        return list;
    }

    /**
     * 为用户设置数据权限
     *
     * @param jobNum
     * @param employeeDataPermissions
     * @param createUser              创建人
     * @param createTime              当前时间
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public StatusDto insertDataPermission(Date createTime, Long createUser, String jobNum, List<EmployeeDataPermission> employeeDataPermissions) {
        Employee employee = employeeDao.getOneByJobNum(jobNum);
        if (employee == null)
            return StatusDto.buildFailure("无此用户");
        //删除
        entityDao.deleteByJobNum(jobNum);
        //添加
        if (employeeDataPermissions == null || employeeDataPermissions.size() == 0) {
        } else {
            entityDao.batchAdd(createTime, jobNum, createUser, employeeDataPermissions);
        }
        return StatusDto.buildSuccess("数据权限设置成功！");
    }

    /**
     * 根据当前用户编号和数据权限类型获取权限列表（用于下拉框） 查询数据字典表
     *
     * @param permissionType 权限类型
     * @param jobNum         员工编号
     * @return
     */
    public List<EmployeeDataPermission> findEmpDataPermissionlist(String permissionType, String jobNum) {
        return entityDao.findEmpDataPermissionlist(permissionType, jobNum);
    }

    /**
     * 根据当前用户编号和数据权限类型获取权限列表（用于下拉框） 查询数据来源表
     *
     * @param jobNum 员工编号
     * @return
     */
    public List<EmployeeDataPermission> empDataPermissionWithDataSource(String jobNum) {
        return entityDao.findEmpDataPermissionWithDataSource(jobNum);
    }

    /**
     * 根据用户编号删除 数据权限
     *
     * @param jobNum 用户编号
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteByJobNum(String jobNum) {
        entityDao.deleteByJobNum(jobNum);
    }
}
