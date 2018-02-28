package com.rocoinfo.service.customer;

import com.rocoinfo.entity.customer.ApplyPosition;
import com.rocoinfo.repository.customer.ApplyPositionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述：
 *
 * @author tony
 * @创建时间 2017-06-16 17:10
 */
@SuppressWarnings("all")
@Service
public class ApplyPositionService {

    @Autowired
    private ApplyPositionDao applyPositionDao;

    public List<ApplyPosition> all() {
        return applyPositionDao.all();
    }
}
