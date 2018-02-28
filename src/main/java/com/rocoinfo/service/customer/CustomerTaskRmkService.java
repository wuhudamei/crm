package com.rocoinfo.service.customer;

import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.entity.customer.CustomerTaskRmk;
import com.rocoinfo.repository.customer.CustomerTaskRmkDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 描述：任务轨迹
 *
 * @author tony
 * @创建时间 2017-06-14 13:42
 */
@SuppressWarnings("all")
@Service
public class CustomerTaskRmkService extends CrudService<CustomerTaskRmkDao, CustomerTaskRmk> {

    /**
     * 查询任务轨迹
     * @param map
     * @return
     */
    public List<CustomerTaskRmk> search(Map<String, Object> map) {
        return this.entityDao.search(map);
    }

    public void updateByTaskNo(CustomerTaskRmk customerTaskRmk) {
        this.entityDao.updateByTaskNo(customerTaskRmk);
    }
    /**
     * 根据任务号查看最新状态
     * @param TaskNo
     * @return
     */
   public CustomerTaskRmk getCustomerTaskRmkByTaskNo(String taskNo){
       return  entityDao.getCustomerTaskRmkByTaskNo(taskNo);
   }
}
