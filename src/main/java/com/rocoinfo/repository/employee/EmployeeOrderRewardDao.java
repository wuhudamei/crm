package com.rocoinfo.repository.employee;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.employee.EmployeeOrderReward;

/**
 * <dl>
 * <dd>Description: 员工奖励 Dao</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-6-15 16:01:23</dd>
 * <dd>@author：Andy</dd>
 * </dl>
 */
@Repository
public interface EmployeeOrderRewardDao extends CrudDao<EmployeeOrderReward> {
	
	/**
	 * 根据日期查询当天的奖励单
	 * @param rewardDate	奖励日期
	 * @return
	 */
	List<EmployeeOrderReward> findAllByDate(@Param("rewardDate") String rewardDate);
	
	/**
	 * 批量更新奖励单
	 * @param employeeRewardList	奖励单信息list
	 */
	void batchUpdate(@Param(value = "employeeRewardList") List<EmployeeOrderReward> employeeRewardList);
}
