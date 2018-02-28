package com.rocoinfo.rest.crmorder;

import com.rocoinfo.service.employee.EmployeeService;

/**
 * 订单系统操作
 * 
 * @author Andy 2017-6-30 14:27:20
 *
 */
public class OrderSystemOpt {

	/**
	 * 同步订单系统员工ID
	 * @param id	id
	 * @param empName	员工姓名
	 * @param mobile	手机号
	 * @param employeeService
	 */
	public static void synOrderSystemEmpId(Long id, String empName, String mobile, EmployeeService employeeService) {
		Thread handlHybrisReq = new Thread(new OperateDataThread(id, empName, mobile, employeeService));
		handlHybrisReq.start();// 启用异步处理同步订单系统员工号
	}
}
