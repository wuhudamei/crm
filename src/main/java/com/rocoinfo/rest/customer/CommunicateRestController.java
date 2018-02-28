package com.rocoinfo.rest.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.rocoinfo.common.BaseController;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.dto.page.PageTable;
import com.rocoinfo.dto.page.Pagination;
import com.rocoinfo.service.customer.CommunicateService;
import com.rocoinfo.utils.MapUtils;

/**
 * 沟通记录controller
 * @author Paul
 * 2017年6月12日
 */
@RestController
@RequestMapping("/api/communicate")
public class CommunicateRestController extends BaseController {
	
	@Autowired
	private CommunicateService communicateService;
	
	/**
	 * 通过customerNo查询
	 * 带分页的条件查询  列表
	 * @param communicateType 沟通类型
	 */
	@RequestMapping("/list")
	public Object list(@RequestParam(value = "communicateMode", required = false) String communicateMode,
			@RequestParam(value = "communicateType", required = false) String communicateType,
			@RequestParam(value = "customerNo", required = false) String customerNo,
			@RequestParam(required = false, defaultValue = "0") Integer offset, 
			@RequestParam(required = false, defaultValue = "20") Integer limit,
			@RequestParam(defaultValue = "id") String orderColumn,
			@RequestParam(defaultValue = "DESC") String orderSort ) {
		
		if(StringUtils.isBlank(customerNo)){
			List reuslt = new ArrayList();
			PageTable page = new PageTable(reuslt, reuslt.size());
			return StatusDto.buildSuccess(page);
		}
		Map<String, Object> params = Maps.newHashMap();
		MapUtils.putNotNull(params, "communicateMode", communicateMode);
		MapUtils.putNotNull(params, "communicateType", communicateType);
		MapUtils.putNotNull(params, "customerNo", customerNo);
		params.put("sort", new Sort(Sort.Direction.valueOf(orderSort), orderColumn));
		
		PageTable page = communicateService.searchScrollPage(params, new Pagination(offset, limit));

		return StatusDto.buildSuccess(page);
	}
	
	
}
