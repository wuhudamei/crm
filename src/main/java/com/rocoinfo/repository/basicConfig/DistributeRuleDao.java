package com.rocoinfo.repository.basicConfig;

import org.springframework.stereotype.Repository;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.basicConfig.DistributeRule;

/**
 * 派发规则 Dao
 * @author Paul
 *
 */
@Repository
public interface DistributeRuleDao extends CrudDao<DistributeRule> {

	/**
	 *  启用当前id,禁用 其他提醒规则 
	 * @param DistributeRule 要启用的派发规则
	 */
	void EnableStatusAndDisableOthers(DistributeRule distributeRule);
	
	/**
	 * 查询启用状态的规则
	 */
	DistributeRule getByStatus();
	
}
