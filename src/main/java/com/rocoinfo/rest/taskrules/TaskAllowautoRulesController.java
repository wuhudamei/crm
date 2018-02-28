package com.rocoinfo.rest.taskrules;

import com.google.common.collect.Maps;
import com.rocoinfo.common.BaseController;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.dto.page.PageTable;
import com.rocoinfo.dto.page.Pagination;
import com.rocoinfo.dto.page.Sort;
import com.rocoinfo.entity.taskrules.TaskAllowautoRules;
import com.rocoinfo.service.taskrules.TaskAllowautoRulesService;
import com.rocoinfo.shiro.ShiroUser;
import com.rocoinfo.utils.MapUtils;
import com.rocoinfo.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <dl>
 * <dd>Description: 美得你智装 </dd>
 * <dd>@date：2017/10/23  16:09</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@RestController
@RequestMapping("/api/taskRules")
public class TaskAllowautoRulesController extends BaseController {
    @Autowired
    private TaskAllowautoRulesService taskAllowautoRulesService;

    /**
     * @param
     * @return
     * @author Ryze
     * @date 2017/10/23 16:12
     * @description 任务派发规则列表
     */
    @RequestMapping("/list")
    public Object list(@RequestParam(value = "promoteCode", required = false) String promoteCode,
                       @RequestParam(value = "storeCode", required = false) String storeCode,
                       @RequestParam(value = "dataSourceCode", required = false) String dataSourceCode,
                       @RequestParam(value = "offset", defaultValue = "0") int offset,
                       @RequestParam(value = "limit", defaultValue = "20") int pageSize,
                       @RequestParam(value = "sortOrder", defaultValue = "DESC") String orderSort) {

        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "promoteCode", promoteCode);
        MapUtils.putNotNull(params, "storeCode", storeCode);
        MapUtils.putNotNull(params, "dataSourceCode", dataSourceCode);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "create_time"));
        PageTable pageTable = taskAllowautoRulesService.searchScrollPage(params, new Pagination(offset, pageSize, sort));
        return StatusDto.buildSuccess(pageTable);
    }

    /**
     * @param
     * @return
     * @author Ryze
     * @date 2017/10/23 16:12
     * @description 任务派发规则添加
     */
    @RequestMapping("/add")
    public Object add(@RequestBody TaskAllowautoRules taskAllowautoRules) {
        List<TaskAllowautoRules> insertBatch = taskAllowautoRules.getInsertBatch();
        ShiroUser loggedUser = WebUtils.getLoggedUser();
        if(insertBatch!=null){
            taskAllowautoRulesService.insertBatch(insertBatch,loggedUser);
        }
        return StatusDto.buildSuccess("添加成功");
    }
    @RequestMapping("/delete/{id}")
    public Object deleteById(@PathVariable("id")Long id) {
        taskAllowautoRulesService.deleteById(id);
        return StatusDto.buildSuccess("操作成功");
    }

}
