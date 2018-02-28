package com.rocoinfo.repository.customer;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.customer.Customer;

/**
 * 描述：
 *
 * @author tony
 * @创建时间 2017-06-07 14:03
 */
@Repository
public interface CustomerDao extends CrudDao<Customer> {
    /**
     * 根据客户姓名等返回客户列表
     * @param list 客户姓名
     * @return 客户列表
     */
    public List<Customer> getCustomers(@Param("statusList") List<String> list);

    /**
     * 通过CustomerNo查询 客户信息
     * @param customerNo 客户编号
     * @return
     */
	public Customer getCustomerByCustomerNo(String customerNo);

	/**
	 * 通过CustomerNo查询 客户信息
	 * @param customerNo 客户编号
	 * @param taskNo 任务编号
	 * @return
	 */
	public Customer getCustomerByCustomerNoAndTaskNo(@Param("customerNo") String customerNo, @Param("taskNo") String taskNo);
	
	/**
	 * 
	 * 函数功能描述:通过手机号查寻客户信息
	 * @param cusMobile
	 * @return
	 */
	public List<Customer> getCustomerByMobileNo(String cusMobile);
	

	/**
	 * 
	 * 函数功能描述:批量插入客户
	 * @param newCusList
	 */
	public void insertCustomerBatch(@Param("newCusList") List<Customer> newCusList);
	
	
	/**
     * 查询记录数
     *
     * @param params 查询条件
     * @return
     */
    Long searchTotal(Map<String, Object> params);
}
