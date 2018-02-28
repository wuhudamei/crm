package com.rocoinfo.service.employee;

import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.entity.employee.Employee;
import com.rocoinfo.entity.employee.EmployeeOrderSource;
import com.rocoinfo.repository.employee.EmployeeDao;
import com.rocoinfo.repository.employee.EmployeeOrderSourceDao;

import com.rocoinfo.service.core.EmployeeQueueCore;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <dl>
 * <dd>Description: 美得你crm</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/13</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@Service
public class EmployeeOrderSourceService extends CrudService<EmployeeOrderSourceDao, EmployeeOrderSource> {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private EmployeeQueueCore empQueueService;

    /**
     * 根据 用户编号 获取相应的接单来源列表（check 选中未选中）
     * 查询数据字典表
     *
     * @param jobNum  员工编号
     * @param dicCode 在字典表接单来源编号
     * @return
     */
    public List<EmployeeOrderSource> findDataOrderSourceByEmpJobNum(String jobNum, String dicCode) {
        return entityDao.findDataOrderSourceByEmpJobNum(jobNum, dicCode);
    }

    /**
     * 根据 用户编号 获取相应的接单来源列表（check 选中未选中）
     * 查询数据来源表
     *
     * @param jobNum 员工编号
     * @return
     * @author Paul
     */
    public List<EmployeeOrderSource> findDataOrderSourceByEmpJobNumWithDataSource(String jobNum) {
        return entityDao.findDataOrderSourceByEmpJobNumWithDataSource(jobNum);
    }

    /**
     * 根据 用户编号 获取接单来源列表
     *
     * @param jobNum 员工编号
     * @return
     */
    public List<EmployeeOrderSource> findDataOrderSourceByJobNum(String jobNum) {
        return entityDao.findDataOrderSourceByJobNum(jobNum);
    }


    /**
     * 为用户设置接单来源
     *
     * @param jobNum
     * @param employeeOrderSources 接单来源列表
     * @param createUser           创建人
     * @param createTime           当前时间
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public StatusDto insertOrderSource(Date createTime, Long createUser, String jobNum, List<EmployeeOrderSource> employeeOrderSources) {
        try {
            Employee employee = employeeDao.getOneByJobNum(jobNum);
            //校验员工状态
            if (employee == null) {
                return StatusDto.buildFailure("无此员工");
            }
            if (!"1".equals(employee.getStatus())) {
                return StatusDto.buildFailure("不能为禁用的员工设置接单来源");
            }
            //新提交的选中的数据来源code
            List<String> newEmpSourceCodes = employeeOrderSources.stream().map(a -> a.getOrderSource()).collect(Collectors.toList());
            //删除前查到以前的接单来源的编号,需要过滤下 check = true
            List<String> oldEmpSourceCodes = findDataOrderSourceByEmpJobNumWithDataSource(jobNum)
                    .stream().filter(a -> a.getChecked()).map(a -> a.getOrderSource()).collect(Collectors.toList());
            //用来操作的老关系的副本
            List<String> oldEmpSourceCodesCopy = new ArrayList<>();
            oldEmpSourceCodesCopy.addAll(oldEmpSourceCodes);

            //先删除
            entityDao.deleteByJobNum(jobNum);
            //后添加
            if (employeeOrderSources == null || employeeOrderSources.size() == 0) {
            } else {
                entityDao.insertBatch(createTime, jobNum, createUser, employeeOrderSources);
            }

            //影响redis里员工的接单来源
            //——刚去掉的来源
            oldEmpSourceCodesCopy.removeAll(newEmpSourceCodes);
            for (String oldSrcCode : oldEmpSourceCodesCopy) {
                empQueueService.removeEmployeeByQueue(employee.getStoreCode(), oldSrcCode, jobNum);
            }

            //——新加的来源
            newEmpSourceCodes.removeAll(oldEmpSourceCodes);
            for (String newSrcCode : newEmpSourceCodes) {
                empQueueService.addEmployeeToQueue(employee.getStoreCode(), newSrcCode, jobNum);
            }

            return StatusDto.buildSuccess("接单来源设置成功！");
        }
        catch (Exception exp){
            exp.printStackTrace();;
            return StatusDto.buildFailure("设置接单来源程序异常！");
        }
    }

    /**
     * 获取全部接单来源列表
     *
     * @return
     */
    public List<EmployeeOrderSource> findOrderSourceList() {
        return entityDao.findOrderSourceList();
    }

    /**
     * 根据用户编号删除 接单来源
     *
     * @param jobNum 用户编号
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteByJobNum(String jobNum) {
        entityDao.deleteByJobNum(jobNum);
    }


    /**
     * 查询指定门店的有用指定接单来源的客服员工信息
     *
     * @param StoreCodes
     * @param sourceCodes
     * @return
     */
    public List<Map<String,Object>> queryEmployeeInfoWithStoreAndSource(List<String> StoreCodes, List<String> sourceCodes){
        try {
            return entityDao.selectEmployeeInfoWithStoreAndSource(StoreCodes, sourceCodes);
        }
        catch (Exception exp){
            exp.printStackTrace();
            return null;
        }
    }
}
