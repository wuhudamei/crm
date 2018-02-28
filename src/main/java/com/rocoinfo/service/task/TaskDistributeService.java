package com.rocoinfo.service.task;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rocoinfo.Constants;
import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.dto.page.PageTable;
import com.rocoinfo.dto.page.Pagination;
import com.rocoinfo.entity.customer.Communicate;
import com.rocoinfo.entity.customer.Customer;
import com.rocoinfo.entity.customer.CustomerHouse;
import com.rocoinfo.entity.customer.CustomerTaskRmk;
import com.rocoinfo.entity.dataSource.DataSource;
import com.rocoinfo.entity.task.DistributeRmk;
import com.rocoinfo.entity.task.Report;
import com.rocoinfo.entity.task.TaskDistribute;
import com.rocoinfo.entity.taskrules.TaskAllowautoRules;
import com.rocoinfo.enumeration.*;
import com.rocoinfo.redis.JedisTemplate;
import com.rocoinfo.repository.customer.CustomerDao;
import com.rocoinfo.repository.customer.CustomerHouseDao;
import com.rocoinfo.repository.customer.CustomerTaskRmkDao;
import com.rocoinfo.repository.dataSource.DataSourceDao;
import com.rocoinfo.repository.employee.EmployeeDao;
import com.rocoinfo.repository.task.DistributeRmkDao;
import com.rocoinfo.repository.task.TaskDistributeDao;
import com.rocoinfo.service.core.TaskQueueCore;
import com.rocoinfo.service.customer.CommunicateService;
import com.rocoinfo.service.order.OrderService;
import com.rocoinfo.service.schedule.ScheduleService;
import com.rocoinfo.service.taskrules.TaskAllowautoRulesService;
import com.rocoinfo.utils.GenerateCodeUtil;
import com.rocoinfo.utils.TaskDistributeTemplateUtil;
import com.rocoinfo.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 描述：
 *
 * @author tony
 * @创建时间 2017-06-13 10:53
 */
@SuppressWarnings("all")
@Service
public class TaskDistributeService extends CrudService<TaskDistributeDao, TaskDistribute> {

	@Autowired
	private JedisTemplate jedisTemplate;
	@Autowired
	private DistributeRmkDao distributeRmkDao;
	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private DataSourceDao dsDao;

	@Autowired
	private CustomerHouseDao customerHouseDao;

	@Autowired
	private TaskDistributeDao taskDistributeDao;
	@Autowired
	private ScheduleService scheduleService;

	/**
	 * 订单服务
	 */
	@Autowired
	private OrderService orderService;

	@Autowired
	private TaskQueueCore taskQueService;
	@Autowired
	private CustomerTaskRmkDao customerTaskRmkDao;
	@Autowired
	private CommunicateService communicateService;
	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private TaskAllowautoRulesService taskAllowautoRulesService;


	/**
	 * 微信端我的客户列表中的新客户数据
	 * 
	 * @param mechandiser
	 *            跟单员
	 * @param status
	 *            状态列表
	 * @return 新客户列表
	 */
	public List<Map> getCustomersTask(String mechandiser, List<String> status, String keywork) {
		return this.entityDao.getCustomersTask(mechandiser, status, keywork);
	}

	/**
	 * 微信端我的客户列表中的新客户数据 带分页
	 *
	 * @param mechandiser
	 *            跟单员
	 * @param status
	 *            状态列表
	 * @return 新客户列表
	 */
	public PageTable getCustomersTaskForPage(String mechandiser, List<String> status, String keywork, int limit, int pageIndex) {

		int offset = pageIndex * limit;

		Pagination page = new Pagination(offset, limit);

		// 沟通中 新客户 其他
		int type = 0;
		// 订单客户查询
		if (status.size() == 1 && CustomerStatus.ORDERSUCCESS.name().equals(status.get(0))) {
			type = 1;
		}

		// 利用pagehelper分页查询
		PageHelper.offsetPage(page.getOffset(), page.getLimit());
		Page<Map> result = (Page<Map>) this.entityDao.getCustomersTaskForPage(mechandiser, status, "", type, WebUtils.getLoggedUser().getStoreCode());
		int total = (int)result.getTotal();
		int pageTotal = total % limit == 0 ? total / limit : (total / limit + 1);

		List<Map> resultList = result.getResult();
		// 判断是不是订单客户列表 如果是订单客户，还需要去请求订单系统对应的订单状态回来

		// 返回查询结果
		return new PageTable<Map>(resultList, (long)pageTotal);
	}

