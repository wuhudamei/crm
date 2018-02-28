package com.rocoinfo.service.customer;

import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.entity.customer.Contacts;
import com.rocoinfo.repository.customer.ContactsDao;
import org.springframework.stereotype.Service;

/**
 * 描述：联系人
 *
 * @author tony
 * @创建时间 2017-06-08 18:45
 */
@SuppressWarnings("all")
@Service
public class ContactsService extends CrudService<ContactsDao, Contacts> {

}
