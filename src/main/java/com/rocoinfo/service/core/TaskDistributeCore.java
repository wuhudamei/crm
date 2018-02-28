package com.rocoinfo.service.core;

import java.util.*;

import com.rocoinfo.entity.basicConfig.Dictionary;
import com.rocoinfo.entity.dataSource.DataSource;
import com.rocoinfo.redis.JedisTemplate;
import com.rocoinfo.service.basicConfig.DictionaryService;
import com.rocoinfo.service.dataScource.DataSourceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rocoinfo.Constants;
import com.rocoinfo.entity.customer.CustomerTaskRmk;
import com.rocoinfo.entity.employee.EmployeeOrderReward;
import com.rocoinfo.entity.employee.EmployeeOrderSource;
import com.rocoinfo.entity.task.DistributeRmk;
import com.rocoinfo.entity.task.TaskDistribute;
import com.rocoinfo.enumeration.CustomerStatus;
import com.rocoinfo.enumeration.TaskDistributeModel;
import com.rocoinfo.enumeration.TaskNodeStatus;
import com.rocoinfo.enumeration.TaskTypeStatus;
import com.rocoinfo.service.basicConfig.DistributeRuleService;
import com.rocoinfo.service.customer.CustomerTaskRmkService;
import com.rocoinfo.service.employee.EmployeeOrderRewardService;
import com.rocoinfo.service.employee.EmployeeOrderSourceService;
import com.rocoinfo.service.task.DistributeRmkService;
import com.rocoinfo.service.task.TaskDistributeService;
import com.rocoinfo.utils.DateUtils;
import com.rocoinfo.utils.TaskDistributeTemplateUtil;

/**
 * <dl>
 * <dd>Description: 任务派发核心service</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-6-14 11:06:20</dd>
 * <dd>@author：Andy</dd>
 * </dl>
 */
@SuppressWarnings("all")
@Service
public class TaskDistributeCore {

	private static Logger logger = LoggerFactory.getLogger(TaskDistributeCore.class);// 日志

	/** A-autoorder自动接单 **/
	private static final String NO_DISTRIBUTE_TYPE_A = "A";
	/** B-busy忙碌 **/
	private static final String NO_DISTRIBUTE_TYPE_B = "B";

	/** 查询所有门店 **/
	private static final String DICTIONARY_MD_NAME = "MD";

	@Autowired
	private TaskDistributeService taskDistributeService;
	@Autowired
	private EmployeeOrderSourceService employeeOrderSourceService;
	@Autowired
	private EmployeeOrderRewardService employeeOrderRewardService;
	@Autowired
	private DistributeRuleService distributeRuleService;
	@Autowired
	private DistributeRmkService distributeRmkService;
	@Autowired
	private CustomerTaskRmkService customerTaskRmkService;
	@Autowired
	private TaskQueueCore taskQueueCore;
	@Autowired
	private EmployeeQueueCore employeeQueueCore;
	@Autowired
	private EmployeeRewardCore employeeRewardCore;
	@Autowired
	private DistributeRuleCore distributeRuleCore;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private DataSourceService dataSourceService;
	@Autowired
	private JedisTemplate jedisTemplate;

	/**
	 * 初始化任务队列
	 */
	public void initTaskToQueue() {
		List<TaskDistribute> taskDistributeList = taskDistributeService.findTaskByType(TaskTypeStatus.INVITATION); // 待邀约
		if (taskDistributeList != null && taskDistributeList.size() > 0) {
			taskDistributeList.forEach(taskDistribute -> {
				taskQueueCore.addTaskToQueue(taskDistribute.getStore(), taskDistribute.getDataSource(),
						taskDistribute.getTaskNo(), taskDistribute.getCustomerNo());
			});
			logger.info("【初始化任务队列成功!】");
		}
	}

