package com.rocoinfo.repository.abnormalData;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.abnormalData.AbnormalData;
import org.springframework.stereotype.Repository;

/**
 * <dl>
 * <dd>Description: 美得你crm 异常数据监控 dao </dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/7</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@Repository
public interface AbnormalDataDao extends CrudDao<AbnormalData> {
}
