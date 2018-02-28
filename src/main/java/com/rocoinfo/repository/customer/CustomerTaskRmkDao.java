package com.rocoinfo.repository.customer;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.customer.CustomerTaskRmk;
import org.springframework.stereotype.Repository;

/**
 * 描述：客户轨迹
 *
 * @author tony
 * @创建时间 2017-06-14 13:34
 */
@SuppressWarnings("all")
@Repository
public interface CustomerTaskRmkDao extends CrudDao<CustomerTaskRmk> {
    public void updateByTaskNo(CustomerTaskRmk updateByTaskNo);

    /**
     * 根据任务号查看最新状态
     * @param TaskNo
     * @return
     */
    CustomerTaskRmk getCustomerTaskRmkByTaskNo(String taskNo);
}
