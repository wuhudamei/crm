package com.rocoinfo.repository.task;

import com.github.pagehelper.Page;
import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.task.Report;
import com.rocoinfo.entity.task.TaskDistribute;
import com.rocoinfo.enumeration.TaskTypeStatus;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 描述：任务派发
 *
 * @author tony
 * @创建时间 2017-06-09 16:49
 */
@SuppressWarnings("all")
@Repository
public interface TaskDistributeDao extends CrudDao<TaskDistribute> {
	List<Map> getCustomersTask(@Param("mechandiser") String mechandiser, @Param("status") List<String> status,
			@Param("keywork") String keywork);

	List<Map> getCustomersTaskForPage(@Param("mechandiser") String mechandiser,
									  @Param("status") List<String> status,
									  @Param("keywork") String keywork,
									  @Param("type") int type,
									  @Param("storeCode") String storeCode);

	/**
	 * 根据任务类型查询任务列表
	 * 
	 * @param taskTypeStatus
	 *            任务类型
	 */
	List<TaskDistribute> findTaskByType(@Param("taskTypeStatus") TaskTypeStatus taskTypeStatus);

	/**
	 * 
	 * 函数功能描述:查询某段时间内客户生成的任务List
	 * 
	 * @param customerNo
	 * @param repeatSecCD
	 * @return
	 */
	public List<TaskDistribute> queryCustomerTaskCreateInPeroud(@Param("customerNo") String customerNo,
			@Param("repeatSecCD") int repeatSecCD,@Param("storeCode") String storeCode);
	
	
	
	/**
	 * 
	 * 函数功能描述:根据手机号查询指定时间段内生成的任务
	 * @param phoneNum
	 * @return
	 */
	public List<TaskDistribute> queryTaskCreateInPeroudByPhoneAndDS(@Param("phoneNum") String phoneNum,
			@Param("repeatSecCD") int repeatSecCD,@Param("dataSource") String dataSource);
	
	

	/**
	 * 
	 * 函数功能描述:批量插入新任务
	 * 
	 * @param newTaskList
	 */
	public void insertNewTaskBatch(@Param("newTaskList") List<TaskDistribute> newTaskList);

	/**
	 * 根据ID或者任务编号更新任务派发数据;任务实体里面可能ID为空或可能任务编号为空，但不能同时为空
	 * 
	 * @param taskDistribute
	 */
	void updateByIdOrTaskNo(TaskDistribute taskDistribute);

	/**
	 * 统计跟单员当日接单数量
	 * 
	 * @param mechandiser
	 *            员工编号
	 * @param currentDate
	 *            日期
	 */
	Integer countEmployeeOrderNum(@Param("mechandiser") String mechandiser, @Param("currentDate") String currentDate);

	/**
	 * 根据用户ID取出最近十条联系记录
	 * 
	 * @param userId
	 * @return
	 */
	List<Map> recentContact(@Param("userId") long userId);

	/**
	 * 后台任务列表:查询该客户下的所有任务
	 */
	Page findTaskByCustomerNo(Map<String, Object> params);

	/**
	 * 根据任务编号获取 转发任务数据
	 * 
	 * @param taskNo
	 * @return
	 */
	TaskDistribute getDistributeTaskByTaskNo(String taskNo);

	/**
	 * 获取员工信息根据任务编号
	 * 
	 * @param taskNo
	 *            任务编号
	 * @return
	 */
	Map<String, Object> getCustomerByTaskNo(@Param("taskNo") String taskNo);

	/**
	 * 带条件的 导出 数据
	 * @author Paul
	 * @date 2017年7月7日 下午3:02:47
	 * @param params 条件
	 * @return
	 */
	List<TaskDistribute> findExportTaskDistribute(Map<String, Object> params);

	/**
	 * 根据任务号查询该任务对应的客户在订单系统中的客户ID
	 * @param taskNo 任务号
	 * @return
	 */
	String findCustomerIdInOrdByTaskNo(@Param("taskNo") String taskNo);
	/**
	 * 根据任务号查询该任务对应的客户姓名和电话
	 * @param taskNo 任务号
	 * @return
	 */
	Map<String,String> findCustomerNameAndPhone(@Param("taskNo") String taskNo);
	/**
	 * @author Ryze
	 * @date 2017-7-17
	 * 报表中心
	 * 统计进线数
	 * @param map 条件
	 * @return
	 */
	List<Report> countReportSourceOrChannel(Map<String, Object> map) ;

	/**
	 * @author Ryze
	 * @date 2017-7-17
	 * 报表中心
	 * 统计进店数
	 * @param map
	 * @return
	 */
	Long countInshop(Map<String, Object> map);

	/**
	 * 通过推荐人id,去获取该推荐人下 所有的任务列表
	 * @param introducerId 推荐人id
	 * @return list
	 * @author: Paul
	 * @date 2017-8-9
	 */
    List<TaskDistribute> findTaskByIntroducerId(String introducerId);


	/**
	 * 查询指定时间内转为指定状态的任务
	 *
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> selectTaskInfoWithState(Map<String, Object> map);


	/**
	 * 更新指定线索任务的状态
	 * @param taskNo
	 * @param newStatus
	 */
	void updateTaskStatus(@Param("taskNo") String taskNo,@Param("newStatus") String newStatus);
	/**
	 * 查询指定时间内创建
	 * 指定门店 的任务详情
	 * @return
	 */
	List<TaskDistribute> findReportForCommunicateAndOrder(Map<String, Object> map);

	/**
	 * 任务关联沟通记录
	 * type: 1 待邀约
	 * @return
	 */
	Long countPersonCommunicate(Map<String, Object> map);

	/**
	 * 任务关联沟通记录
	 * type: 2 已邀约
	 * type: 3 接待
	 * @return
	 */
	Long countPersonCommunication(Map<String, Object> map);


	/**
	 * 任务关联沟OrderInfor
	 * type: 1 大定
	 * type: 2 小定
	 * type: 3 退单
	 * @return
	 */
	Long countPersonOrderInfo(Map<String, Object> map);
}
