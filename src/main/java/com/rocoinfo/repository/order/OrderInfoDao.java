package com.rocoinfo.repository.order;


import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.order.SyncOrderInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：同步订单信息
 *
 * @author tony
 * @创建时间 2017-07-17 15:35
 */
@SuppressWarnings("all")
public interface OrderInfoDao extends CrudDao<SyncOrderInfo> {
    void updateList(@Param("list") List<SyncOrderInfo> list);
}
