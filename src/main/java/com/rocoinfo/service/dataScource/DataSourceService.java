package com.rocoinfo.service.dataScource;

import java.util.List;
import java.util.Map;

import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.entity.dataSource.DataSource;
import com.rocoinfo.repository.dataSource.DataSourceDao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <dl>
 * <dd>Description: 美得你crm 数据来源 service</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/7</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@Service
public class DataSourceService extends CrudService<DataSourceDao,DataSource> {
	
	
	/**
	 * 
	 * 函数功能描述:根据调用者Id查询数据来源配置
	 * @param callId
	 * @return
	 */
	public DataSource getDataSourceByCallId(String callId){
		return entityDao.getDataSourceByCallId(callId);
	}
	
	
	/**
	 * 
	 * 函数功能描述:同步当前启用的dataSourc的callId和callSecret到全局map中
	 */
    public void syncCallCertificateMap(){
    	/*清除map中的老数据*/
    	PropertyHolder.callCertificateMap.clear();
    	
        Map<String, String> callCertificateMap = PropertyHolder.callCertificateMap;
        List<DataSource> all = entityDao.findAll();
        for (DataSource source:all) {
        	
            String callId = source.getCallId();
            String callSecret = source.getCallSecret();
            //验证改数据源的callId和secret是否可以同步到map中
            if("1".equals(source.getStatus()) && StringUtils.isNotBlank(callId) && StringUtils.isNotBlank(callSecret))
            	callCertificateMap.put(callId, callSecret);
        }
    }
	
}