	/**
	 * 如果该任务号有多个订单，则以小定订单为主。即最终显示的是小定
	 * 'LARGESET': '大定',
	 * 'SMALLSET': '小定'
	 * @param houseOrders 房屋订单号列表
	 * @param resultList 订单系统查询回来列表
	 * @return
	 */
	private Map handleStatus(List<Map> houseOrders, List<Map> resultList) {
		Map<String, String> map = new HashMap<String, String>();
		for (Map m : houseOrders) {
			String ordNo = (String)m.get("orderNum");
			String taskNo = (String)m.get("taskNo");
			for (Map sm : resultList) {
				if (ordNo.equals((String) sm.get("orderNum"))) {
					String statu = (int)sm.get("statu") == 0 ? "SMALLSET" : "LARGESET";
					// 判断该任务有没有保存，如果没有保存过，则直接保存; 如果保存过，则看看保存的是大定还是小定，如果是小定则直接略过,如果是大定，则直接保存当前
					if (!map.containsKey(taskNo) || "LARGESET".equals(map.get(taskNo))) {
						map.put(taskNo, statu);
					}
					break;
				}
			}
		}
		return map;
	}

	/**
	 * 根据任务类型查询任务
	 * 
	 * @param taskTypeStatus
	 *            任务类型枚举
	 */
	public List<TaskDistribute> findTaskByType(TaskTypeStatus taskTypeStatus) {
		return this.entityDao.findTaskByType(taskTypeStatus);
	}

	/**
	 * 更新派发
	 * 
	 * @param taskDistribute
	 */
	public void updateByIdOrTaskNo(TaskDistribute taskDistribute) {
		this.entityDao.updateByIdOrTaskNo(taskDistribute);
	}

	/**
	 * 统计跟单员当日接单数量
	 * 
	 * @param mechandiser
	 *            跟单员
	 * @param currentDate
	 *            接单日期
	 * @return
	 */
	public Integer countEmployeeOrderNum(String mechandiser, String currentDate) {
		return this.entityDao.countEmployeeOrderNum(mechandiser, currentDate);
	}

	/**
	 * 根据返回任务派发对象
	 * @param taskNo 任务编号
	 * @return
	 */
	public TaskDistribute getDistributeTaskByTaskNo(String taskNo) {
		return this.entityDao.getDistributeTaskByTaskNo(taskNo);
	}


	/**
	 * 函数功能描述:插入新任务，使用默认的任务重复间隔时间
	 *
	 * @param newTasks
	 * @param dsCode
	 * @return
	 */
	public List<TaskDistribute> addNewTasks(List<TaskDistribute> newTasks, String dsCode) {
		int defaultRepeatSecondCD = PropertyHolder.getRepeatCDSec();
		return addNewTasks(newTasks,dsCode,defaultRepeatSecondCD);
	}


