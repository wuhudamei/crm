package com.rocoinfo.repository.callCentre;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.rocoinfo.entity.callCentre.CustomerCallInLog;

/**
 * 功能描述:话务中心Dao接口
 * @author phil
 * 2017年6月29日
 */

@Repository
public interface CallCentreDao {
	
	
	/**
	 * 
	 * 函数功能描述:通过jlId查询成功呼入记录
	 * @param jlID
	 * @return
	 */
	public List<CustomerCallInLog> queryCallInLogListByjlID(String jlID);
	
	
	
	/**
	 * 
	 * 函数功能描述:插入单个客户成功呼入记录
	 * @param callInLog
	 */
	public void insertCallInLog(CustomerCallInLog callInLog);
}
