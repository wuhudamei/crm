package com.rocoinfo.service.core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.entity.task.TaskDistribute;
import com.rocoinfo.repository.task.TaskDistributeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocoinfo.Constants;
import com.rocoinfo.redis.JedisTemplate;
import com.rocoinfo.weixin.util.StringUtils;

/**
 * <dl>
 * <dd>Description: 任务数据相关service</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-6-14 11:06:20</dd>
 * <dd>@author：Andy</dd>
 * </dl>
 */
@SuppressWarnings("all")
@Service
public class TaskQueueCore {

	private static Logger logger = LoggerFactory.getLogger(TaskQueueCore.class);// 日志

	@Autowired
	private JedisTemplate jedisTemplate;

	@Autowired
	private TaskDistributeDao taskDao;

	/**
	 * 添加任务到队列.
	 * 
	 * @param storeCode
	 *            门店
	 * @param dataSource
	 *            数据来源
	 * @param taskNo
	 *            任务编号
	 * @param customerNo
	 *            客户编号
	 * 
	 * @description key=T , value=门店_来源_任务编号_客户编号
	 * 
	 *              例: key = T , value =
	 *              BJ_XMT_s2skwi32las2lkaadkei23feks_XMT17062823081
	 */
	public void addTaskToQueue(String storeCode, String dataSource, String taskNo, String customerNo) {
		//检查队列中是否已经存在该任务
		List<String> taskList = jedisTemplate.getListAllItem(Constants.TASK_DISTRIBUTE_QUEUE_KEY);
		StringBuffer sb = new StringBuffer();
		sb.append(storeCode).append("_").append(dataSource).append("_").append(taskNo).append("_").append(customerNo);

		if(!taskList.contains(sb.toString())) {
			jedisTemplate.rpush(Constants.TASK_DISTRIBUTE_QUEUE_KEY, sb.toString());
		}
	}

	/**
	 * 从队列中取一个任务后将任务移除队列
	 * 
	 * @return taskInfoStr
	 * @description
	 * 
	 * 				例: BJ_XMT_s2skwi32las2lkaadkei23feks
	 */
	public String getTaskByQueue() {
		String taskInfoStr = jedisTemplate.getOneItemByList(Constants.TASK_DISTRIBUTE_QUEUE_KEY);
		String[] taskInfo = taskInfoStr.split("_");
		if(taskInfo.length == 4){
			removeTaskByQueue(taskInfo[0], taskInfo[1], taskInfo[2], taskInfo[3]);
			return taskInfoStr;
		}else{
			removeTaskByQueue(taskInfo[0], taskInfo[1], taskInfo[2], ""); //无效任务直接移除队列
			logger.error("【门店:" + taskInfo[0] +",来源:" + taskInfo[1] +",任务编号:" + taskInfo[2] +",缺少客户编号,无法进行正常派发,移除队列!】");
			getTaskByQueue(); //重新取任务
		}
		return null;
	}
	
	/**
	 * 任务队列是否为空
	 * 
	 * @return true=任务队列空 false=任务队列不为空
	 */
	public boolean taskQueueIsEmpty() {
		List<String> taskList = jedisTemplate.getListAllItem(Constants.TASK_DISTRIBUTE_QUEUE_KEY); // 获取任务队列.
		return taskList.isEmpty();
	}

	/**
	 * 从任务队列中移除任务
	 * 
	 * @param storeCode
	 *            门店
	 * @param dataSource
	 *            数据来源
	 * @param taskNo
	 *            任务编号
	 * @param customerNo
	 *            客户编号
	 * @return	true/false
	 */
	public boolean removeTaskByQueue(String storeCode, String dataSource, String taskNo, String customerNo) {
		boolean removeFlag = false;
		if(StringUtils.isNotBlank(customerNo)){
			removeFlag = jedisTemplate.lremOne(Constants.TASK_DISTRIBUTE_QUEUE_KEY, storeCode + "_" + dataSource + "_" + taskNo + "_" + customerNo);
		}else{
			removeFlag = jedisTemplate.lremOne(Constants.TASK_DISTRIBUTE_QUEUE_KEY, storeCode + "_" + dataSource + "_" + taskNo);
		}
		return removeFlag;
	}


	/**
	 * 查询redis任务队列实时的情况
	 * @return
	 */
	public StatusDto queryTaskQueueRealTimeInfo(String storeCode,String sourceCode,String keyWord){
		try {
			//最终的查询结果，元素包含字段齐全
			List<Map<String,Object>> taskInfoInQueueMapList = new ArrayList<>();
			//临时用的信息list，元素信息不全
			List<Map<String,Object>> partInfoInQueueMapList = new ArrayList<>();

			//redis中待自动派发的线索
			List<String> taskStrList = jedisTemplate.getListAllItem(Constants.TASK_DISTRIBUTE_QUEUE_KEY);
			for (String taskInfoStr : taskStrList) {
				if (StringUtils.isBlank(taskInfoStr)) {
					continue;
				}
				String storeCodeStr = "";
				String sourceCodeStr = "";
				String taskNoStr = "";
				String customerNoStr = "";
				String[] taskInfoItems = taskInfoStr.split("_");
				if (taskInfoItems.length >= 4) {
					storeCodeStr = taskInfoItems[0];
					sourceCodeStr = taskInfoItems[1];
					taskNoStr = taskInfoItems[2];
					customerNoStr = taskInfoItems[3];
				}
				//根据门店和数据来源 条件 进行筛选
				if(StringUtils.isNotBlank(storeCode) && !storeCode.equals(storeCodeStr)){
					continue;
				}
				if(StringUtils.isNotBlank(sourceCode) && !sourceCode.equals(sourceCodeStr)){
					continue;
				}

				Map<String,Object> taskInfoInqueueMap = new HashMap<>();
				taskInfoInqueueMap.put("storeCode",storeCodeStr);
				taskInfoInqueueMap.put("sourceCode",sourceCodeStr);
				taskInfoInqueueMap.put("taskNo",taskNoStr);
				taskInfoInqueueMap.put("customerNo",customerNoStr);

				partInfoInQueueMapList.add(taskInfoInqueueMap);
			}

			//为筛选后的任务 补全信息
			for (Map<String,Object> taskInfoMap : partInfoInQueueMapList){
				String taskNoStr = taskInfoMap.get("taskNo").toString();
				Map<String,String> task = taskDao.findCustomerNameAndPhone(taskNoStr);
				String cusName = task.get("name");
				String cusMobile = task.get("mobile");
				//需要根据keyword筛选
				if(StringUtils.isNotBlank(keyWord) && !cusName.contains(keyWord) && !cusMobile.contains(keyWord)){
					continue;
				}
				taskInfoMap.put("cusName",cusName);
				taskInfoMap.put("cusMobile",cusMobile);
				/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String createTimeStr = sdf.format(task.getCreateTime());*/
				taskInfoMap.put("createTime",task.get("createTime"));

				taskInfoInQueueMapList.add(taskInfoMap);
			}
			return StatusDto.buildSuccess(taskInfoInQueueMapList);
		}catch (Exception exp){
			exp.printStackTrace();
			return StatusDto.buildFailure("查询redis中线索时出错");
		}

	}


}
