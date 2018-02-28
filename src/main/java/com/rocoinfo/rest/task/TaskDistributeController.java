package com.rocoinfo.rest.task;

import com.google.common.collect.Maps;
import com.rocoinfo.common.BaseController;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.dto.page.PageTable;
import com.rocoinfo.dto.page.Pagination;
import com.rocoinfo.dto.page.Sort;
import com.rocoinfo.enumeration.TaskDistributeStatus;
import com.rocoinfo.service.core.TaskQueueCore;
import com.rocoinfo.service.task.TaskDistributeService;
import com.rocoinfo.shiro.ShiroUser;
import com.rocoinfo.utils.MapUtils;
import com.rocoinfo.utils.WebUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <dl>
 * <dd>Description: 任务派发相关Controller</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-6-6 16:20:07</dd>
 * <dd>@author：Andy</dd>
 * </dl>
 */

@RestController
@RequestMapping(value = "/api/task")
public class TaskDistributeController extends BaseController {
	
	@Autowired
	private TaskDistributeService taskService;
	@Autowired
	private TaskQueueCore taskQueueCoreService;

	/**
	 * Ryze
	 * 获取任务派发列表
	 *
	 * @param keyword   关键字
	 * @param distributeStatus   派发状态
	 * @param storeCode     门店编号
	 * @param dataSourceCode     来源编号
	 * @param offset    分页参数
	 * @param pageSize
	 * @param orderSort
	 * @return
	 */
	@RequestMapping("/list")
	public Object list(@RequestParam(value = "keyword", required = false) String keyword,
					   @RequestParam(value = "mechandiser", required = false) String mechandiser,
					   @RequestParam(value = "distributeStatus", required = false) String distributeStatus,
					   @RequestParam(value = "storeCode", required = false) String storeCode,
					   @RequestParam(value = "dataSourceCode", required = false) String dataSourceCode,
					   @RequestParam(value = "startDate", required = false) Date startDate,
					   @RequestParam(value = "endDate", required = false) Date endDate,
					   @RequestParam(value = "status", required = false) String status,
					   @RequestParam(value = "promoteSource", required = false) String promoteSource,
					   @RequestParam(value = "offset", defaultValue = "0") int offset,
					   @RequestParam(value = "limit", defaultValue = "20") int pageSize,
					   @RequestParam(value = "sortOrder", defaultValue = "DESC") String orderSort,
					   @RequestParam(value = "taskStatus",required = false) String taskStatus) {
		
		Map<String, Object> params = Maps.newHashMap();
		MapUtils.putNotNull(params, "keyword", keyword);
		MapUtils.putNotNull(params, "mechandiser", mechandiser);
		MapUtils.putNotNull(params, "distributeStatus", distributeStatus);
		MapUtils.putNotNull(params, "storeCode", storeCode);
		MapUtils.putNotNull(params, "dataSourceCode", dataSourceCode);
		MapUtils.putNotNull(params, "startDate", startDate);
		MapUtils.putNotNull(params, "endDate", endDate);
		MapUtils.putNotNull(params, "taskStatus", taskStatus);
        MapUtils.putNotNull(params, "status", status);
        MapUtils.putNotNull(params, "promoteSource", promoteSource);
		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "create_time"));
		PageTable pageTable = taskService.searchScrollPage(params, new Pagination(offset, pageSize, sort));

		return StatusDto.buildSuccess(pageTable);
	}

	/**
	 * @author Ryze
	 * 任务转派 或者 派发
	 * @param taskNo 任务编号
	 * @param jobNum //跟单员
	 * @param oldJobNum //原跟单员
	 * @param isWchat //是不是微信端
	 * @param applyId //微信端的参数 applyId申请id
	 */
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public Object update(@RequestParam(value = "taskNo", required = false) String taskNo,
						 @RequestParam(value = "oldJobNum", required = false) String oldJobNum,
						 @RequestParam(value = "isWchat", required = false) Boolean isWchat,
						 @RequestParam(value = "jobNum", required = false) String jobNum,
						 @RequestParam(value = "applyId", required = false) Integer applyId){
		ShiroUser loggedUser = WebUtils.getLoggedUser();
		return taskService.updateTask(null,taskNo,oldJobNum,jobNum,loggedUser.getId(),isWchat,applyId);
	}

	/**
	 * @author Ryze  进店
	 * @param taskNo 任务编号
	 * @param customerNo 客户编号
	 * @param mechandiser 跟单员
	 * @return
	 */
	@RequestMapping(value = "/goStore",method = RequestMethod.POST)
	public Object update(@RequestParam(value = "taskNo", required = false) String taskNo,
						 @RequestParam(value = "customerNo", required = false) String customerNo,
						 @RequestParam(value = "mechandiser", required = false) String mechandiser){
		ShiroUser loggedUser = WebUtils.getLoggedUser();
		  taskService.goStore(taskNo,customerNo,mechandiser,Integer.valueOf(loggedUser.getId().toString()));
		return StatusDto.buildSuccess("进店成功");
	}


	/**
	 * 查询任务队列的实时信息
	 * @return
	 */
	@RequestMapping(value = "/queueinfo")
	public Object taskToAutoDistributeRealtimeQueueInfo(@RequestParam(value = "storeCode",required = false) String storeCode,
														@RequestParam(value = "sourceCode",required = false) String sourceCode,
														@RequestParam(value = "keyWord" ,required = false) String keyWord){
		StatusDto checkResult = checkFunctionAbleForRole("管理员");
		if(checkResult != null){
			return checkResult;
		}
		return taskQueueCoreService.queryTaskQueueRealTimeInfo(storeCode,sourceCode,keyWord);
	}


    /**
     * 将任务状态修改为有效
     * @param taskNo
     * @return
     */
    @RequestMapping(value = "/chstatus",method = RequestMethod.POST)
	public Object changeTaskStatus(@RequestParam(value = "taskNo") String taskNo){
        if(!WebUtils.getLoggedUser().getPermissions().contains("task:tovalid")){
            return StatusDto.buildFailure("权限不足");
        }
	    return taskService.updateTaskStatus(taskNo, TaskDistributeStatus.VALID.getIndex()+"");
    }
}
