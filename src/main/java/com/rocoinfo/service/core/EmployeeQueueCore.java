package com.rocoinfo.service.core;

import java.util.*;
import java.util.stream.Collectors;

import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.entity.employee.Employee;
import com.rocoinfo.repository.employee.EmployeeDao;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocoinfo.Constants;
import com.rocoinfo.redis.JedisTemplate;

/**
 * <dl>
 * <dd>Description: 员工数据相关service</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-6-14 11:06:20</dd>
 * <dd>@author：Andy</dd>
 * </dl>
 */
@SuppressWarnings("all")
@Service
public class EmployeeQueueCore {

	private static Logger logger = LoggerFactory.getLogger(EmployeeQueueCore.class);// 日志

	/** 员工队列设置成功的标志 **/
	private static final String EMPLOYEE_QUEUE_FLAG = "1";

	@Autowired
	private JedisTemplate jedisTemplate;

	@Autowired
	private EmployeeDao empDao;

	/**
	 * 添加跟单员到队列
	 * 
	 * @param storeCode
	 *            门店
	 * @param orderSource
	 *            接单来源
	 * @param jobNo
	 *            员工编号
	 * 
	 * @description key = E_门店_来源 value = [员工号,员工号,...]
	 * 
	 *              例: key = E_BJ_XMT value = [bj00001,bj00002,bj00003,...];
	 *              表示北京门店,新媒体的跟单员队列为:bj00001,bj00002,bj00003
	 */
	public void addEmployeeToQueue(String storeCode, String orderSource, String jobNo) {
		//判断队列中是否已存在该员工
		String empQueueKeyStr = Constants.EMPLOYEE_ORDER_KEY + "_" + storeCode + "_" + orderSource;
		List<String> empJobNoList = jedisTemplate.getListAllItem(empQueueKeyStr);
		if(!empJobNoList.contains(jobNo)) {
			jedisTemplate.rpush(empQueueKeyStr, jobNo);
		}
	}

	/**
	 * 设置员工队列标识
	 */
	public void setEmployeeQueueFlag() {
		// 表示初始化跟单员队列成功
		jedisTemplate.set(Constants.EMPLOYEE_ORDER_FLAG, EMPLOYEE_QUEUE_FLAG);
	}

	/**
	 * 员工队列是否为空
	 * 
	 * @return true/false
	 * 
	 * @description true:空 false:不为空
	 */
	public boolean getEmployeeQueueIsEmpty() {
		String isEmptyFlag = jedisTemplate.get(Constants.EMPLOYEE_ORDER_FLAG);
		if (StringUtils.isBlank(isEmptyFlag)) {
			return true;
		}
		return false;
	}

	/**
	 * 删除员工队列是否为空标识
	 * 
	 * @return
	 */
	public void deleteEmployeeQueueIsEmpty() {
		jedisTemplate.del(Constants.EMPLOYEE_ORDER_FLAG);
	}

	/**
	 * 获取队列中第一个跟单员并将跟单员移动到对尾
	 * 
	 * @param storeCode
	 *            门店
	 * @param orderSource
	 *            接单来源
	 * @param moveToLast
	 *            是否移动到对尾 true-是,false-不移动
	 * 
	 * @description 暂时废弃此方法,如果有需要再行使用
	 */
	@Deprecated
	public synchronized String getEmployeeAndMoveToQueueLast(String storeCode, String orderSource, boolean moveToLast) {
		String jobNo = jedisTemplate
				.getOneItemByList(Constants.EMPLOYEE_ORDER_KEY + "_" + storeCode + "_" + orderSource); // 取list中第一个元素
		if (moveToLast) {
			boolean removeFlag = removeEmployeeByQueue(storeCode, orderSource, jobNo);
			if (removeFlag) {
				addEmployeeToQueue(storeCode, orderSource, jobNo);
			}
		}
		return jobNo;
	}

