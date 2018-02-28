package com.rocoinfo.service.employee;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.entity.employee.EmployeeOrderReward;
import com.rocoinfo.repository.employee.EmployeeOrderRewardDao;
import com.rocoinfo.utils.DateUtils;

/**
 * <dl>
 * <dd>Description: 员工奖励单service</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-6-15 16:27:59</dd>
 * <dd>@author：Andy</dd>
 * </dl>
 */
@SuppressWarnings("all")
@Service
public class EmployeeOrderRewardService extends CrudService<EmployeeOrderRewardDao,EmployeeOrderReward> {
    
    /**
     * 根据当前日期查询奖励单
     * @return
     */
    public List<EmployeeOrderReward> findAllByDate(){
        return  entityDao.findAllByDate(DateUtils.formatDate(new Date()));
    }

    /**
     * 查询某个员工在某天的奖励单
     * @return
     */
    public List<EmployeeOrderReward> findByEmpAndDate(String empJobNum,Date rewardDate){
        String rewardDateStr = null;
        //不传日期，默认使用当日日期
        if(rewardDate == null){
            rewardDateStr = DateUtils.formatDate(new Date());
        }
        else{
            rewardDateStr = DateUtils.formatDate(rewardDate);
        }

        Map<String,Object> queryMap = new HashMap<String,Object>();
        queryMap.put("jobNo",empJobNum);
        queryMap.put("rewardDate",rewardDateStr);

        return entityDao.search(queryMap);
    }
    
    @Override
    public int update(EmployeeOrderReward employeeOrderReward){
    	return this.entityDao.update(employeeOrderReward);
    }
    
    /**
     * 批量更新奖单
     * @param employeeRewardList 奖励单信息list
     */
    public void batchUpdate(List<EmployeeOrderReward> employeeRewardList){
    	this.entityDao.batchUpdate(employeeRewardList);
    }
    
}
