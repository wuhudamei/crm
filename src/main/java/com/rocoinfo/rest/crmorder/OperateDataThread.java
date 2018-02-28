package com.rocoinfo.rest.crmorder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rocoinfo.Constants;
import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.entity.employee.Employee;
import com.rocoinfo.service.employee.EmployeeService;
import com.rocoinfo.utils.CrmApiUtil;
import com.rocoinfo.utils.JsonUtils;

/**
 * 数据操作线程
 * 
 * @author andy 2017-6-30 14:30:07
 *
 */
public class OperateDataThread implements Runnable {
	
	private static Logger logger = LoggerFactory.getLogger(OperateDataThread.class);// 日志
	
	private static final String CODE = "code";
	
	private static final String DATA = "data";

	/** id **/
	private Long id;
	/** 员工姓名 **/
	private String empName;
	/** 员工手机号 **/
	private String mobile;
	private EmployeeService employeeService;

	public OperateDataThread(Long id, String empName, String mobile, EmployeeService employeeService) {
		this.id = id;
		this.empName = empName;
		this.mobile = mobile;
		this.employeeService = employeeService;
	}

	@Override
	public void run() {
		//todo 新版接口中无需执行该流程——同步员工客服在老订单流转中的uuid
		if(!CrmApiUtil.shouldUseOldApi()){
			return;
		}
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("realName", empName);
		paramMap.put("mobile", mobile);
		String ordStr = "";
		try {
			ordStr = CrmApiUtil.post(PropertyHolder.getCrmApiUrl() + Constants.MACTH_EMPID_URL, paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("访问订单系统查询员工UUID失败,错误信息:" + e);
		}
		if(StringUtils.isNotBlank(ordStr)){
			Map<String, Object> jsonMap = JsonUtils.fromJsonAsMap(ordStr, String.class, Object.class);
			if("1".equals(jsonMap.get(CODE).toString())){ //查询到客服信息
				Map<String,String> dataMap = (Map<String,String>)jsonMap.get(DATA);
				String empUuid = dataMap.get("id");
				Employee employee = new Employee();
				employee.setId(id);
				employee.setEmpIdinord(empUuid);
				employeeService.update(employee);
			}
		}
	}
}
