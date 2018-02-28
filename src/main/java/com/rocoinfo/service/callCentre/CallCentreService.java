package com.rocoinfo.service.callCentre;

import com.rocoinfo.entity.callCentre.CustomerCallInLog;
import com.rocoinfo.entity.task.TaskDistribute;
import com.rocoinfo.repository.callCentre.CallCentreDao;

import com.rocoinfo.rest.task.TaskFromBJCallCentreApiController;
import com.rocoinfo.service.task.TaskDistributeService;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 描述：
 *
 * @author phil
 * @创建时间 2017-06-29 17:10
 */
@SuppressWarnings("all")
@Service
public class CallCentreService {

	private static String DATASOURCE_CODE_HWZX = "HWZX";

	private static Logger logger = LoggerFactory.getLogger(TaskFromBJCallCentreApiController.class);// 日志

	@Autowired
	private TaskDistributeService taskService;

	@Autowired
	private CallCentreDao callCentrDao;
	
	
	/**
	 * 
	 * 函数功能描述:通过ljId查询是否已经存在客户成功呼入记录
	 * @param jlId
	 * @return
	 */
	public boolean ifCallInLogExcited(String jlId){
		List<CustomerCallInLog> callInLogList = callCentrDao.queryCallInLogListByjlID(jlId);
		if(callInLogList.size()>0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
	/**
	 * 
	 * 函数功能描述:持久化单个客户成功呼入记录
	 * @param callInLog
	 */
	public void addCustomerCallInLog(CustomerCallInLog callInLog){
		callCentrDao.insertCallInLog(callInLog);
	}



	/**
	 * 将老话务中心的Datatransfer项目传来的json数据转成crm中的线索任务，并且保存
	 * @param reqListStr
	 * @return
	 */
	public JSONObject saveTaskFromOldCallCentre(String reqListStr){
		//返回结果
		JSONObject jSONObjects = new JSONObject();
		JSONObject errJson = new JSONObject();

		List<TaskDistribute> newTaskList = new ArrayList<TaskDistribute>();
		try{
			//事先准备好出错的对象
			errJson.put("message", "fail");
			errJson.put("msg", "程序出现异常");

			logger.debug("dataTransfer传来的数据："+reqListStr);

			JSONArray reqJsonArr = new JSONArray(reqListStr);



			String ids = null;
			String cids = null;
			String ldxxIDS = "";

			for (int i = 0; i < reqJsonArr.length(); i++) {
				JSONObject taskObj = reqJsonArr.getJSONObject(i);//json对象
				//客户来电信息--接通电话的记录，用来插入
				if (taskObj.has("InfoType")&& taskObj.getString("InfoType").equalsIgnoreCase("CALLINFO")) {
					if(ldxxIDS == ""){
						ldxxIDS =  inserCustomerCallInLog(taskObj);
					}else{
						ldxxIDS = ldxxIDS+","+ inserCustomerCallInLog(taskObj);
					}

					continue;
				}else{
					/*要转成任务的数据，包括两部分
					 * 1、话务人员在大唐开发的easyCrm中保存的客户信息
					 * 2、未接通的通话记录
					 */
					String[] arr = new  String[2];

					/*构造单个任务*/
					arr=  buildTask(taskObj,newTaskList);

					if("CIDs".equals(arr[0])){
						if (cids == null) {
							cids = arr[1];
						} else {
							cids = cids + "," + arr[1];
						}
					}else if(("IDs").equals(arr[0])){
						if (ids == null) {
							ids = arr[1];
						} else {
							ids = ids + "," + arr[1];
						}
					}
				}
			}


			/*持久化任务*/
			if(newTaskList.size()>0) {
				taskService.addNewTasks(newTaskList, DATASOURCE_CODE_HWZX);
			}


			/*把任务生成结果回馈给DataTransfer项目*/
			if(!("".equals(ldxxIDS)) ){//只传进线信息时候，出现了重复信息
				jSONObjects.put("message", "fail");
				jSONObjects.put("客户进线信息ID", ldxxIDS);
				jSONObjects.put("msg", "客户进线信息ID重复");
			}
			if (cids == null) {
				//正常情况
				if (ids == null && ldxxIDS == "") {//两种信息都传并且进线信息的ID不重复  或者只传不是进线信息的数据
					jSONObjects.put("message", "ok");
				}if (ids == null && ldxxIDS!= "") {//两种信息都传但是，进线信息的ID重复
					jSONObjects.put("message", "fail");
				} else if(ids!=null) {
					jSONObjects.put("message", "fail");
					jSONObjects.put("IDs", ids);
					jSONObjects.put("msg", "客户和客户电话不能为空");
				}
			}else{
				if(ldxxIDS!=""){
					/*jSONObjects.put("msg", "CUSTOMERID,Form_Id,Company_Id,客户进线信息ID重复");*/
					jSONObjects.put("msg", "CUSTOMERID,客户进线信息ID重复");
				}else{
					/*jSONObjects.put("msg", "CUSTOMERID,Form_Id,Company_Id有重复");*/
					jSONObjects.put("msg", "CUSTOMERID有重复");
				}
				jSONObjects.put("message", "fail");
				jSONObjects.put("CIDs", cids);

			}
			return jSONObjects;
		}
		catch(Exception exp){
			exp.printStackTrace();
			return errJson;
		}
	}



	/**
	 * 传的InfoType这个参数的值不等于CALLINFO的情况下 执行该方法  也就是插入客户信息的方法
	 * cid = CUSTOMERID  com_id = company_id
	 * ids 以上参数的拼接结果
	 * @param object
	 * @return 返回一个String数组
	 */
	private String[] buildTask (JSONObject object,List<TaskDistribute> newTaskList){
		String[] arr = new  String[2];
		//错误的客户信息Id
		String ids = null;
		//重复的customerId
		String cids = null;
		//为了catch中能取到值用的客户信息ID
		String errIdStr = null;

		/**************从dataTransfer中传来的json对象中抽出构造CRM中task所需要的参数 START*************/
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			String keh = object.getString("KEH");// 1.客户
			String kehdh = object.getString("KEHDH");// 2. 客户电话
			String cid = object.getString("CustomerId");// 16. 标识
			errIdStr = cid;

			//如果是无效的话务任务，不往crm中传
			if(StringUtils.isNotBlank(keh) && keh.contains("无效")){
				arr[0] = "message";
				arr[1] =  "ok";
				return arr;
			}

			/*检查一个月内是否已经为该电话分生成过任务*/
			if(taskService.ifTaskFromDSExcited(kehdh,DATASOURCE_CODE_HWZX)){
				logger.debug("话务传来的重复任务，电话:"+kehdh);
				arr[0] = "CIDs";
				arr[1] =  errIdStr;
				return arr;
			}

			Timestamp laidsj = null;
			if ("".equals(object.getString("LAIDSJ"))|| "null".equals(object.getString("LAIDSJ"))
					|| StringUtils.isEmpty(object.getString("LAIDSJ"))|| object.getString("LAIDSJ") == null) {
				laidsj = null;
			} else {
				laidsj = new Timestamp(sdf.parse(object.getString("LAIDSJ")).getTime());// 3.来电时间
			}
			String gdy = "";// object.getString("GENDY"); // 4.跟单员
			String gdyid = "";// object.getString("GENDYID");// 5. 跟单员ID
			String shiy = object.getString("SHIY"); // 6. 事由
			/*
			 * 房产证面积字段数据库中华原本是float类型，改成了varchar 这边的接收类型还没改为varchar
			 */
			String fangczmj = "";
			Timestamp yujzxsj = null;
			String yus = "";
			try {
				fangczmj = StringUtils.isEmpty(object.getString("FCZMJ")) ? null : object.getString("FCZMJ");
			} catch (NumberFormatException e2) {
				fangczmj = "";
			}
			try {
				yus = StringUtils.isEmpty(object.getString("YS")) ? null: object.getString("YS"); // 14.预算
			} catch (NumberFormatException e2) {
				yus = "";
			}
			try {
				if ("".equals(object.getString("YJZXSJ"))|| "null".equals(object.getString("YJZXSJ"))|| StringUtils.isEmpty(object.getString("YJZXSJ")) || object.getString("YJZXSJ") == null) {
					yujzxsj = null;
				} else {
					yujzxsj = new Timestamp(sdf.parse(object.getString("YJZXSJ")).getTime());// 11. 预计装修时间
				}
			} catch (Exception e2) {
				yujzxsj = null;
			}
			String xinfjf = object.getString("XINJF"); // 8.新旧房
			String fangx = object.getString("FANGX"); // 9. 房型
			String address = object.getString("ADDRESS");// 10. 地址
			String xiangxdz = object.getString("XIANGXX");// 17推广渠道
			String shangmldsj = object.getString("SMLDSJ");// 18 上门来电时间
			String shifczrk = object.getString("CZRK"); // 12.是否常住人口
			String fengg = object.getString("FG"); // 13.风格
			String lianxfs = object.getString("LXFS"); // 15. 联系方式


			//门店编号
			int rs = object.toString().indexOf("Company_Id");
			Integer company_id = 0;// 初始化
			Integer com_id = 0;// 初始化
			String create_user = "1";
			if (rs > 0) {
				// 如果传过来的值为空，就给他一个值0，进不去判断，所以查询的时候，用的con_id为0，0可以和int类型的比较
				company_id = StringUtils.isEmpty(object.getString("Company_Id")) ? 0 : Integer.valueOf(object.getString("Company_Id"));

			}
			//工单
			int res = object.toString().indexOf("Form_Id");
			String form_id = null;
			if (res > 0) {// 判断有没有传这个字段，大于0代表传的有这个参数
				form_id = object.getString("Form_Id");
			}
			/**************从dataTransfer中传来的json对象中抽出构造CRM中task所需要的参数 END*************/


			/**************构造TaskDistributed对象*************/
			//通用属性赋值
			TaskDistribute newTask = new TaskDistribute();
			newTask.setCustomerName(keh);
			newTask.setCustomerMobile(kehdh);
			newTask.setCallTime(laidsj);
			newTask.setPromoteSource(xiangxdz);
			newTask.setStore("BJ1");
			newTask.setCreator("system");
			newTask.setRemark(shiy);

			if (cid.startsWith("hs")) {// 呼损，呼叫未接通
				newTask.setCallAnswered("N");
			}else{
				newTask.setCallAnswered("Y");
			}

			//验证任务是否有效
			if (StringUtils.isBlank(keh) && StringUtils.isBlank(kehdh)) {
				ids = cid;
			}

			//任务放入List中
			newTaskList.add(newTask);

			if (cids == null) {
				if (ids == null) {
					arr[0] = "message";
					arr[1] =  "ok";
				} else {
					arr[0] = "IDs";
					arr[1] =  ids;
				}
			} else {
				arr[0] = "CIDs";
				arr[1] =  cids;
			}

		} catch (Exception e) {
			e.printStackTrace();
			arr[0] = "IDs";
			arr[1] =  errIdStr;
		}
		return  arr;
	}



	/**
	 * 插入客户成功来电信息的方法
	 * 传输数据传来的这个参数InfoType的值如果是CALLINFO 就调用这个方法，插入数据到crm_callin_log表中
	 * @param object
	 * @return ids
	 */
	private String inserCustomerCallInLog(JSONObject object) {
		//用来拼接重复的ID
		String ids = "";
		String errJLId = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Timestamp ts = new Timestamp(new Date().getTime());
			String jlID = object.getString("ID");// 1.传过来的ID
			errJLId = jlID;
			String ivrsp = object.getString("IVRSp");// 2.
			String callID = object.getString("CallID");// 3.
			Timestamp startTime = null;
			Timestamp endTime = null;
			Timestamp startQTime = null;
			Timestamp endQTime = null;

			String serviceType = object.getString("ServiceType");// 8.
			String caller = object.getString("Caller");// 9.
			String agentID = object.getString("AgentID");// 10.
			String agentExt = object.getString("AgentExt");// 11.
			String groupID = object.getString("GroupID");// 12.
			String trunkCallee = object.getString("TrunkCallee");// 13.
			String company_Id = StringUtils.isEmpty(object.getString("Company_Id")) ? "0": (object.getString("Company_Id"));// 14.
			try{
				startTime = new Timestamp(sdf.parse(object.getString("StartTime")).getTime());// 4.
				endTime = new Timestamp(sdf.parse(object.getString("EndTime")).getTime());// 5.
				startQTime = new Timestamp(sdf.parse(object.getString("StartQTime")).getTime());// 6.
				endQTime = new Timestamp(sdf.parse(object.getString("EndQTime")).getTime());// 7.
			} catch (ParseException e1) {
				startTime=null;
				endTime = null;
				startQTime = null;
				endQTime = null;
			}
			/**
			 * 通过传过来的ID去数据库中查是否存在了
			 */
			String create_user = "1";

			if (ifCallInLogExcited(jlID)) {
				ids=jlID;
			} else {
				CustomerCallInLog kehldxx = new CustomerCallInLog();
				kehldxx.setCreateDate(ts);
				kehldxx.setJlid(jlID);
				kehldxx.setIvrsp(ivrsp);
				kehldxx.setCallid(callID);
				kehldxx.setStarttime(startTime);
				kehldxx.setEndtime(endTime);
				kehldxx.setStartqtime(startQTime);
				kehldxx.setEndqtime(endQTime);
				kehldxx.setServicetype(Integer.valueOf(serviceType));
				kehldxx.setCaller(caller);
				kehldxx.setAgentid(agentID);
				kehldxx.setAgentext(agentExt);
				kehldxx.setGroupid(groupID);
				kehldxx.setTrunkcallee(trunkCallee);
				kehldxx.setCreateUser(create_user);
				kehldxx.setCompanyId(Integer.parseInt(company_Id));
				//持久化callInLog
				addCustomerCallInLog(kehldxx);
			}
		} catch (Exception e) {
			logger.debug("添加传输客户来电信息失败,失败的原因："+e.getMessage());
			ids = "errJLId";
		}
		return ids;
	}
}
