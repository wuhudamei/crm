package com.rocoinfo.rest.dataSource;

import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.Maps;
import com.rocoinfo.common.BaseController;
import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.dto.page.PageTable;
import com.rocoinfo.dto.page.Pagination;
import com.rocoinfo.dto.page.Sort;
import com.rocoinfo.entity.dataSource.DataSource;
import com.rocoinfo.service.dataScource.DataSourceService;
import com.rocoinfo.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

/**
 * <dl>
 * <dd>Description: 美得你crm 数据来源管理controller</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/7</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@RestController
@RequestMapping("api/data")
public class DataSourceController extends BaseController {
    @Autowired
    DataSourceService dataSourceService;

    /**
     * 查询列表
     *
     * @param offset    分页参数
     * @param pageSize
     * @param orderSort
     * @return
     */
    @RequestMapping("/list")
    public Object list(@RequestParam(value = "keyword", required = false) String keyword,
                        @RequestParam(value = "offset", defaultValue = "0") int offset,
                       @RequestParam(value = "limit", defaultValue = "20") int pageSize,
                       @RequestParam(value = "sortOrder", defaultValue = "DESC") String orderSort) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "keyword", keyword);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.valueOf(orderSort), "id"));
        PageTable pageTable = dataSourceService.searchScrollPage(params, new Pagination(offset, pageSize, sort));
        return StatusDto.buildSuccess(pageTable);
    }
    /**
     * 修改数据源
     * @param dataSource
     * @return 操作情况
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateStatus(@RequestBody DataSource dataSource ) {
        dataSource.setUpdateTime(new Date());
        dataSourceService.update(dataSource);
        dataSourceService.syncCallCertificateMap();
        return StatusDto.buildSuccess("操作成功");
    }
    /**
     *  根据id 查询
     * @param id
     * @return 查到的实体
     */
    @RequestMapping(value = "/getOneById",method = RequestMethod.GET)
    public Object getOneById(@RequestParam(value = "id", required = false) String id) {
        if(!StringUtils.isEmpty(id)) {
            long lid = Long.parseLong(id);
            DataSource dataSourceServiceById = dataSourceService.getById(lid);
            return StatusDto.buildSuccess(dataSourceServiceById);
        }
        return StatusDto.buildFailure("id 不能为空");
    }
    /**
     * 添加数据来源
     * @param dataSource
     * @return 操作情况
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object insert(@RequestBody  DataSource dataSource ) {
        dataSource.setCreateTime(new Date());
        dataSourceService.insert(dataSource);
        dataSourceService.syncCallCertificateMap();
        return StatusDto.buildSuccess("操作成功");
    }
    /**
     *  查询接口列表 下拉框用
     * @return 查到的列表
     */
    @RequestMapping(value = "/getNameLabel")
    public Object getNameLabel() {
        return StatusDto.buildSuccess(dataSourceService.findAll());
    }
    

}
