package com.rocoinfo.service.basicConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.entity.basicConfig.DistributeRule;
import com.rocoinfo.entity.basicConfig.RemindRule;
import com.rocoinfo.repository.basicConfig.DistributeRuleDao;
import com.rocoinfo.repository.basicConfig.RemindRuleDao;

/**
 * 提醒规则 service
 * @author Paul
 *
 */
@Service
public class RemindRuleService extends CrudService<RemindRuleDao, RemindRule> {

	@Autowired
	private RemindRuleDao remindRuleDao;
	
	//状态0: 禁用
	private final String DISABLE_STATUS = "0";
	//状态1: 启用
	private final String Enable_STATUS = "1";
	
	/**
	 * 通过id 启用/禁用 提醒规则
	 *  1.需要启用:启用当前规则,同时禁用其他规则
	 *  2.需要禁用:禁用当前规则
	 * @param id 规则id
	 * @return	 返回处理结果:成功/失败
	 */
	@Transactional(rollbackFor = RuntimeException.class)
	public boolean updateStatusById(Long id, String status) {
		boolean result = false;
		try {
			RemindRule remindRule = new RemindRule();
			remindRule.setId(id);
			
			if(Enable_STATUS.equals(status)){
				//需要禁用操作:
				remindRule.setStatus(DISABLE_STATUS);//禁用
				remindRuleDao.update(remindRule);
				return true;
			}
			
			//需要启用操作:
			remindRule.setStatus(Enable_STATUS);//启用
			// 启用当前,禁用其他
			remindRuleDao.EnableStatusAndDisableOthers(remindRule);
			
			result = true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
}
