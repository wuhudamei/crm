package com.rocoinfo.repository.customer;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.customer.Communicate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述：沟通记录
 *
 * @author tony
 * @创建时间 2017-06-09 11:37
 */
@SuppressWarnings("all")
@Repository
public interface CommunicateDao extends CrudDao<Communicate> {
    /**
     * 根据任务号查询该任务的沟通记录列表
     * @param taskNo 任务号
     * @return 沟通列表
     */
    public List<Communicate> findByTaskNo(@Param("taskNo") String taskNo);
}