	/**
	 * 将跟单员移动至队列尾部根据员工号
	 * 
	 * @param storeCode
	 * @param orderSource
	 * @param jobNo
	 * @return
	 */
//	public synchronized void moveEmployeeToQueueLastByJobNo(String storeCode, String orderSource, String jobNo) {
//		boolean removeFlag = removeEmployeeByQueue(storeCode, orderSource, jobNo);
//		if (removeFlag) {
//			addEmployeeToQueue(storeCode, orderSource, jobNo);
//		}
//	}
	
	/**
	 * 移动跟单员到队列尾部
	 * 
	 * @param storeCode
	 *            门店
	 * @param orderSource
	 *            接单来源
	 * @param jobNo
	 *            员工编号
	 */
	public synchronized void moveEmployeeToQueueLast(String storeCode, String orderSource, String jobNo) {
		boolean removeFlag = removeEmployeeByQueue(storeCode, orderSource, jobNo);
		if (removeFlag) {
			addEmployeeToQueue(storeCode, orderSource, jobNo);
		}
	}

	/**
	 * 获取跟单员
	 * 
	 * @param storeCode
	 *            门店
	 * @param orderSource
	 *            接单来源
	 * @return
	 */
	public synchronized String getEmployeeByQueue(String storeCode, String orderSource) {
		return jedisTemplate.getOneItemByList(Constants.EMPLOYEE_ORDER_KEY + "_" + storeCode + "_" + orderSource); // 取list中第一个元素
	}

	/**
	 * 获取跟单员 列表
	 * 
	 * @param storeCode
	 *            门店
	 * @param orderSource
	 *            接单来源
	 * @return
	 */
	public List<String> getEmployeeQueueList(String storeCode, String orderSource) {
		return jedisTemplate.getListAllItem(Constants.EMPLOYEE_ORDER_KEY + "_" + storeCode + "_" + orderSource);
	}

	/**
	 * 从队列中移除跟单员
	 * 
	 * @param storeCode
	 *            门店
	 * @param orderSource
	 *            接单来源
	 * @param jobNo
	 *            员工编号
	 * @return
	 */
	public boolean removeEmployeeByQueue(String storeCode, String orderSource, String jobNo) {
		return jedisTemplate.lremOne(Constants.EMPLOYEE_ORDER_KEY + "_" + storeCode + "_" + orderSource, jobNo);
	}

	/**
	 * 删除门店,接单来源队列中所有员工
	 * 
	 * @param storeCode
	 *            门店
	 * @param orderSource
	 *            接单来源
	 * @return
	 */
	public void deleteEmployeeQueue(String storeCode, String orderSource) {
		jedisTemplate.del(Constants.EMPLOYEE_ORDER_KEY + "_" + storeCode + "_" + orderSource);
	}

	/**
	 * 添加跟单员接单规则
	 * 
	 * @param storeCode
	 *            门店
	 * @param jobNo
	 *            员工编号
	 * @param autoOrder
	 *            是否自动接单
	 * @param busyThreshold
	 *            忙碌阈值
	 * @description key = 门店_员工号 , value = 是否自动接单(Y-是,N-否)_忙碌阈值(单数)
	 * 
	 *              例: key = BJ_bj0001 , value = Y_15
	 */
	public void addEmployeeGetOrderRule(String storeCode, String jobNo, String autoOrder, String busyThreshold) {
		jedisTemplate.set(Constants.EMPLOYEE_ORDER_RULE_KEY + "_" + storeCode + "_" + jobNo,
				autoOrder + "_" + busyThreshold);
	}

	/**
	 * 获取跟单员接单规则
	 * 
	 * @param storeCode
	 *            门店
	 * @param jobNo
	 *            员工号
	 * 
	 * @description key = 门店_员工号 , value = 是否自动接单(Y-是,N-否)_忙碌阈值(单数)
	 * 
	 *              例: key = BJ_bj0001 , value = Y_15
	 */
	public String getEmployeeGetOrderRule(String storeCode, String jobNo) {
		return jedisTemplate.get(Constants.EMPLOYEE_ORDER_RULE_KEY + "_" + storeCode + "_" + jobNo);
	}

