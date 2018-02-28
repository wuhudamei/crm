package com.rocoinfo.service.basicConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.entity.basicConfig.DistributeRule;
import com.rocoinfo.repository.basicConfig.DistributeRuleDao;

/**
 * 派发规则 service
 * @author Paul
 *
 */
@Service
public class DistributeRuleService extends CrudService<DistributeRuleDao, DistributeRule> {

	@Autowired
	private DistributeRuleDao distributeRuleDao;
	
	//状态0: 禁用
	private final String DISABLE_STATUS = "0";
	//状态1: 启用
	private final String Enable_STATUS = "1";
	
	/**
	 * 查询启用状态的派发规则code
	 * @return
	 */
	public String getByStatus(){
		DistributeRule distributeRule = distributeRuleDao.getByStatus();
		if(distributeRule != null){
			return distributeRule.getCode();
		}
		return null;
	}
	
	/**
	 * 通过id 启用/禁用 派发规则
	 *  1.需要启用:启用当前规则,同时禁用其他规则
	 *  2.需要禁用:禁用当前规则
	 * @param id 规则id
	 * @return	 返回处理结果:成功/失败
	 */
	@Transactional(rollbackFor = RuntimeException.class)
	public boolean updateStatusById(Long id, String status) {
		boolean result = false;
		try {
			DistributeRule distributeRule = new DistributeRule();
			distributeRule.setId(id);
			
			if(Enable_STATUS.equals(status)){
				//需要禁用操作:
				distributeRule.setStatus(DISABLE_STATUS);//禁用
				distributeRuleDao.update(distributeRule);
				return true;
			}
			
			//需要启用操作:
			distributeRule.setStatus(Enable_STATUS);//启用
			// 启用当前,禁用其他
			distributeRuleDao.EnableStatusAndDisableOthers(distributeRule);
			
			result = true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
}