	/**
	 * 
	 * 函数功能描述:插入新任务，使用指定的任务重复间隔时间
	 * 
	 * @param newTask
	 */
	@Transactional(rollbackFor = Exception.class)
	public List<TaskDistribute> addNewTasks(List<TaskDistribute> newTasks, String dsCode,int repeatSecondCD) {
		//根据客户手机号去重
		removeDuplicateCusPhoneNoWithOrder(newTasks);

		// 要持久化的任务
		List<TaskDistribute> tasksToAdd = new ArrayList<TaskDistribute>();
		// 要持久化的客户
		List<Customer> customersToAdd = new ArrayList<Customer>();
		// 要放到自动分发redis队列里的任务
		List<TaskDistribute> tasksToAutoDistribute = new ArrayList<TaskDistribute>();
		// 查到数据来源配置
		DataSource tasksDs = dsDao.getDataSourceByCode(dsCode);
		if (tasksDs == null) {
			return null;
		}


		/* 处理并归类任务 */
		for (TaskDistribute newTask : newTasks) {

			/* 准备工作 */
			// 任务的生成时间
			newTask.setCreateTime(new Date());
			// 任务编号
			newTask.setTaskNo(GenerateCodeUtil.generateTaskCode());
			newTask.setDistributeStatus("N");
			newTask.setDataSource(tasksDs.getSourceCode());

			/* 判断任务是否异常 */
			if (!checkTaskOK(newTask)) {
				newTask.setType(TaskTypeStatus.ABNORMAL.toString());
				newTask.setStatus(TaskDistributeStatus.INVALID.getIndex() + "");
				
				//后台创建的任务会事先选择跟单员，此处需要清掉，否则会分给那些跟单员
				newTask.setMechandiser(null);
				newTask.setDistributeTime(null);
				newTask.setDistributeModel(null);
				
				tasksToAdd.add(newTask);
				continue;
			}

			//防止高并发造成的极端时间内无法判断是否重复
			//如果短时间内存在重复，直接将任务按重复方式处理
			if(checkTaskRepeatInShortPerioud(newTask)){
				newTask.setType(TaskTypeStatus.REPEAT.toString());
				newTask.setStatus(TaskDistributeStatus.INVALID.getIndex() + "");

				//后台创建的任务会事先选择跟单员，此处需要清掉，否则会分给那些跟单员
				newTask.setMechandiser(null);
				newTask.setDistributeTime(null);
				newTask.setDistributeModel(null);

				tasksToAdd.add(newTask);
				continue;
			}


			/*如果客户名称为空，设置成手机号*/
			if(StringUtils.isBlank(newTask.getCustomerName())) {
				newTask.setCustomerName(newTask.getCustomerMobile());
			}

			/* 从任务中构造客户，并且建立与客户的关联 */
			List<Customer> oldCus = customerDao.getCustomerByMobileNo(newTask.getCustomerMobile());
			if(oldCus == null || oldCus.size() <=0){
				String newCusNo = GenerateCodeUtil.generateCustomerCode(tasksDs.getSourceCode());

				Customer newCus = new Customer();
				newCus.setCustomerNo(newCusNo);
				newCus.setCustomerName(newTask.getCustomerName());
				newCus.setCustomerMobile(newTask.getCustomerMobile());
				newCus.setReserveMobile(newTask.getReserveMobile());
				newCus.setCustomerTag(newTask.getTaskTag());
				newCus.setHomePhone(newTask.getHomePhone());
				customersToAdd.add(newCus);

				newTask.setCustomerNo(newCusNo);
				newTask.setType(TaskTypeStatus.INVITATION.toString());
				newTask.setStatus(TaskDistributeStatus.VALID.getIndex() + "");
			} else {
				Customer tmpOldCus = oldCus.get(0);
				newTask.setCustomerNo(tmpOldCus.getCustomerNo());
				/* 判断任务是否是重复任务,同一客户在CD时间内同一门店的任务 */
				List<TaskDistribute> oldTasks = entityDao.queryCustomerTaskCreateInPeroud(tmpOldCus.getCustomerNo(),
						repeatSecondCD,newTask.getStore());
				if (oldTasks.size() > 0) {
					newTask.setType(TaskTypeStatus.REPEAT.toString());
					newTask.setStatus(TaskDistributeStatus.INVALID.getIndex() + "");
					
					//后台创建的任务会事先选择跟单员，此处需要清掉，否则会分给那些跟单员
					newTask.setMechandiser(null);
					newTask.setDistributeTime(null);
					newTask.setDistributeModel(null);
				} else {
					newTask.setType(TaskTypeStatus.INVITATION.toString());
					newTask.setStatus(TaskDistributeStatus.VALID.getIndex() + "");
				}
			}
			tasksToAdd.add(newTask);
			
			//只有需要预约的线索才去设置派发相关设置
			if(TaskTypeStatus.INVITATION.toString().equals(newTask.getType())){
				//判断是否可以
				if (ifTaskAbleAutoDistribute(newTask)) {
					tasksToAutoDistribute.add(newTask);
				}
			}
		}

		/* 批量持久化任务和客户 */
		// 持久化任务
		if (tasksToAdd.size() > 0) {
			entityDao.insertNewTaskBatch(tasksToAdd);
		}

		// 持久化客户
		if (customersToAdd.size() > 0) {
			customerDao.insertCustomerBatch(customersToAdd);
		}

		/* 将需要自动分配的任务放到redisList中 */
		for (TaskDistribute taskToAdd : tasksToAutoDistribute) {
			taskQueService.addTaskToQueue(taskToAdd.getStore(), taskToAdd.getDataSource(),
					taskToAdd.getTaskNo(),taskToAdd.getCustomerNo());
		}

		/* 返回持久化的taskList */
		return tasksToAdd;

	}


	/**
	 * 通过手机号判断某门店的线索在指定时间段内是否重复
	 *
	 * @param mobileNo	客户手机号
	 * @param repeatCD  任务重复CD时长(秒)
	 * @return
	 */
	public boolean checkIfTaskRepeatInPerioud(String mobileNo,int repeatCD,String storeCode){
		//手机号为空，直接返回重复
		if(StringUtils.isBlank(mobileNo)) {
			return true;
		}

		List<Customer> oldCus = customerDao.getCustomerByMobileNo(mobileNo);
		//该手机号的客户已经存在，判断新旧线索间隔是否大于CD时间
		if(oldCus.size() > 0 ) {
			/* 在CD时间内有以前的线索，则重复 */
			List<TaskDistribute> oldTasks = entityDao.queryCustomerTaskCreateInPeroud(mobileNo, repeatCD,storeCode);
			if(oldTasks.size() >0) {
				return true;
			}
		}

		//任务未重复
		return false;
	}


