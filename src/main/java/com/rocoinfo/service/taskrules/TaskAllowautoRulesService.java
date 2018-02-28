package com.rocoinfo.service.taskrules;

import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.entity.taskrules.TaskAllowautoRules;
import com.rocoinfo.repository.taskrules.TaskAllowautoRulesDao;
import com.rocoinfo.shiro.ShiroUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <dl>
 * <dd>Description: 美得你智装 任务派法规则 service </dd>
 * <dd>@date：2017/10/23  12:57</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@Service
public class TaskAllowautoRulesService extends CrudService<TaskAllowautoRulesDao, TaskAllowautoRules> {
    /**
     * @author Ryze
     * @date 2017/10/23 14:42
     * @description 批量添加
     * @param rulesList 要添加的规则
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertBatch(List<TaskAllowautoRules> rulesList,ShiroUser loggedUser) {
        if(rulesList!=null) {
            List<TaskAllowautoRules> all = entityDao.findAllThreeColumn();
            if(all!=null){
                List<TaskAllowautoRules> collect = rulesList.stream().filter(a -> !all.contains(a)).collect(Collectors.toList());
                rulesList=collect;
                rulesList.stream().forEach(a->{a.setCreatorId(loggedUser.getId().toString());
                    a.setCreatorName(loggedUser.getName()); a.setCreateTime(new Date());});
            }
            if(rulesList!=null && rulesList.size()>0) {
                entityDao.insertBatch(rulesList);
            }
        }
    }
    /**
     * @author Ryze
     * @date 2017/10/23 14:47
     * @description 判断该规则是否存在
     * @param
     * @return
     */
    public Boolean getTaskAutomaticallyDistributed(TaskAllowautoRules taskAllowautoRules){
        return Optional.ofNullable(entityDao.getTaskAutomaticallyDistributed(taskAllowautoRules)).map(a->{if(a>0){return true;}else{return false;}} ).orElse(false);
    }
}
