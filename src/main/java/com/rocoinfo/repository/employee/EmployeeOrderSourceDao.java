package com.rocoinfo.repository.employee;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.employee.EmployeeOrderSource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <dl>
 * <dd>Description: 美得你crm 用户接单来源Dao</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/13</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@Repository
public interface EmployeeOrderSourceDao extends CrudDao<EmployeeOrderSource> {
    /**
     * 批量添加
     *
     * @param orderSourceList
     */
    void insertBatch(@Param("createTime") Date createTime,
                     @Param("jobNum") String jobNum,
                     @Param("createUser") Long createUser,
                     @Param("orderSourceList") List<EmployeeOrderSource> orderSourceList);

    /**
     * 根据用户编号删除 接单来源
     *
     * @param jobNum 用户编号
     */
    void deleteByJobNum(String jobNum);

    /**
     * 根据 用户编号 获取相应的接单来源列表
     *	查询数据字典表
     * @param jobNum  员工编号
     * @param dicCode 在字典表接单来源编号
     * @return
     */
    List<EmployeeOrderSource> findDataOrderSourceByEmpJobNum(@Param("jobNum") String jobNum, @Param("dicCode") String dicCode);

    /**
     * 根据 用户编号 获取相应的接单来源列表
     *	查询数据来源表
     * @param jobNum  员工编号
     * @return
     */
    List<EmployeeOrderSource> findDataOrderSourceByEmpJobNumWithDataSource(@Param("jobNum") String jobNum);
    
    /**
     * 获取全部接单来源列表
     *
     * @return
     */
    List<EmployeeOrderSource> findOrderSourceList();

    /**
     * 根据 用户编号 获取接单来源列表
     * @param jobNum 员工编号
     * @return
     */
    List<EmployeeOrderSource> findDataOrderSourceByJobNum(String jobNum);


    /**
     * 查询指定门店的有用指定接单来源的客服员工信息
     *
     * @param storeCodes
     * @param sourceCodes
     * @return
     */
    List<Map<String,Object>> selectEmployeeInfoWithStoreAndSource(@Param("storeCodes") List<String> storeCodes,@Param("sourceCodes") List<String> sourceCodes);
}