	/**
	 * 检查短时间内连续传来的任务是否重复
	 * 目前只根据用户电话来判断
	 * 使用对象锁防止并发造成的异常
	 * @return
	 */
	private synchronized boolean checkTaskRepeatInShortPerioud(TaskDistribute task){
		List<String> cusPhoneNoList = jedisTemplate.getListAllItem(Constants.RECENT_CUSTOMER_PHONENO);
		StringBuffer phoneNumSb = new StringBuffer();
		phoneNumSb.append(task.getStore()).append("_").append(task.getCustomerMobile());
		//存在重复的
		if(cusPhoneNoList.contains(phoneNumSb.toString())) {
			return true;
		}

		//没有重复的
		//如果redis中记录的手机号超过设定的最大数量，则将队列中第一个移除队列
		if(cusPhoneNoList.size()>=PropertyHolder.getRecentCusPhoneNuMaxAmount()) {
			jedisTemplate.lpop(Constants.RECENT_CUSTOMER_PHONENO);
		}

		//将该电话号码加入redis里
		jedisTemplate.rpush(Constants.RECENT_CUSTOMER_PHONENO,phoneNumSb.toString());

		//没有重复，返回false
		return false;
	}


	/**
	 * 
	 * 函数功能描述:根据任务关键字段数据检查任务是否正常，目前是
	 *  1、检查任务是否有手机号
	 *  2、客户姓名中包含"无效"字样
	 * 
	 * @param task
	 * @return 正常返回true
	 */
	private boolean checkTaskOK(TaskDistribute task) {
		//没有手机号肯定是不行的
		if (task == null || StringUtils.isBlank(task.getCustomerMobile()) || StringUtils.isBlank(task.getStore())) {
			return false;
		}
		else{
			//如果有客户姓名，姓名中包含无效，设置成无效
			if(StringUtils.isNotBlank(task.getCustomerName()) && task.getCustomerName().contains("无效")) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 后台系统任务添加 新增任务/客户/房屋，使用默认的重复CD
	 *
	 * @param customerHouse 是否有房屋信息
	 * @param customerHouse 房屋信息
	 * @param taskDistribute 任务
	 * @param newTaskType 新任务阶段
	 * @return 添加成功与否信息
	 */
	public Object addBackTask(Boolean showHouseFlag,String newTaskType, CustomerHouse customerHouse, TaskDistribute taskDistribute) {
		return addBackTask(showHouseFlag,newTaskType,customerHouse,taskDistribute,null);
	}


	/**
	 * 后台系统任务添加 新增任务/客户/房屋，指定重复CD
	 *
	 * @param customerHouse 是否有房屋信息
	 * @param customerHouse 房屋信息
	 * @param taskDistribute 任务
	 * @param newTaskType 新任务阶段
	 * @param repeatCDSecs 同一个客户的两个任务认为是重复的时间间隔
	 * @return 添加成功与否信息
	 * @author Paul
	 */
	@Transactional(rollbackFor = RuntimeException.class)
	public Object addBackTask(Boolean showHouseFlag,String newTaskType, CustomerHouse customerHouse, TaskDistribute taskDistribute,Integer repeatCDSecs) {
		Object result = null;
		try {
			List<TaskDistribute> newTasks = new ArrayList<TaskDistribute>();
			newTasks.add(taskDistribute);
			// 派发方式:人工--选择了跟单员就是 手工派单
			if (StringUtils.isNotBlank(taskDistribute.getMechandiser())) {
				taskDistribute.setDistributeModel(TaskDistributeModel.HAND.toString());
			}
			// 设置门店: 如果是空,就取当前登录人所在门店;
			if (StringUtils.isBlank(taskDistribute.getStore())) {
				taskDistribute.setStore(WebUtils.getLoggedUser().getStoreCode());
			}
			//设置创建人Id
			taskDistribute.setCreator(WebUtils.getLoggedUserId()+"");
			
			// 1.调用任务添加方法,添加任务/客户
			List<TaskDistribute> resNewTasks = null;
			//如果设定了认为任务重复的时间，则以设定的为主
			if (repeatCDSecs == null) {
				resNewTasks = addNewTasks(newTasks, taskDistribute.getDataSource());
			} else {
				resNewTasks = addNewTasks(newTasks, taskDistribute.getDataSource(), repeatCDSecs.intValue());
			}

			if (resNewTasks == null) {
				// 新增任务失败
				return StatusDto.buildFailure("新增任务失败!");
			}
			// 获取返回的任务对象
			TaskDistribute newTaskDistribute = resNewTasks.get(0);
			if (TaskTypeStatus.REPEAT.name().equals(newTaskDistribute.getType())) {
				return StatusDto.buildFailure("创建失败,此线索已经存在!!");
			} else if(TaskTypeStatus.ABNORMAL.name().equals(newTaskDistribute.getType())){
				return StatusDto.buildFailure("创建失败,此线索必要信息错误,如门店,来源等信息错误!");
			}else if(TaskTypeStatus.OTHER.name().equals(newTaskDistribute.getType())){
				return StatusDto.buildFailure("创建失败,此线索不合法,请重新检查后提交!");
			}

			// 2.添加房屋信息
			if(showHouseFlag){
				customerHouse.setCustomerNo(newTaskDistribute.getCustomerNo());
				customerHouse.setTaskNo(newTaskDistribute.getTaskNo());
				customerHouse.setCreateTime(new Date());
				customerHouse.setCreateUser(WebUtils.getLoggedUserId());
				customerHouseDao.insert(customerHouse);
			}
			// 选择了跟单员,就进行派发
			if (StringUtils.isNotBlank(newTaskDistribute.getMechandiser())) {
				updateTask(newTaskType, newTaskDistribute.getTaskNo(), null, newTaskDistribute.getMechandiser(),
						WebUtils.getLoggedUserId(), false,1);
			}
			result = StatusDto.buildSuccess("新增任务成功!");
		} catch (Exception e) {
			result = StatusDto.buildSuccess("新增任务失败!");
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * @author Ryze 修改派单员
	 * @param type
	 *            派单任务类型 转派传null
	 * @param taskNo
	 *            派单任务编号
	 * @param jobNum
	 *            现指派员工编号
	 * @param oldJobNum
	 *            原来员工编号,没有传null
	 * @param userId
	 *            当前操作人id
	 * @param isWchat
	 *            转派区别是微信还是pc 派发没用
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public StatusDto updateTask(String type, String taskNo, String oldJobNum, String jobNum, Long userId,
			Boolean isWchat,Integer applyId) {
		// 根据 oldJobNum 判断是false-转派 还是true-派发
		// 微信端 查询原来的跟单员
		if (isWchat) {
			oldJobNum = entityDao.getDistributeTaskByTaskNo(taskNo).getMechandiser();
		}
		if (StringUtils.isEmpty(oldJobNum)) {
			return distributeTask(type, taskNo, jobNum, userId);
		} else {
			return trunDistributeTask(taskNo, oldJobNum, jobNum, userId, isWchat,applyId);
		}
	}

	/**
	 * 派发
	 * 
	 * @param type
	 *            指定派发类型
	 * @param taskNo
	 *            派发的任务编号
	 * @param jobNum
	 *            派发给->客服编号
	 * @param userId
	 *            当前操作人
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	private StatusDto distributeTask(String type, String taskNo, String jobNum, Long userId) {
		TaskDistribute taskDistribute = new TaskDistribute();
		taskDistribute.setTaskNo(taskNo);
		taskDistribute.setMechandiser(jobNum);
		taskDistribute.setDistributeTime(new Date());
		taskDistribute.setDistributeStatus(Constants.AUTO_TASK_DISTRIBUTION_Y);
		// 1.修改任务派发表
		entityDao.update(taskDistribute);
		DistributeRmk distributeRmk = new DistributeRmk();
		distributeRmk.setDistributeMechandiser(jobNum);
		distributeRmk.setTaskNo(taskNo);
		// 指定派发类型 true——派发 false——手动派发
		if (StringUtils.isEmpty(type)) {
			distributeRmk.setType(TaskNodeStatus.INVITATION.toString());
		} else {
			distributeRmk.setType(type);
		}
		distributeRmk.setCreateTime(new Date());
		distributeRmk.setCreateUser(userId);
		// 2.添加派单轨迹
		distributeRmkDao.insert(distributeRmk);
		// 3.修改原先的任务轨迹
		CustomerTaskRmk customerTaskRmk = new CustomerTaskRmk();
		customerTaskRmk.setTaskNo(taskNo);
		customerTaskRmk.setNewFlag("0");
		customerTaskRmkDao.updateByTaskNo(customerTaskRmk);
		// 4.添加新的任务轨迹
		customerTaskRmk.setCreateTime(new Date());
		customerTaskRmk.setNewFlag("1");
		customerTaskRmk.setCustomerNo(entityDao.getDistributeTaskByTaskNo(taskNo).getCustomerNo());
		customerTaskRmk.setCreateUser(userId);
		customerTaskRmk.setMechandiser(jobNum);
		// 指定派发类型 true——派发 false——手动派发
		if (StringUtils.isEmpty(type)) {
			customerTaskRmk.setCurrentStatus(CustomerStatus.NEWCUSTOMER.toString());
		} else {
			// 邀约-> 待邀约 接待->进店
			if (TaskNodeStatus.INVITATION.toString().equals(type)) {
				customerTaskRmk.setCurrentStatus(CustomerStatus.NEWCUSTOMER.toString());
			} else if (TaskNodeStatus.RECEPTION.toString().equals(type)) {
				customerTaskRmk.setCurrentStatus(CustomerStatus.INTOSHOP.toString());
			}

		}
		customerTaskRmkDao.insert(customerTaskRmk);
		callTemplateMessage(jobNum,taskNo);
		return StatusDto.buildSuccess("修改成功");
	}

	/**
	 * 转派
	 * 
	 * @param taskNo
	 * @param oldJobNum
	 * @param jobNum
	 * @param userId
	 * @param isWchat
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	private StatusDto trunDistributeTask(String taskNo, String oldJobNum, String jobNum, Long userId, boolean isWchat , Integer applyId) {
		TaskDistribute taskDistribute = new TaskDistribute();
		taskDistribute.setTaskNo(taskNo);
		taskDistribute.setMechandiser(jobNum);
		taskDistribute.setDistributeTime(new Date());
		// 微信端 修改状态为有效 1
		if (isWchat && applyId!=null) {
			taskDistribute.setStatus(String.valueOf(TaskDistributeStatus.VALID.getIndex()));
		}
		// 1.修改任务派发表
		entityDao.update(taskDistribute);
		// 组装派单轨迹的数据
		DistributeRmk distributeRmk = new DistributeRmk();
		distributeRmk.setDistributeMechandiser(jobNum);
		distributeRmk.setOldMechandiser(oldJobNum);
		distributeRmk.setTaskNo(taskNo);
		// 微信 ->邀约 pc->最新状态
		if (isWchat && applyId!=null ) {
			distributeRmk.setType(TaskNodeStatus.INVITATION.toString());
		} else {
			distributeRmk.setType(distributeRmkDao.getLatestStateOfTask(taskNo).getType());
		}
		distributeRmk.setCreateTime(new Date());
		distributeRmk.setCreateUser(userId);
		// 2.添加派单轨迹
		distributeRmkDao.insert(distributeRmk);
		// 微信端 需要添加任务轨迹
		if (isWchat) {
			// 修改原先的任务轨迹 添加任务轨迹表
			CustomerTaskRmk customerTaskRmk = new CustomerTaskRmk();
			customerTaskRmk.setTaskNo(taskNo);
			customerTaskRmk.setNewFlag("0");
			//查询最新状态
			CustomerTaskRmk customerTaskRmkByTaskNo = customerTaskRmkDao.getCustomerTaskRmkByTaskNo(taskNo);
			customerTaskRmkDao.updateByTaskNo(customerTaskRmk);
			customerTaskRmk.setCreateTime(new Date());
			customerTaskRmk.setNewFlag("1");
			customerTaskRmk.setCustomerNo(entityDao.getDistributeTaskByTaskNo(taskNo).getCustomerNo());
			//微信端客户经理的转派
			if(applyId==null){
				customerTaskRmk.setCurrentStatus(customerTaskRmkByTaskNo.getCurrentStatus());
			}else {
				customerTaskRmk.setCurrentStatus(CustomerStatus.NEWCUSTOMER.toString());
			}
			customerTaskRmk.setCreateUser(userId);
			customerTaskRmk.setMechandiser(jobNum);
			customerTaskRmkDao.insert(customerTaskRmk);
			if(applyId!=null) {
				//微信端的无效客户申请的结果 日程
				scheduleService.handleInvalidateRefuse(applyId, taskNo);
			}
		}
		callTemplateMessage(jobNum,taskNo);
		return StatusDto.buildSuccess("修改成功");
	}

	/**
	 * 发模板消息
	 * @param jobNum
	 * @param taskNo
	 */
	private void callTemplateMessage(String jobNum,String taskNo){
		//通过任务号查询客户信息
		Map<String, String> customerNameAndPhone = entityDao.findCustomerNameAndPhone(taskNo);
		//发模板消息 员工编号 客户姓名  客户电话
		if(customerNameAndPhone!=null){
			String phoneNo = customerNameAndPhone.get("mobile");
			TaskDistributeTemplateUtil.sendTaskDistributeReminder(jobNum, customerNameAndPhone.get("name"), phoneNo);
		}
	}

	/**
	 * 根据用户Id获取最近十条联系人
	 * 
	 * @param userId
	 * @return
	 */
	public List<Map> recentContact(long userId) {
		List<Map> list = this.entityDao.recentContact(userId);

		return list;
	}

	/**
	 * 后台任务列表:查询该客户下的所有任务
	 */
	public PageTable findTaskByCustomerNo(Map<String, Object> params, Pagination page) {
		// 设置排序条件
		params.put("sort", page.getSort());
		// 利用pagehelper分页查询
		PageHelper.offsetPage(page.getOffset(), page.getLimit());
		Page result = (Page) taskDistributeDao.findTaskByCustomerNo(params);
		// 返回查询结果
		return new PageTable(result.getResult(), result.getTotal());
	}

	/**
	 * 根据任务编号查询客户信息
	 * 
	 * @return
	 */
	public Map<String, Object> getCustomerByTaskNo(String taskNo) {
		return taskDistributeDao.getCustomerByTaskNo(taskNo);
	}
	
	
	/**
	 * 
	 * 函数功能描述:验证是否已经为某个电话号创建了任务
	 * @param phoneNum
	 * @return
	 */
	public boolean ifTaskFromDSExcited(String phoneNum,String dataSource){
		int repeatSecondCD = PropertyHolder.getRepeatCDSec();
		List<TaskDistribute> oldTasks = entityDao.queryTaskCreateInPeroudByPhoneAndDS(phoneNum,repeatSecondCD,dataSource);
		if (oldTasks.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 进店
	 * @param taskNo
	 * @param customerNo
	 * @param mechandiser
	 */
	@Transactional(rollbackFor = Exception.class)
	public  void goStore(String taskNo, String customerNo,String mechandiser,Integer userId){
		//添加沟通记录
		Communicate communicate = new Communicate();
		communicate.setCommunicateMode("IN");
		communicate.setCommunicateType("JD");
		communicate.setInvalidFlag("1");
		communicate.setRemark("前台接待");
		communicate.setCreateTime(new Date());
		communicate.setCreateUser(userId);
		communicate.setTaskNo(taskNo);
		communicate.setCustomerNo(customerNo);
		communicateService.insert(communicate);
		//添加任务状态
		CustomerTaskRmk customerTaskRmk = new CustomerTaskRmk();
		customerTaskRmk.setTaskNo(taskNo);
		customerTaskRmk.setNewFlag("0");
		customerTaskRmkDao.updateByTaskNo(customerTaskRmk);
		customerTaskRmk.setCreateTime(new Date());
		customerTaskRmk.setNewFlag("1");
		customerTaskRmk.setCustomerNo(customerNo);
		customerTaskRmk.setCurrentStatus(CustomerStatus.INTOSHOP.toString());
		customerTaskRmk.setCreateUser(Long.valueOf(userId.toString()));
		customerTaskRmk.setMechandiser(mechandiser);
		customerTaskRmkDao.insert(customerTaskRmk);
	}

	@Transactional(rollbackFor = Exception.class)
	public StatusDto taskImport(List<Map<String, String>> tasks, String storeCode, String dataSourceCode) {
		List<TaskDistribute> list = new ArrayList<TaskDistribute>();

		TaskDistribute taskDistribute = null;
		for (Map<String, String> task : tasks) {
			taskDistribute = new TaskDistribute();

			taskDistribute.setCustomerName(task.get("name"));
			taskDistribute.setCustomerMobile(task.get("mobile"));
			taskDistribute.setPromoteSource(task.get("dataSource"));

			// 派发方式:人工
			taskDistribute.setDistributeModel(TaskDistributeModel.HAND.toString());
			// 设置门店
			taskDistribute.setStore(storeCode);
			//设置来电时间
			taskDistribute.setCallTime(new Date());

			list.add(taskDistribute);
		}

		// 1.调用任务添加方法,添加任务/客户
		List<TaskDistribute> resNewTasks = addNewTasks(list, dataSourceCode);

		return StatusDto.buildSuccess("导入成功,成功数:" + resNewTasks.size());
	}

	/**
	 * 带条件的 导出 数据
	 * @author Paul
	 * @date 2017年7月7日 下午3:02:47
	 * @param params 条件
	 * @return
	 */
	public List<TaskDistribute> findExportTaskDistribute(Map<String, Object> params) {
		return taskDistributeDao.findExportTaskDistribute(params);
	}

	/**
	 * 根据任务号查询该任务对应的客户在订单系统中的客户ID
	 * @param taskNo
	 * @return
	 */
	public String findCustomerIdInOrdByTaskNo(String taskNo) {
		return this.entityDao.findCustomerIdInOrdByTaskNo(taskNo);
	}
	/**
	 * @author Ryze
	 * @date 2017-7-17
	 * 报表中心
	 * 统计任务数量
	 * @param map 条件
	 * @return
	 */
	public List<Report>countReportSourceOrChannel(Map<String, Object> map) {
		return  entityDao.countReportSourceOrChannel(map);
	}

	/**
	 * @author Ryze
	 * @date 2017-7-17
	 * 报表中心
	 * 统计进店数
	 * @param map
	 * @return
	 */
	public Long countInshop(Map<String, Object> map){
		return  entityDao.countInshop(map);
	}

	/**
	 * 通过推荐人id,去获取该推荐人下 所有的任务列表
	 * @param introducerId 推荐人id
	 * @return list
	 * @author: Paul
	 * @date 2017-8-9
	 */
	public List<TaskDistribute> findTaskByIntroducerId(String introducerId) {
		return taskDistributeDao.findTaskByIntroducerId(introducerId);
	}


	/**
	 * 去除手机号重复的线索
	 * **/
	public static void removeDuplicateCusPhoneNoWithOrder(List<TaskDistribute> taskList)
	{
		Set set = new HashSet();
		List<TaskDistribute> newList = new ArrayList<TaskDistribute>();
		for(TaskDistribute task : taskList){
			if (set.add(task.getCustomerMobile())) {
				newList.add(task);
			}
		}

		taskList.clear();
		taskList.addAll(newList);
	}


	/**
	 * 查询小美返单在指定门店的 进店了的任务
	 *
	 * @param storeCode
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public StatusDto queryCustomerIntoShop(String startTime,String endTime){
		List<Map<String,Object>> inShopInfoMapList = queryTaskInfoWithState("XMFD",
				CustomerStatus.INTOSHOP.toString(),startTime,endTime);
		//数据查询失败
		if (inShopInfoMapList == null){
			return StatusDto.buildFailure("查询任务线索进店信息失败");
		}
		//正常，限制返回的字段
		List<Map<String,Object>> inshopResultList = new ArrayList<Map<String,Object>>();
		for (Map<String,Object> infoMap : inShopInfoMapList){
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap.put("customerName",infoMap.get("customerName"));
			resultMap.put("customerMobile",infoMap.get("cusMobile"));
			resultMap.put("inTime",infoMap.get("happenTime"));
			resultMap.put("tallContent","");
			inshopResultList.add(resultMap);
		}
		return StatusDto.buildSuccess(inshopResultList);
	}


	/**
	 * 查询指定门店指定时间段内的员工进店信息
	 *
	 * @param storeCode
	 * @param dataSource
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Map<String,Object>> queryTaskInfoWithState(String dataSource,String status,String startTime,String endTime){
		//String storeCode, String status, Date startTime,Date endTime
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
			Date startDate = sdf.parse(startTime);
			Date endDate = sdf.parse(endTime);

			Map<String,Object> queryParam = new HashMap<String,Object>();
			queryParam.put("dataSource",dataSource);
			queryParam.put("status",status);
			queryParam.put("startTime",startDate);
			queryParam.put("endTime",endDate);

			return taskDistributeDao.selectTaskInfoWithState(queryParam);
		}
		catch (Exception exp){
			return null;
		}
	}


	/**
	 * 判断任务是否可以自动派发
	 * @return
	 */
	public boolean ifTaskAbleAutoDistribute(TaskDistribute newTask){
		/* 推算任务是否需要系统自动分配 */
		//如果任务重设置了跟单员，则在后续操作中系统自动以手动方式派发
		if(StringUtils.isNotBlank(newTask.getMechandiser())) {
			newTask.setDistributeModel(TaskDistributeModel.HAND.toString());
		}

		//还未确定 setDistributeModel，则根据规则来确定
		if (StringUtils.isBlank(newTask.getDistributeModel())) {
			//todo 等待启用 新的判断任务是否可以自动派发
			/*//根据门店、数据来源、推广渠道判断任务是否 可以自动派发
			TaskAllowautoRules param = new TaskAllowautoRules();
			param.setStoreCode(newTask.getStore());
			param.setPromoteCode(newTask.getPromoteSource());
			param.setDataSourceCode(newTask.getDataSource());

			if(taskAllowautoRulesService.getTaskAutomaticallyDistributed(param)){
				newTask.setDistributeModel(TaskDistributeModel.SYSTEM.toString());
			}
			else{
				newTask.setDistributeModel(TaskDistributeModel.HAND.toString());
			}*/

			//临时使用老的判断逻辑
			DataSource tasksDs = dsDao.getDataSourceByCode(newTask.getDataSource());
			if ("Y".equals(tasksDs.getAutoDistribute())) {
				newTask.setDistributeModel(TaskDistributeModel.SYSTEM.toString());
			} else {
				newTask.setDistributeModel(TaskDistributeModel.HAND.toString());
			}
		}

		return TaskDistributeModel.SYSTEM.toString().equals(newTask.getDistributeModel());
	}

	/**
	 * 更改指定线索任务的订单状态
	 * @return
	 */
	public StatusDto updateTaskStatus(String taskNo,String newStatus){
		try{
			entityDao.updateTaskStatus(taskNo,newStatus);
			return StatusDto.buildSuccess("线索状态更新成功");
		}
		catch (Exception exp){
			return StatusDto.buildFailure("修改订单状态时发生异常");
		}
	}
	/**
	 * 查询指定时间内创建
	 * 指定门店 的任务详情
	 * @return
	 */
	public List<TaskDistribute> findReportForCommunicateAndOrder(Map<String, Object> map){
		return  entityDao.findReportForCommunicateAndOrder(map);
	}

	/**
	 * 任务关联沟通记录
	 * type: 1 待邀约
	 * @return
	 */
	public Long countPersonCommunicate(Map<String, Object> map){
		return entityDao.countPersonCommunicate(map);
	}

	/**
	 * 任务关联沟通记录
	 * type: 2 已邀约
	 * type: 3 接待
	 * @return
	 */
	public Long countPersonCommunication(Map<String, Object> map){
		return entityDao.countPersonCommunication(map);
	}

	/**
	 * 任务关联沟OrderInfor
	 * type: 1 大定
	 * type: 2 小定
	 * type: 3 退单
	 * @return
	 */
	public Long countPersonOrderInfo(Map<String, Object> map){
		return entityDao.countPersonOrderInfo(map);
	}
}