	/**
	 * 初始化接单员队列 包括跟单员、接单规则、接单奖励,
	 * 默认只初始化奖励单
	 * @param initAll 是否初始化所有的数据
	 */
	public void initEmployeeParamToQueue(boolean initAll,Integer dayCount) {
		//指定几天前的旧的奖励单
		if(dayCount == null){
			dayCount = -1;
		}
		//如果需要初始化员工整套，初始化跟单员队列和接单规则
		if(initAll) {
			//查询员工接单来源
			List<EmployeeOrderSource> dataSourceList = employeeOrderSourceService.findOrderSourceList();
			if (dataSourceList != null && dataSourceList.size() > 0) {
				dataSourceList.forEach(orderSource -> {
					employeeQueueCore.addEmployeeToQueue(orderSource.getStoreCode(), orderSource.getOrderSource(),
							orderSource.getJobNum());
					employeeQueueCore.addEmployeeGetOrderRule(orderSource.getStoreCode(), orderSource.getJobNum(),
							orderSource.getAutoOrder(), orderSource.getBusyThreshold());
				});
				logger.info("初始化【跟单员队列】,【跟单员接单规则】成功!");
			}
		}

		//初始化员工的奖励单
		//——保存以前的奖励单的情况，并清除
		List<EmployeeOrderReward> employeeRewardList = new ArrayList<>();
		Set<String> oldRewardKeys = jedisTemplate.getKeys(Constants.EMPLOYEE_REWARD_NUM_KEY+"_*");
		for (String oldRewardKey : oldRewardKeys){
			String[] oldKeyParams = oldRewardKey.split("_");
			if( oldKeyParams.length < 2 ){
				continue;
			}
			String employeeJobNo = oldKeyParams[1];
			Integer employeeRewardNum = employeeRewardCore.getGiveEmployeeRewardOrderNum(employeeJobNo);
			EmployeeOrderReward employeeOrderReward = new EmployeeOrderReward();
			employeeOrderReward.setJobNo(employeeJobNo);
			employeeOrderReward.setActualNum(employeeRewardNum);
			employeeOrderReward.setRewardDate(DateUtils.format(DateUtils.getDateBeforOrAfterDate(
					new Date(), dayCount), "yyyy-MM-dd"));
			employeeRewardList.add(employeeOrderReward);
			// ————删除员工奖励单和已奖励单
			employeeRewardCore.removeEmployeeReward(employeeJobNo);
		}
		//————批量更新所有
		if (!employeeRewardList.isEmpty()) {
			employeeOrderRewardService.batchUpdate(employeeRewardList);
		}

		//——加载今日的奖励单配置
		List<EmployeeOrderReward> todayEmpRewardList = employeeOrderRewardService.findAllByDate();
		if (!todayEmpRewardList.isEmpty()) {
			todayEmpRewardList.forEach(employeeReward -> {
				employeeRewardCore.setEmployeeRewardOrderNum(employeeReward.getJobNo(), employeeReward.getRewardOrderNum());
			});
		}
		employeeQueueCore.setEmployeeQueueFlag();
		logger.info("初始化【跟单员奖励】成功!");
	}


	/**
	 * 重新加载redis中接单员的整套配置信息
	 */
	public void reloadEmployeeConfigInRedis(){
		// 查询所有门店
		List<Dictionary> dictionaryList = dictionaryService.findSubDictListByCode(DICTIONARY_MD_NAME);
		// 查询所有数据来源
		List<DataSource> dataSourceList = dataSourceService.findAll();


		if (dictionaryList != null && dictionaryList.size() > 0) {
			if (dataSourceList != null && dataSourceList.size() > 0) {
				try {
					dictionaryList.forEach(dictionary -> {
						dataSourceList.forEach(dataSource -> {
							// 门店来源下所有员工队列
							List<String> employeeQueueList = employeeQueueCore.getEmployeeQueueList(dictionary.getCode(),
									dataSource.getSourceCode());
							employeeQueueList.forEach(employeeJobNo -> {
								// 删除员工接单规则
								employeeQueueCore.removeEmployeeGetOrderRule(dictionary.getCode(), employeeJobNo);
							});
							// 删除员工队列.
							employeeQueueCore.deleteEmployeeQueue(dictionary.getCode(), dataSource.getSourceCode());
						});
					});
					// 删除员工队列是否为空标识
					employeeQueueCore.deleteEmployeeQueueIsEmpty();

					// 重新初始化接单员信息:队列,接单规则,接单奖励(清除老数据)
					initEmployeeParamToQueue(true,0);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("定时任务出错:" + e.getMessage());
				}
			}
		}
	}



	/**
	 * 初始化当前生效的派发规则的编号
	 */
	public void initDistributeRule() {
		String distributeRule = distributeRuleService.getByStatus();
		if (StringUtils.isNotBlank(distributeRule)) {
			distributeRuleCore.setDistributeRuleCode(distributeRule);
		}
	}

	/**
	 * 执行派发逻辑
	 */
	public void execute() {
		boolean taskQueue = taskQueueCore.taskQueueIsEmpty();
		if (!taskQueue) { // 有任务等待消费
			String taskStr = taskQueueCore.getTaskByQueue();// 任务队列取1个任务
			if (StringUtils.isNotBlank(taskStr)) { // 有效的任务
				String[] taskInfo = taskStr.split("_");
				String distributeCode = distributeRuleCore.getDistributeRuleCode(); // 派发规则
				if (Constants.LOOP_DISTRIBUTE_RULE.equals(distributeCode)) { // 规则001
					ruleLoopExecuteBody(taskInfo[0], taskInfo[1], taskInfo[2], taskInfo[3]);
				}
			} else {
				logger.error("【无效的任务:" + taskStr + ",TaskDistributeCore(142Line)】");
			}
		}
	}

