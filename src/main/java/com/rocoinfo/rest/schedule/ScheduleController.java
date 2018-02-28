package com.rocoinfo.rest.schedule;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import com.rocoinfo.entity.customer.ApplyPosition;
import com.rocoinfo.enumeration.SwapOneAndZero;
import com.rocoinfo.service.customer.ApplyPositionService;
import com.rocoinfo.service.customer.InvalidateUserApplyService;
import com.rocoinfo.service.customer.ReturnOrderService;
import com.rocoinfo.service.employee.EmployeeService;
import com.rocoinfo.shiro.ShiroUser;
import com.rocoinfo.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.google.common.collect.Maps;
import com.rocoinfo.common.BaseController;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.dto.page.PageTable;
import com.rocoinfo.dto.page.Pagination;
import com.rocoinfo.dto.page.Sort;
import com.rocoinfo.entity.schedule.Schedule;
import com.rocoinfo.service.schedule.ScheduleService;
import com.rocoinfo.utils.MapUtils;

/**
 * <dl>
 * <dd>Description: 美得你crm 日程 controller</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/7</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@SuppressWarnings("all")
@RestController
@RequestMapping(value = "/api/schedule")
public class ScheduleController extends BaseController{
    @Autowired
    private ScheduleService scheduleService;

    /**
     * 无效客户申请服务
     */
    @Autowired
    private InvalidateUserApplyService invalidateUserApplyService;

    /**
     * 退单申请服务
     */
    @Autowired
    private ReturnOrderService returnOrderService;

    /**
     * 员工服务
     */
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ApplyPositionService applyPositionService;

    @RequestMapping("/findByUser")
    public Object findByUser(@RequestParam(value = "jobNum", required = false) String jobNum,
                             @RequestParam(value = "offset", defaultValue = "0") int offset,
                             @RequestParam(value = "limit", defaultValue = "20") int pageSize,
                             @RequestParam(value = "sortOrder", defaultValue = "DESC") String orderSort) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "jobNum", jobNum);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.valueOf(orderSort), "id"));
        PageTable pageTable = scheduleService.searchScrollPage(params, new Pagination(offset, pageSize, sort));
        return StatusDto.buildSuccess(pageTable);

    }

    /**
     * 根据日期查询当天的日程 给日历日程使用
     * @param date 日期参数
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/search")
    public Object search(@RequestParam("date") String date) {
        if(date == null || "".equals(StringUtils.trim(date))) {
            return StatusDto.buildFailure("日期不能为空");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        ShiroUser user = WebUtils.getLoggedUser();
        map.put("jobNum", user.getUsername());
        map.put("startTime", date + " 00:00:00");
        map.put("endTime", date + " 23:59:59");

        // 数据组装
        List<Schedule> list =   scheduleService.search(map);

        return StatusDto.buildSuccess(assembleData(list, date));
    }

    /**
     * 根据日期查询当天的日程 给日程列表使用
     * @param date 日期参数
     * @return
     */
    @RequestMapping(value = "/searchschedulelist")
    public Object searchScheduleByDate(@RequestParam("date") String date) {
        if(date == null || "".equals(StringUtils.trim(date))) {
            return StatusDto.buildFailure("日期不能为空");
        }
        Map<String, Object> map = new HashMap<String, Object>();

        ShiroUser user = WebUtils.getLoggedUser();
        map.put("jobNum", user.getUsername());
        map.put("startTime", date + " 00:00:00");
        map.put("endTime", date + " 23:59:59");

        return StatusDto.buildSuccess(scheduleService.search(map));
    }

    /**
     * 把数据组装成前端需要的结构
     * @param list 数据库数据列表
     * @param date 查询日期
     * @return 封装结果
     */
    private List<Map<String, Object>> assembleData(List<Schedule> list, String date) {
        String[] dates = date.split("-");
        int year = Integer.valueOf(dates[0]); // 年
        int month = Integer.valueOf(dates[1]); // 月
        int day = Integer.valueOf(dates[2]); // 日

        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 24; i++) {
            LocalDateTime startTime = LocalDateTime.of(year,month, day, i, 0, 0);
            LocalDateTime endTime = LocalDateTime.of(year,month, day, i, 59, 59);
            resultList.add(timeSection(list, startTime, endTime, i));
        }

        return resultList;
    }

    /**
     * 根据当前的时间段从查询回来的列表中查找出
     * @param list 数据库数据列表
     * @param start 时间段开始时间
     * @param end 时间段结束时间
     * @return 该时间段对应的数据
     */
    private Map<String, Object> timeSection(List<Schedule> list, LocalDateTime start, LocalDateTime end, int hour) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Schedule> sectionList = new ArrayList<Schedule>();
        for (Schedule schedule : list) {
            Instant instant = schedule.getScheduleTime().toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);

            if(localDateTime.compareTo(start) >= 0 && localDateTime.compareTo(end) < 0) {
                sectionList.add(schedule);
            }
        }

        map.put("hour", hour);
        map.put("events", sectionList);
        return map;
    }

    /**
     * 日程里面显示的审批列表
     * @return
     */
    @RequestMapping(value = "/applylist")
    public Object applyList() {
        Map map = new HashMap();
        // 针对无效客户申请时，审批人是当前登录人员  mechandiser
        ShiroUser user = WebUtils.getLoggedUser();
        map.put("mechandiser", user.getUsername());

        // 针对退单申请时，审批人是看当前登录人的岗位是否是总监  position @TODO
//        List<ApplyPosition> applyPositionList = applyPositionService.all();
//        System.out.println(applyPositionList);

        // 如果是在这个配置的列表里面的话，则有权限审批退单申请
//        for (ApplyPosition applyPosition : applyPositionList) {
//            if (user.getPosition() != null && user.getPosition().equals(applyPosition.getPosition())) {
//                map.put("position", applyPosition.getPosition());
//                break;
//            }
//        }

        return StatusDto.buildSuccess(invalidateUserApplyService.searchApplys(map));
    }

    /**
     * 根据申请编号查询无效客户申请详情
     * @param applyNo 申请编号
     * @return
     */
    @RequestMapping(value = "/invalidatedetail/{applyno}")
    public Object applyInvalidateUserDetail(@PathVariable(value = "applyno") String applyNo) {
        return StatusDto.buildSuccess(invalidateUserApplyService.findDetailByApplyNo(applyNo));
    }

    /**
     * 根据ID获取日程详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}")
    public Object getScheduleDetailById(@PathVariable("id") long id) {
        return StatusDto.buildSuccess(scheduleService.getById(id));
    }

    /**
     * 添加日程
     * @param schedule
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object insertSchedule(@RequestBody Schedule schedule) {
        if (schedule.getTitle() == null || schedule.getTitle() == "") {
            return StatusDto.buildFailure("标题不能为空");
        }
        if (schedule.getContent() == null || schedule.getContent() == "") {
            return StatusDto.buildFailure("内容不能为空");
        }
        if (schedule.getScheduleTime() == null) {
            return StatusDto.buildFailure("日程时间不能为空");
        }

        // 添加当前登录员工
        ShiroUser user = WebUtils.getLoggedUser();
        schedule.setJobNum(user.getUsername());
        schedule.setGenerateModel(SwapOneAndZero.ZERO.getLabel());
        schedule.setCreateTime(new Date());
        scheduleService.insert(schedule);
        return StatusDto.buildSuccess("添加成功");
    }

    /**
     * 无效客户申请审批通过
     * @param id 申请ID
     * @param taskNo 任务编号
     *
     * @return
     *
     * @auther tony
     */
    @RequestMapping(value = "/invalidateagree", method = RequestMethod.GET)
    public Object agreeInvalidate(@RequestParam(value = "id") int id,
                                  @RequestParam(value = "taskNo") String taskNo) {
        return scheduleService.handleInvalidateAgree(id, taskNo);
    }

    /**
     * 拒绝退单申请
     * @param id
     * @param taskNo
     * @return
     */
    @RequestMapping(value = "/refusereturnorder", method = RequestMethod.GET)
    public Object refuseReturnOrder(@RequestParam(value = "id", required = true) int id,
                                    @RequestParam(value = "taskNo", required = true) String taskNo,
                                    @RequestParam(value = "orderId", required = true) String  orderId) {
        scheduleService.handleReturnOrderRefuse(id, taskNo, orderId);
        return StatusDto.buildSuccess("审批成功");
    }

    /**
     * 同意退单申请
     * @param id 申请ID
     * @param taskNo 任务编号
     * @param orderId 订单Id
     * @return
     */
    @RequestMapping(value = "/agreeereturnorder", method = RequestMethod.GET)
    public Object agreeReturnOrder(@RequestParam(value = "id", required = true) int id,
                                   @RequestParam(value = "taskNo", required = true) String taskNo,
                                   @RequestParam(value = "orderId", required = true) String orderId) throws Exception {
        return scheduleService.handleReturnOrderAgree(id, taskNo, orderId);
    }
}
