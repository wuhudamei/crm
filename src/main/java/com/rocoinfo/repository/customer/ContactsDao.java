package com.rocoinfo.repository.customer;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.customer.Contacts;
import org.springframework.stereotype.Repository;

/**
 * 描述：
 *
 * @author tony
 * @创建时间 2017-06-08 18:42
 */
@SuppressWarnings("all")
@Repository
public interface ContactsDao extends CrudDao<Contacts>{
}
