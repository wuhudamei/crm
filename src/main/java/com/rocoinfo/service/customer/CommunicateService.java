package com.rocoinfo.service.customer;

import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.entity.customer.Communicate;
import com.rocoinfo.repository.customer.CommunicateDao;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述：沟通记录
 *
 * @author tony
 * @创建时间 2017-06-09 11:39
 */
@SuppressWarnings("all")
@Service
public class CommunicateService extends CrudService<CommunicateDao, Communicate> {

    /**
     * 根据任务号查询该任务的沟通记录列表
     * @param taskNo 任务号
     * @return 沟通列表
     */
    public List<Communicate> findByTaskNo(String taskNo) {
        return this.entityDao.findByTaskNo(taskNo);
    }
}
