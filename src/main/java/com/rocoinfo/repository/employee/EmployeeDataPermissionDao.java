package com.rocoinfo.repository.employee;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.employee.Employee;
import com.rocoinfo.entity.employee.EmployeeDataPermission;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.crypto.Data;

import java.util.Date;
import java.util.List;

/**
 * <dl>
 * <dd>Description: 美得你crm 用户数据权限dao</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/9</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@Repository
public interface EmployeeDataPermissionDao extends CrudDao<EmployeeDataPermission> {
    /**
     * 根据 用户编号 获取相应的数据权限列表
     *		查询数据字典表
     * @param jobNum 员工编号
     * @return
     */
    List<EmployeeDataPermission> findDataPermissionByEmpId(String jobNum);

    /**
     * 根据 用户编号 获取相应的数据权限列表
     *		查询数据来源表
     * @param jobNum 员工编号
     * @return
     */
    List<EmployeeDataPermission> findDataPermissionByEmpIdWithDataSource(
			String jobNum);
    
    /**
     * 根据用户编号删除 数据权限
     *
     * @param jobNum 用户编号
     */
    void deleteByJobNum(String jobNum);

    /**
     * 给用户添加权限
     *
     * @param jobNum                  用户编号
     * @param employeeDataPermissions 数据权限 集合
     * @param createUser              创建人
     * @param createTime              当前时间
     */
    void batchAdd(
            @Param("createTime") Date createTime,
            @Param("jobNum") String jobNum,
            @Param("createUser") Long createUser,
            @Param("employeeDataPermissions") List<EmployeeDataPermission> employeeDataPermissions);

    /**
     * 根据员工编号 和权限类型 获取相应的权限列表--查询数据字典表
     * @param permissionType 权限类型
     * @param jobNum 员工编号
     * @return
     */
    List<EmployeeDataPermission> findEmpDataPermissionlist(@Param("permissionType") String permissionType, @Param("jobNum") String jobNum);

    /**
     * 根据员工编号 和权限类型 获取相应的权限列表--查询数据来源表
     * @param jobNum 员工编号
     * @return
     */
    List<EmployeeDataPermission> findEmpDataPermissionWithDataSource(@Param("jobNum") String jobNum);

    
}
