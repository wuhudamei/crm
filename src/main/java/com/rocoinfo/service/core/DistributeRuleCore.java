package com.rocoinfo.service.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocoinfo.Constants;
import com.rocoinfo.redis.JedisTemplate;

/**
 * <dl>
 * <dd>Description: 派发规则相关信息 service</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-6-16 15:49:11</dd>
 * <dd>@author：Andy</dd>
 * </dl>
 */
@SuppressWarnings("all")
@Service
public class DistributeRuleCore {

	private static Logger logger = LoggerFactory.getLogger(DistributeRuleCore.class);// 日志

	@Autowired
	private JedisTemplate jedisTemplate;

	/**
	 * 设置派发规则
	 * 
	 * @param distributeRuleCode
	 *            派发规则编码
	 */
	public void setDistributeRuleCode(String distributeRuleCode) {
		jedisTemplate.set(Constants.DISTRIBUTE_RULE_KEY, distributeRuleCode);
	}

	/**
	 * 获取派发规则
	 * @return
	 */
	public String getDistributeRuleCode() {
		return jedisTemplate.get(Constants.DISTRIBUTE_RULE_KEY);
	}

}