	/**
	 * 循环派发规则执行主体
	 * 
	 * @param storeCode
	 *            门店
	 * @param source
	 *            来源
	 * @param taskNo
	 *            任务编号
	 * @param customerNo
	 *            客户编号
	 */
	private void ruleLoopExecuteBody(String storeCode, String source, String taskNo, String customerNo) {
		StringBuilder sb = new StringBuilder();
		String jobNo = employeeQueueCore.getEmployeeByQueue(storeCode, source); // 获取跟单员
		sb.append("【门店:" + storeCode + ",来源:" + source + ",任务号:" + taskNo + ",客户号:" + customerNo + "】，【跟单员:" + jobNo
				+ "】，");
		if (StringUtils.isNotBlank(jobNo)) {
			// 获取今日应奖励单数
			Integer rewardNum = employeeRewardCore.getEmployeeRewardOrderNum(jobNo);
			sb.append("【奖励单:" + rewardNum + "】，");
			// 员工没有奖励单，则客服接了该任务后重新排到队尾，如果有奖励单，则可以继续接任务
			if (rewardNum <= 0) {
				// 将跟单员移至对尾
				employeeQueueCore.moveEmployeeToQueueLast(storeCode, source, jobNo);
			}
			// 获取客服的接单规则
			String employeeRule = employeeQueueCore.getEmployeeGetOrderRule(storeCode, jobNo);
			sb.append("【接单规则:" + employeeRule + "】，");
			if (StringUtils.isNotBlank(employeeRule)) {
				Integer todayOrderNum = taskDistributeService.countEmployeeOrderNum(jobNo, getDate()); // 获取已分配任务总数
				String[] rule = employeeRule.split("_");
				String autoFlag = rule[0];
				Integer busyThreshold = Integer.parseInt(rule[1]);
				sb.append("【是否自动接单:" + autoFlag + "】，");
				// 是自动接单
				if (Constants.AUTO_ORDER_Y.equals(autoFlag)) {
					sb.append("【是否有奖励单:" + rewardNum + "】，");
					if (rewardNum > 0) { // 有奖励单
						Integer giveRewardNum = employeeRewardCore.getGiveEmployeeRewardOrderNum(jobNo);// 已奖励数量
						if (giveRewardNum < rewardNum) { // 奖励未完成
							boolean taskDistributeFlag = executeTaskDistribute(storeCode, source, taskNo, jobNo,
									customerNo);
							if (taskDistributeFlag) { // 任务派发成功
								Integer nowRewardNum = giveRewardNum + 1; // 奖励数加一
								employeeRewardCore.setGiveEmployeeRewardOrderNum(jobNo, nowRewardNum);// 更新redis奖励数量
								if (nowRewardNum.equals(rewardNum)) { // 奖励已经发完
									employeeRewardCore.removeEmployeeRewardAndUpdateDataBase(jobNo);// 移除员工奖励单数和已奖励单数
								}
							} else {// 任务派发失败的话,将此任务在加入到队列中
								taskQueueCore.addTaskToQueue(storeCode, source, taskNo, customerNo);
							}
						}
					} else { // 无奖励单
						sb.append("【当日接单:" + todayOrderNum + ",忙碌阈值:" + busyThreshold + "】，");
						if (todayOrderNum < busyThreshold) {// 未处于忙碌状态
							boolean taskDistributeFlag = executeTaskDistribute(storeCode, source, taskNo, jobNo,
									customerNo);
							if (!taskDistributeFlag) {// 任务派发失败的话,将此任务在加入到队列中
								taskQueueCore.addTaskToQueue(storeCode, source, taskNo, customerNo);
								logger.error("任务派发失败,将任务重新追加到对尾!TaskDistributeCore【202Line】");
							}
						} else {
							sb.append("【员工:" + jobNo + ",处于忙碌状态,未给予派单,任务加到对尾】，");
							taskQueueCore.addTaskToQueue(storeCode, source, taskNo, customerNo);
							logger.error(sb.toString());
						}
					}
				} else {
					taskQueueCore.addTaskToQueue(storeCode, source, taskNo, customerNo);
					sb.append("【员工:" + jobNo + ",处于未自动接单状态,任务加到对尾】，");
					//防止有奖励单的客户 不能自动接单，造成该门店来源组合的任务卡死
					employeeQueueCore.moveEmployeeToQueueLast(storeCode, source, jobNo);
					logger.error(sb.toString());
				}
			} else {
				taskQueueCore.addTaskToQueue(storeCode, source, taskNo, customerNo);
				sb.append("，【门店 :" + storeCode + ",下跟单员:" + jobNo + "】未设置接单规则!】");
				logger.error(sb.toString());
			}
		} else {
			taskQueueCore.addTaskToQueue(storeCode, source, taskNo, customerNo);
			sb.append("，【门店 :" + storeCode + ",来源:" + source + "】未设置对应的跟单员!】");
			logger.error(sb.toString());
		}
		logger.info(sb.toString());
	}

