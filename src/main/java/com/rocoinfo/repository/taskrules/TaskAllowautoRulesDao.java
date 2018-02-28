package com.rocoinfo.repository.taskrules;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.taskrules.TaskAllowautoRules;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <dl>
 * <dd>Description: 美得你智装  任务派法规则 dao</dd>
 * <dd>@date：2017/10/23  12:56</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@Repository
public interface TaskAllowautoRulesDao extends CrudDao<TaskAllowautoRules> {
    /**
     * @author Ryze
     * @date 2017/10/23 14:42
     * @description 批量添加
     * @param list 要添加的规则
     * @return
     */
     void insertBatch(List<TaskAllowautoRules> list);
    /**
     * @author Ryze
     * @date 2017/10/23 14:47
     * @description 判断该规则是否存在
     * @param
     * @return
     */
    Long getTaskAutomaticallyDistributed(TaskAllowautoRules taskAllowautoRules);


    /**
     * 查询所有 只查3个字段
     *
     * @return
     */
    List<TaskAllowautoRules> findAllThreeColumn();

}
