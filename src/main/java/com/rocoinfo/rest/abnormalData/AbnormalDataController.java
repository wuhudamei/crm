package com.rocoinfo.rest.abnormalData;

import com.google.common.collect.Maps;
import com.rocoinfo.common.BaseController;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.dto.page.PageTable;
import com.rocoinfo.dto.page.Pagination;
import com.rocoinfo.dto.page.Sort;
import com.rocoinfo.service.abnormalData.AbnormalDataService;
import com.rocoinfo.service.basicConfig.DictionaryService;
import com.rocoinfo.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <dl>
 * <dd>Description: 美得你crm 异常数据controller</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/7</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@RestController
@RequestMapping("api/abnormal")
public class AbnormalDataController extends BaseController {
    @Autowired
    AbnormalDataService abnormalDataService;
    @Autowired
    DictionaryService dictionaryService;
    /**
     * 查询列表
     *
     * @param offset    分页参数
     * @param pageSize
     * @param orderSort
     * @return
     */
    @RequestMapping("/list")
    public Object list(@RequestParam(value = "abnormalContent", required = false) String abnormalContent,
                       @RequestParam(value = "abnormalType", required = false) String abnormalType,
                       @RequestParam(value = "sourceCode", required = false) String sourceCode,
                       @RequestParam(value = "status", required = false) String status,
                       @RequestParam(value = "offset", defaultValue = "0") int offset,
                       @RequestParam(value = "limit", defaultValue = "20") int pageSize,
                       @RequestParam(value = "sortOrder", defaultValue = "DESC") String orderSort) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "abnormalContent", abnormalContent);
        MapUtils.putNotNull(params, "abnormalType", abnormalType);
        MapUtils.putNotNull(params, "sourceCode", sourceCode);
        MapUtils.putNotNull(params, "status", status);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.valueOf(orderSort), "id"));
        PageTable pageTable = abnormalDataService.searchScrollPage(params, new Pagination(offset, pageSize, sort));
        return StatusDto.buildSuccess(pageTable);
    }
    /**
     *  根据在数据字典里的配置 查询类型
     * @param dicCode  异常类编号
     * @return
     */
    @RequestMapping(value = "/type",method = RequestMethod.GET)
    public Object getType(@RequestParam  String dicCode) {
        return StatusDto.buildSuccess(dictionaryService.findSubDictListByCode(dicCode));
    }

}