	/**
	 * 删除跟单员接单规则
	 * 
	 * @param storeCode
	 *            门店
	 * @param jobNo
	 *            员工编号
	 */
	public void removeEmployeeGetOrderRule(String storeCode, String jobNo) {
		jedisTemplate.del(Constants.EMPLOYEE_ORDER_RULE_KEY + "_" + storeCode + "_" + jobNo);
	}


	/**
	 * 查询接单员工队列的实时信息
	 * @return
	 */
	public StatusDto findEmploeeQueueRealTimeInfo(String storeCode,String sourceCode,String keyWord){
		List<Map<String, Object>> empResultList = null;
		try {
			//查询所有正常的员工姓名
			Map<String, String> empNameMap = new HashMap<>();
			List<Employee> allEffectEmpList = empDao.findAllEmpEffect();
			for (Employee emp : allEffectEmpList) {
				empNameMap.put(emp.getJobNum(), String.format(("%s（%s）"), emp.getEmpName(), emp.getOrgCode()));
			}

			List<Map<String, Object>> empInQueueMapList = new ArrayList<>();
			//查询所有门店_数据来源队列的key
			Set<String> queueKeyList = jedisTemplate.getKeys(Constants.EMPLOYEE_ORDER_KEY + "_*");
			for (String queueKey : queueKeyList) {
				String storeCodeStr = "";
				String sourceCodeStr = "";
				String[] params = queueKey.split("_");
				if(params.length >= 3){
					storeCodeStr = params[1];
					sourceCodeStr = params[2];
				}

				//从redis中查询该队列下的接单客服jobNo
				List<String> empInQueueJobNoList = jedisTemplate.getListAllItem(queueKey);
				for (String jobNoInQueue : empInQueueJobNoList) {
					Map<String, Object> empInQueueMap = new HashMap<>();
					empInQueueMap.put("storeCode",storeCodeStr);
					empInQueueMap.put("sourceCode",sourceCodeStr);
					empInQueueMap.put("empName", empNameMap.containsKey(jobNoInQueue) ? empNameMap.get(jobNoInQueue) : "");
					empInQueueMap.put("expectedReward",jedisTemplate.get(String.format("%s_%s",Constants.EMPLOYEE_REWARD_NUM_KEY,jobNoInQueue)));
					empInQueueMap.put("actualReward",jedisTemplate.get(String.format("%s_%s",Constants.EMPLOYEE_GIVE_REWARD_NUM_KEY,jobNoInQueue)));
					//——客服员工的接单规则
					String ruleStr = jedisTemplate.get(String.format("%s_%s_%s", Constants.EMPLOYEE_ORDER_RULE_KEY, storeCodeStr, jobNoInQueue));
					if(ruleStr == null){
						ruleStr = "";
					}
					String allowAuto = "";
					String dailyThreshold = "";
					String[] ruleParamsArr = ruleStr.split("_");
					if(ruleParamsArr.length >= 2){
						allowAuto = ruleParamsArr[0];
						dailyThreshold = ruleParamsArr[1];
					}
					empInQueueMap.put("allowAuto", allowAuto);
					empInQueueMap.put("dailyThreshold",dailyThreshold);

					empInQueueMapList.add(empInQueueMap);
				}
			}
			//根据条件筛选结果
			empResultList = empInQueueMapList.stream().filter(a->{
				//——按门店筛选
				if(StringUtils.isNotEmpty(storeCode) && !storeCode.equals(a.get("storeCode").toString())){
					return false;
				}
				//——按数据来源筛选
				if(StringUtils.isNotEmpty(sourceCode) && !sourceCode.equals(a.get("sourceCode").toString())){
					return false;
				}
				//——按员工名筛选
				if(StringUtils.isNotEmpty(keyWord) && !a.get("empName").toString().contains(keyWord)){
					return false;
				}
				return true;
			}).collect(Collectors.toList());

			return StatusDto.buildSuccess(empResultList);
		}catch (Exception e){
			e.printStackTrace();
			return StatusDto.buildFailure();
		}

	}

}
