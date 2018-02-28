package com.rocoinfo.service.core;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocoinfo.Constants;
import com.rocoinfo.entity.employee.EmployeeOrderReward;
import com.rocoinfo.redis.JedisTemplate;
import com.rocoinfo.service.employee.EmployeeOrderRewardService;
import com.rocoinfo.utils.DateUtils;
import com.rocoinfo.weixin.util.StringUtils;

/**
 * <dl>
 * <dd>Description: 员工奖励相关service</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-6-14 11:06:20</dd>
 * <dd>@author：Andy</dd>
 * </dl>
 */
@SuppressWarnings("all")
@Service
public class EmployeeRewardCore {

	private static Logger logger = LoggerFactory.getLogger(EmployeeRewardCore.class);// 日志

	@Autowired
	private JedisTemplate jedisTemplate;
	@Autowired
	private EmployeeOrderRewardService employeeOrderRewardService;

	/**
	 * 设置跟单员应奖励单数
	 * 
	 * @param jobNo
	 *            员工编号
	 * @param rewardOrderNum
	 *            奖励单数
	 */
	public void setEmployeeRewardOrderNum(String jobNo, Integer rewardOrderNum) {
		jedisTemplate.set(Constants.EMPLOYEE_REWARD_NUM_KEY + "_" + jobNo, String.valueOf(rewardOrderNum));
	}

	/**
	 * 获取员工当日应奖励单数
	 * 
	 * @param jobNo 员工编号
	 * @return
	 */
	public Integer getEmployeeRewardOrderNum(String jobNo) {
		String rewardNum = jedisTemplate.get(Constants.EMPLOYEE_REWARD_NUM_KEY + "_" + jobNo);
		if (StringUtils.isNotBlank(rewardNum)) {
			return Integer.parseInt(rewardNum);
		}
		return 0;
	}

	/**
	 * 设置 员工已经奖励的数量
	 * 
	 * @param jobNo
	 *            员工号
	 * @param giveRewardOrderNum
	 *            已奖励数量
	 */
	public void setGiveEmployeeRewardOrderNum(String jobNo, Integer giveRewardOrderNum) {
		jedisTemplate.set(Constants.EMPLOYEE_GIVE_REWARD_NUM_KEY + "_" + jobNo, String.valueOf(giveRewardOrderNum));
	}

	/**
	 * 从redis中获取员工已经奖励的单数
	 * 
	 * @param jobNo
	 *            员工编号
	 * @return
	 */
	public Integer getGiveEmployeeRewardOrderNum(String jobNo) {
		String giveRewardNum = jedisTemplate.get(Constants.EMPLOYEE_GIVE_REWARD_NUM_KEY + "_" + jobNo);
		if (StringUtils.isNotBlank(giveRewardNum)) {
			return Integer.parseInt(giveRewardNum);
		}
		return 0;
	}

	/**
	 * 删除员工奖励单数及已奖励单数并且同时更新到数据库
	 * 
	 * @param jobNo
	 *            员工编号
	 */
	public void removeEmployeeRewardAndUpdateDataBase(String jobNo) {
		//更新数据库中的奖励单信息
		updateEmployeeRewardNumToDataBase(jobNo);
		jedisTemplate.del(Constants.EMPLOYEE_REWARD_NUM_KEY + "_" + jobNo);
		jedisTemplate.del(Constants.EMPLOYEE_GIVE_REWARD_NUM_KEY + "_" + jobNo);
	}

	/**
	 * 删除员工奖励单数及已奖励单数
	 * 
	 * @param jobNo
	 *            员工编号
	 * @description 包括需要奖励的数量和已经奖励的数量
	 */
	public void removeEmployeeReward(String jobNo) {
		jedisTemplate.del(Constants.EMPLOYEE_REWARD_NUM_KEY + "_" + jobNo);
		jedisTemplate.del(Constants.EMPLOYEE_GIVE_REWARD_NUM_KEY + "_" + jobNo);
	}

	/**
	 * 更新员工已奖励单数到数据库
	 * 
	 * @param jobNo
	 *            员工号
	 */
	private void updateEmployeeRewardNumToDataBase(String jobNo) {
		// 已奖励数量
		Integer giveRewardNum = this.getGiveEmployeeRewardOrderNum(jobNo);
		EmployeeOrderReward employeeOrderReward = new EmployeeOrderReward();
		employeeOrderReward.setActualNum(giveRewardNum);
		employeeOrderReward.setJobNo(jobNo);
		employeeOrderReward.setRewardDate(DateUtils.format(new Date(), "yyyy-MM-dd"));
		// 更新奖励字段
		employeeOrderRewardService.update(employeeOrderReward);
	}

}
