package com.rocoinfo.rest.customer;

import java.util.Date;
import java.util.Map;

import com.rocoinfo.entity.employee.Employee;
import com.rocoinfo.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.rocoinfo.common.BaseController;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.dto.page.PageTable;
import com.rocoinfo.dto.page.Pagination;
import com.rocoinfo.entity.customer.Contacts;
import com.rocoinfo.service.customer.ContactsService;
import com.rocoinfo.utils.MapUtils;

/**
 * 描述：联系人
 *
 * @author tony
 * @创建时间 2017-06-08 19:17
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/api/contacts")
public class ContactsController extends BaseController {

	@Autowired
	private ContactsService contactsService;

	/**
	 * 带分页的条件查询 列表
	 * 
	 * @param
	 */
	@RequestMapping("/list")
	public Object list(
			@RequestParam(value = "customerNo", required = false) String customerNo,
			@RequestParam(required = false, defaultValue = "0") Integer offset,
			@RequestParam(required = false, defaultValue = "20") Integer limit,
			@RequestParam(defaultValue = "id") String orderColumn,
			@RequestParam(defaultValue = "DESC") String orderSort) {

		Map<String, Object> params = Maps.newHashMap();
		MapUtils.putNotNull(params, "customerNo", customerNo);
		params.put("sort", new Sort(Sort.Direction.valueOf(orderSort), orderColumn));

		PageTable page = contactsService.searchScrollPage(params, new Pagination(offset, limit));

		return StatusDto.buildSuccess(page);
	}

	/**
	 * 添加联系人
	 * @param contacts 联系人对象
	 * @return
	 */
    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public Object createContacts(@RequestBody Contacts contacts) {

        contacts.setCreateTime(new Date());

        Employee e = new Employee();
        e.setId(WebUtils.getLoggedUserId());
        contacts.setCreateUser(e);

        contactsService.insert(contacts);
        return StatusDto.buildSuccess("添加成功");
    }
}
