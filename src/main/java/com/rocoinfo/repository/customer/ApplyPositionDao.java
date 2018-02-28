package com.rocoinfo.repository.customer;

import com.rocoinfo.entity.customer.ApplyPosition;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述：
 *
 * @author tony
 * @创建时间 2017-06-16 16:50
 */
@SuppressWarnings("all")
@Repository
public interface ApplyPositionDao {
    @Select(value = "select position from crm_apply_position")
    List<ApplyPosition> all();
}
