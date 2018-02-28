package com.rocoinfo.service.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.entity.customer.CustomerHouse;
import com.rocoinfo.repository.customer.CustomerHouseDao;

/**
 * 描述：房屋信息
 *
 * @author tony
 * @创建时间 2017-06-09 15:16
 */
@SuppressWarnings("all")
@Service
public class CustomerHouseService extends CrudService<CustomerHouseDao, CustomerHouse>{
	
	@Autowired
	private CustomerHouseDao customerHouseDao;
    /**
     * 根据任务编号查找该编号下的房屋列表，一个任务可能有多个房屋
     * @param taskNo 任务编号
     * @return 房屋列表
     */
    public List<CustomerHouse> findByTaskNo(String taskNo) {
        return this.entityDao.findByTaskNo(taskNo);
    }

    /**
     * 通过客户编码 查询房屋信息列表
     * @param customerNo 客户编码
     * @return
     */
	public List<CustomerHouse> findCustomerHouseByCustomerNo(String customerNo) {
		return customerHouseDao.findCustomerHouseByCustomerNo(customerNo);
	}

    /**
     * 查找该任务已生成的订单的房屋列表
     * @param taskNo
     * @return
     */
    public List<CustomerHouse> findOrderListByTaskNo(String taskNo) {
        return this.entityDao.findOrderListByTaskNo(taskNo);
    }

    /**
     * 通过订单号更新房屋/订单状态
     * @param status
     * @param orderId
     */
    public void updateOrderStatusByOrderId(int status, String orderId) {
        this.entityDao.updateOrderStatusByOrderId(status, orderId);
    }
}
