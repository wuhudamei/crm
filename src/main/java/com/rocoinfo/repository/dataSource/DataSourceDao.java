package com.rocoinfo.repository.dataSource;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.dataSource.DataSource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <dl>
 * <dd>Description: 美得你crm 数据来源dao</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/7</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@Repository
public interface DataSourceDao extends CrudDao<DataSource> {

	
	/**
	 * 
	 * 函数功能描述:根据调用者id查询数据来源配置
	 * @param callId
	 * @return
	 */
	public DataSource getDataSourceByCallId(@Param("callId") String callId);
	
	
	/**
	 * 
	 * 函数功能描述:根据来源编号查询数据来源配置
	 * @param dsCode
	 * @return
	 */
	public DataSource getDataSourceByCode(@Param("dsCode") String dsCode);
}
