package com.rocoinfo.repository.customer;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.customer.InvalidateUserApply;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * 描述：无效客户申请
 *
 * @author tony
 * @创建时间 2017-06-13 14:47
 */
@SuppressWarnings("all")
@Repository
public interface InvalidateUserApplyDao extends CrudDao<InvalidateUserApply> {

    /**
     * 根据申请编号申请详情数据
     * @param applyNo 申请编号
     * @return
     */
    public InvalidateUserApply findDetailByApplyNo(@Param("applyNo") String applyNo);

    /**
     *
     * @param map
     *    {
     *       mechandiser 跟单号
     *       position 岗位
     *    }
     * @return
     */
    public List<Map> searchApplys(Map map);
}
