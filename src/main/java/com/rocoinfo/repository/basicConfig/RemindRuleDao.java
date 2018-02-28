package com.rocoinfo.repository.basicConfig;

import org.springframework.stereotype.Repository;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.basicConfig.DistributeRule;
import com.rocoinfo.entity.basicConfig.RemindRule;

/**
 * 提醒规则 Dao
 * @author Paul
 *
 */
@Repository
public interface RemindRuleDao extends CrudDao<RemindRule> {

	/**
	 *  启用当前id,禁用 其他提醒规则
	 * @param RemindRule 要启用的提醒规则
	 */
	void EnableStatusAndDisableOthers(RemindRule remindRule);
	
}
