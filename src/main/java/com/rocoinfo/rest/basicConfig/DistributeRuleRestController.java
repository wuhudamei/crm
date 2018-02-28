package com.rocoinfo.rest.basicConfig;

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
import com.rocoinfo.entity.basicConfig.DistributeRule;
import com.rocoinfo.service.basicConfig.DistributeRuleService;
import com.rocoinfo.utils.MapUtils;

/**
 * 	派发规则 controller
 * 
 * @author Paul
 * @time 2017-06-06 20:23:01
 *
 */
@RestController
@RequestMapping(value = "/api/rule/distributeRule")
public class DistributeRuleRestController extends BaseController{
	
	@Autowired
	private DistributeRuleService distributeRuleService;
	
	/**
	 * 查询派发规则 列表
	 * @param name 	  规则名称
	 * @param offset  分页起始
	 * @param limit   每页数量
	 * @param orderColumn 排序字段
	 * @param orderSort  排序规则
	 * @return 
	 */
	@RequestMapping("/list")
	public Object list(@RequestParam(value = "name", required = false) String name,
			@RequestParam(required = false, defaultValue = "0") Integer offset, 
			@RequestParam(required = false, defaultValue = "20") Integer limit,
			@RequestParam(defaultValue = "id") String orderColumn,
			@RequestParam(defaultValue = "DESC") String orderSort ) {
		
		Map<String, Object> params = Maps.newHashMap();
		MapUtils.putNotNull(params, "name", name);
		params.put("sort", new Sort(Sort.Direction.valueOf(orderSort), orderColumn));
		
		PageTable page = distributeRuleService.searchScrollPage(params, new Pagination(offset, limit));

		return StatusDto.buildSuccess(page);
	}
	
	/**
	 *  通过id 启用/禁用 派发规则
	 *  启用当前规则,同时禁用其他规则
	 * @param 规则id
	 * @return 处理结果
	 */
	@RequestMapping("/updateStatusById")
	public Object updateStatusById(Long id, String status){
		
		if(id == null || StringUtils.isBlank(status)){
			return StatusDto.buildFailure("参数id或Status不能为空!");
		}
		boolean result = distributeRuleService.updateStatusById(id,status);
		if(result){
			return StatusDto.buildSuccess("操作成功!");
		}
		return StatusDto.buildFailure("操作失败!");
	}
}
