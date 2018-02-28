package com.rocoinfo.rest.task;

import com.google.common.collect.Maps;
import com.rocoinfo.common.BaseController;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.dto.page.PageTable;
import com.rocoinfo.dto.page.Pagination;
import com.rocoinfo.entity.customer.CustomerHouse;
import com.rocoinfo.entity.task.TaskDistribute;
import com.rocoinfo.service.task.TaskDistributeService;
import com.rocoinfo.shiro.ShiroUser;
import com.rocoinfo.utils.MapUtils;
import com.rocoinfo.utils.WebUtils;
import com.rocoinfo.utils.excel.ExcelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 后台任务相关controller
 * @author Paul
 * 2017年6月16日
 */
@RestController
@RequestMapping(value = "/api/backTask")
public class TaskRestController extends BaseController {
	
	@Autowired
	private TaskDistributeService taskService;
	
	
	/**
	 *  新增任务,并创建客户信息/房屋信息/
	 * @param showHouseFlag 是否添加房屋信息
	 * @param newTaskType 新任务阶段
	 * @param customerHouse 房屋信息
	 * @param taskDistribute 任务信息
	 * @return
	 */
	@RequestMapping("/addTask")
	public Object addTask(Boolean showHouseFlag,String newTaskType, CustomerHouse customerHouse, TaskDistribute taskDistribute){
		ShiroUser loggedUser = WebUtils.getLoggedUser();
		if(loggedUser == null){
			return StatusDto.buildFailure("会话失效,请重新登录!");
		}
		return taskService.addBackTask(showHouseFlag,newTaskType, customerHouse, taskDistribute);
	}
	
	/**
	 * 带分页的条件查询  
	 * 后台任务列表:查询该客户下的所有任务
	 */
	@RequestMapping("/findTaskByCustomerNo")
	public Object findTaskByCustomerNo(
			@RequestParam(required = false, defaultValue = "0") String customerNo,
			@RequestParam(required = false, defaultValue = "0") Integer offset,
			@RequestParam(required = false, defaultValue = "20") Integer limit,
			@RequestParam(defaultValue = "id") String orderColumn,
			@RequestParam(defaultValue = "DESC") String orderSort) {

		Map<String, Object> params = Maps.newHashMap();
		MapUtils.putNotNull(params, "customerNo", customerNo);
		params.put("sort", new Sort(Sort.Direction.valueOf(orderSort), orderColumn));

		PageTable page = taskService.findTaskByCustomerNo(params, new Pagination(offset, limit));

		return StatusDto.buildSuccess(page);
	}
	
	/**
	 * 导出excel
	 * 
	 * @param resp
	 * @param startDate 开始时间
	 * @param keyword  关键字
	 * @param endDate 结束时间
	 */
	@RequestMapping(value = "/export")
	public void export(HttpServletResponse resp,String keyword, String storeCode,
			String dataSourceCode,String distributeStatus, Date startDate, Date endDate,
					   @RequestParam(value = "status", required = false) String status,
					   @RequestParam(value = "promoteSource", required = false) String promoteSource) {
		ShiroUser user = WebUtils.getLoggedUser();
		Map<String, Object> params = Maps.newHashMap();
		MapUtils.putNotNull(params, "keyword", keyword);
		MapUtils.putNotNull(params, "storeCode", storeCode);
		MapUtils.putNotNull(params, "distributeStatus", distributeStatus);
		MapUtils.putNotNull(params, "dataSourceCode", dataSourceCode);
		MapUtils.putNotNull(params, "startDate", startDate);
		MapUtils.putNotNull(params, "endDate", endDate);
		MapUtils.putNotNull(params, "status", status);
		MapUtils.putNotNull(params, "promoteSource", promoteSource);

		ServletOutputStream out = null;
		Workbook workbook = null;
		String fileName = "";
		try {
			if("Y".equals(distributeStatus)){
				fileName = "已派发任务.xlsx";
			}else{
				fileName = "未派发任务.xlsx";
			}
			resp.setContentType("application/x-msdownload");
			resp.addHeader("Content-Disposition",
					"attachment; filename=\"" + java.net.URLEncoder.encode(fileName, "UTF-8") + "\"");
			out = resp.getOutputStream();
			List<TaskDistribute> list = taskService.findExportTaskDistribute(params);
			if (CollectionUtils.isNotEmpty(list)) {
				/*list.forEach(taskDistribute -> {
					taskDistribute.setDistributeStatus("Y".equals(taskDistribute.getDistributeStatus()) ? "已派发" : "未派发");
				});*/
				
				workbook = ExcelUtil.getInstance().exportObj2ExcelWithTitleAndFields(list, TaskDistribute.class, true,
						titles, fields);
			}

			if (workbook != null) {
				workbook.write(out);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(out);
		}
	}
	
	
	// 初识化导出的字段
		private static List<String> titles = null;
		private static List<String> fields = null;

		static {
			titles = new ArrayList<String>(4);
			titles.add("客户姓名");
			titles.add("手机号");
			titles.add("客户编号");
			titles.add("进线时间");
			
			fields = new ArrayList<String>(4);
			fields.add("customerName");
			fields.add("customerMobile");
			fields.add("customerNo");
			fields.add("createTime");
		}

	/**
	 *  获取jobNum
	 */
	@RequestMapping("/getJobNum")
	public Object getJobNum(){
		ShiroUser loggedUser = WebUtils.getLoggedUser();
		return StatusDto.buildSuccess("查询成功",loggedUser.getUsername());
	}



}
