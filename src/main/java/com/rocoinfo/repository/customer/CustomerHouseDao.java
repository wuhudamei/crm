package com.rocoinfo.repository.customer;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.customer.CustomerHouse;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 描述：房屋信息Dao
 *
 * @author tony
 * @创建时间 2017-06-08 09:47
 */
@SuppressWarnings("all")
@Repository
public interface CustomerHouseDao extends CrudDao<CustomerHouse>{
    /**
     * 根据任务编号查询房屋列表
     * @param taskNo 任务编号
     * @return 针对该任务的房屋列表
     */
    public List<CustomerHouse> findByTaskNo(@Param("taskNo") String taskNo);
    
    /**
     * 通过客户编码 查询房屋信息列表
     * @param customerNo 客户编码
     * @return
     */
	public List<CustomerHouse> findCustomerHouseByCustomerNo(String customerNo);

    /**
     * 查找该任务已生成的订单的房屋列表
     * @param taskNo
     * @return
     */
    List<CustomerHouse> findOrderListByTaskNo(@Param("taskNo") String taskNo);

    /**
     * 通过订单ID更新房屋状态
     * @param status
     * @param orderId
     */
    void updateOrderStatusByOrderId(@Param("status") Integer status, @Param("orderId") String orderId);

    /**
     * 根据任务号列表查询订单号
     * @param taskNoList
     * @return
     */
    List<Map> findOrderNoByTaskNoList(@Param("taskNoList") List<String> taskNoList);
}