	/**
	 * 不执行任务派发的方法
	 * 
	 * @param storeCode
	 *            门店
	 * @param source
	 *            数据来源/接单来源
	 * @param moveToLast
	 *            是否移入队尾,true:是,false:否
	 * @param type
	 *            类型 0-未设置自动接单,1-忙碌中
	 * @description
	 * 
	 */
	@Deprecated
	private void executeNoDistributeTask(String storeCode, String source, String jobNo, String type) {
		employeeQueueCore.moveEmployeeToQueueLast(storeCode, source, jobNo); // 将跟单员移至对尾
		String typeName = "";
		if (NO_DISTRIBUTE_TYPE_A.equals(type)) {
			typeName = "未设置自动接单";
		} else if (NO_DISTRIBUTE_TYPE_B.equals(type)) {
			typeName = "已处于忙碌状态";
		}
		logger.info("跟单员【" + jobNo + "】" + typeName + ",未分配任务,排到队尾!");
	}

	/**
	 * 执行没有奖励单派发逻辑
	 * 
	 * @param storeCode
	 *            门店
	 * @param dataSource
	 *            数据来源
	 * @param jobNo
	 *            员工号
	 */
	private void executeNoRewardDistribute(String storeCode, String dataSource, String jobNo) {
		employeeQueueCore.moveEmployeeToQueueLast(storeCode, dataSource, jobNo);
	}

	/**
	 * 执行派发逻辑
	 * 
	 * @param storeCode
	 *            门店
	 * @param source
	 *            数据来源
	 * @param taskNo
	 *            任务编号
	 * @param jobNo
	 *            员工号
	 * @param customerNo
	 *            客户编号
	 * @return true-派发成功,false-派发失败
	 */
	@Transactional(rollbackFor = Exception.class)
	private boolean executeTaskDistribute(String storeCode, String source, String taskNo, String jobNo,
			String customerNo) {
		try {
			// 1:更新任务状态
			TaskDistribute taskDistribute = new TaskDistribute();
			taskDistribute.setTaskNo(taskNo);
			taskDistribute.setMechandiser(jobNo);
			taskDistribute.setDistributeStatus("Y"); // Y-已派发
			taskDistribute.setDistributeModel(TaskDistributeModel.SYSTEM.name()); // 派发方式
			taskDistribute.setDistributeTime(DateUtils.currentDate());
			taskDistributeService.updateByIdOrTaskNo(taskDistribute);

			// 2:插入任务派发轨迹表
			DistributeRmk distributeRmk = new DistributeRmk();
			distributeRmk.setTaskNo(taskNo);
			distributeRmk.setDistributeMechandiser(jobNo);
			distributeRmk.setType(TaskNodeStatus.INVITATION.name());
			distributeRmk.setCreateTime(DateUtils.currentDate());
			distributeRmk.setCreateUser(-1L);
			distributeRmkService.insert(distributeRmk);

			// 3:客户任务轨迹信息
			CustomerTaskRmk customerTaskRmk = new CustomerTaskRmk();
			customerTaskRmk.setTaskNo(taskNo);
			customerTaskRmk.setCustomerNo(customerNo);
			customerTaskRmk.setNewFlag("1");// 1-最新记录
			customerTaskRmk.setCurrentStatus(CustomerStatus.NEWCUSTOMER.name());
			customerTaskRmk.setCreateTime(DateUtils.currentDate());
			customerTaskRmk.setCreateUser(-1L); // -1表示当前操作人为系统
			customerTaskRmk.setMechandiser(jobNo);
			customerTaskRmkService.insert(customerTaskRmk);
			sendTaskDistributeTemplateMessage(taskNo, jobNo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 发送任务派发模板消息
	 * 
	 * @param taskNo
	 *            任务编号
	 * @param jobNo
	 *            跟单员编号
	 */
	private void sendTaskDistributeTemplateMessage(String taskNo, String jobNo) {
		Map<String, Object> customerInfo = taskDistributeService.getCustomerByTaskNo(taskNo);
		if (customerInfo != null) {
			TaskDistributeTemplateUtil.sendTaskDistributeReminder(jobNo, customerInfo.get("customer_name").toString(),
					customerInfo.get("customer_mobile").toString());
		}

	}

	/**
	 * 获取当前日期 年-月-日
	 * 
	 * @return
	 */
	private String getDate() {
		return DateUtils.format(DateUtils.currentDate(), "yyyy-MM-dd");
	}

}
