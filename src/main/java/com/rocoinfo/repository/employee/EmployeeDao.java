package com.rocoinfo.repository.employee;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.employee.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <dl>
 * <dd>Description: 美得你crm 用户dao</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/5</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@Repository
public interface EmployeeDao extends CrudDao<Employee> {

    /**
     * 根据编号查询用户
     *
     * @param jobNum 编号
     * @return
     */
    Employee getOneByJobNum(String jobNum);

    /**
     * 根据来源编号查询客户信息列表
     *
     * @param sourceCode
     * @return
     */
    List<Employee> findOneBySourceCode(String sourceCode);

    /**
     * 统计客服待邀约 当天/当月的数量
     *
     * @param nodeType 待邀约
     * @param timeType 当天/当月 day/month
     * @param date     哪天的
     * @param jobNum   客服编号
     * @return
     */
    long countByTaskNodeAndTime(@Param("jobNum") String jobNum,
                                @Param("nodeType") String nodeType,
                                @Param("date") Date date,
                                @Param("timeType") String timeType);

    /**
     * 统计客服已经邀约 当天/当月的数量
     *
     * @param timeType 当天/当月 day/month
     * @param date     哪天的
     * @param jobNum   客服编号
     * @return
     */
    long countAlreadyInvited(@Param("jobNum") String jobNum,
                             @Param("date") Date date,
                             @Param("timeType") String timeType);

    /**
     * 统计客服接待数
     *
     * @param timeType 当天/当月 day/month
     * @param jobNum   客服编号
     * @param date     哪天的
     * @return
     */
    long countReceptionCustomer(@Param("jobNum") String jobNum,
                                @Param("date") Date date,
                                @Param("timeType") String timeType);

    /**
     * 统计客户数
     *
     * @param jobNum 员工编号
     * @param date   哪天的
     * @return
     */
    long countCustomer(@Param("jobNum") String jobNum,
                       @Param("date") Date date);

    /**
     * 根据客服编号统计当日的接单量
     *
     * @param jobNum 客服编号
     * @return
     */
    long countOrderNum(String jobNum);

    /**
     * 查询员工分页
     *
     * @param params
     * @return
     */
    List<Map<String, String>> findEmployeeByPage(Map<String, Object> params);

    /**
     * 报表中心  统计 派单数
     *
     * @param params
     * @return
     */
    long countOrderNumByDate(Map<String, Object> params);

    /**
     * 报表中心  统计 待邀约数
     *
     * @param params
     * @return
     */
    long countByTaskNodeAndDate(Map<String, Object> params);

    /**
     * 报表中心  统计 已邀约数
     *
     * @param params
     * @return
     */
    long countAlreadyInvitedAndDate(Map<String, Object> params);

    /**
     * 报表中心  统计 已接待客户数
     *
     * @param params
     * @return
     */
    long countReceptionCustomerAndDate(Map<String, Object> params);

    /**
     *  查询所有员工数据(包含已删除的)
     * @return
     */
    List<Employee> findAllWithDelete();


    /**
     * 查找所有正常的员工
     * @return
     */
    List<Employee> findAllEmpEffect();
}
