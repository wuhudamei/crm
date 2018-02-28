package com.rocoinfo.repository.task;

/**
 * <dl>
 * <dd>Description: 美得你crm 任务派单轨迹 Dao</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/15</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.task.DistributeRmk;
import org.springframework.stereotype.Repository;

@SuppressWarnings("all")
@Repository
public interface DistributeRmkDao extends CrudDao<DistributeRmk> {
    /**
     * 根据任务编号查询 最新的轨迹
     * @param taskNo
     * @return
     */
    DistributeRmk getLatestStateOfTask(String taskNo);
}
