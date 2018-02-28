package com.rocoinfo.repository.customer;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.customer.ReturnOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 描述：订单无效申请
 *
 * @author tony
 * @创建时间 2017-06-13 17:04
 */
@SuppressWarnings("all")
@Repository
public interface ReturnOrderDao extends CrudDao<ReturnOrder> {
    /**
     * 根据申请编号查询退单详情
     * @param applyNo 退单编号
     * @return
     */
    public ReturnOrder findDetailByApplyNo(@Param("applyNo") String applyNo);
}
