package com.rocoinfo.rest.employee;

import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.Maps;
import com.rocoinfo.common.BaseController;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.dto.page.PageTable;
import com.rocoinfo.dto.page.Pagination;
import com.rocoinfo.dto.page.Sort;
import com.rocoinfo.entity.employee.Employee;
import com.rocoinfo.entity.employee.EmployeeDataPermission;
import com.rocoinfo.entity.task.TaskDistribute;
import com.rocoinfo.service.core.EmployeeQueueCore;
import com.rocoinfo.service.core.TaskDistributeCore;
import com.rocoinfo.service.employee.EmployeeDataPermissionService;
import com.rocoinfo.service.employee.EmployeeService;
import com.rocoinfo.shiro.ShiroUser;
import com.rocoinfo.utils.MapUtils;
import com.rocoinfo.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <dl>
 * <dd>Description: 美得你crm 用户 controller</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/5</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@RestController
@RequestMapping(value = "/api/employee")
public class EmployeeController extends BaseController {

    @Autowired
    EmployeeService userService;
    @Autowired
    EmployeeDataPermissionService employeeDataPermissionService;
    @Autowired
    EmployeeQueueCore employeeQueueService;
    @Autowired
    TaskDistributeCore taskDistributeCoreService;


    /**
     * 获取本地用户列表
     *
     * @param keyword   关键字
     * @param storeCode 门店编号
     * @param autoOrder 是否自动接单
     * @param offset    分页参数
     * @param source    来自于管理员或者普通用户页面
     * @param pageSize
     * @param orderSort
     * @return
     */
    @RequestMapping("/list")
    public Object list(@RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "autoOrder", required = false) String autoOrder,
                       @RequestParam(value = "storeCode", required = false) List<String> storeCode,
                       @RequestParam(value = "source", required = false) String source,
                       @RequestParam(value="status", required = false) String  status,
                       @RequestParam(value = "offset", defaultValue = "0") int offset,
                       @RequestParam(value = "limit", defaultValue = "20") int pageSize,
                       @RequestParam(value = "sortOrder", defaultValue = "DESC") String orderSort) {
        if(storeCode.size()==0){
            storeCode=null;
        }
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "keyword", keyword);
        MapUtils.putNotNull(params, "autoOrder", autoOrder);
        MapUtils.putNotNull(params, "store", storeCode);
        MapUtils.putNotNull(params, "source", source);
        MapUtils.putNotNull(params, "status", status);
        //标记为列表查询页面
        MapUtils.putNotNull(params, "flg", "1");
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "job_num"));
        PageTable pageTable = userService.searchScrollPage(params, new Pagination(offset, pageSize, sort));
        return StatusDto.buildSuccess(pageTable);
    }

    /**
     * 员工列表 去除和筛选
     *
     * @param id          去除员工的id
     * @param jobNum     去除员工的job_no
     * @param store         筛选门店-传入则按传入的筛选 -未传入则按当前用户测筛选
     * @param dataSource 筛选来源-传入则按传入的筛选 -未传入则按当前用户测筛选
     * @param orderFlag 是否统计当日接单数 Y是
     * @param keyword     名字电话keyword模糊搜索
     * @param offset    分页参数
     * @param isWchat    是不是微信 微信的不分页
     * @param pageSize
     * @param orderSort
     * @return
     */
    @RequestMapping("/listCollection")
    public Object listCollection(@RequestParam(value = "id", required = false) String id,
                                 @RequestParam(value = "jobNum", required = false) String jobNum,
                                 @RequestParam(value = "store", required = false) List <String> store,
                                 @RequestParam(value = "dataSource", required = false) List <String> dataSource,
                                 @RequestParam(value = "keyword", required = false) String keyword,
                                 @RequestParam(value = "orderFlag", required = false) String orderFlag,
                                 @RequestParam(value = "offset", defaultValue = "0") int offset,
                                 @RequestParam(value = "limit", defaultValue = "20") int pageSize,
                                 @RequestParam(value = "sortOrder", defaultValue = "DESC") String orderSort,
                                 @RequestParam(value = "isWchat",required = false) Boolean isWchat) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "id", id);
        MapUtils.putNotNull(params, "jobNum", jobNum);
        MapUtils.putNotNull(params, "orderFlag", orderFlag);
        //设置查询的员工状态
        MapUtils.putNotNull(params, "status", "1");
        MapUtils.putNotNull(params, "keyword", keyword);
        //标记为调用查询
        MapUtils.putNotNull(params, "flg", "2");
        //查询当前员工的门店数据权限
        String username = WebUtils.getLoggedUser().getUsername();
        if(store==null || store.size()==0){
            List<EmployeeDataPermission> stores = employeeDataPermissionService.findEmpDataPermissionlist("MD", username);
            store=new ArrayList<String>();
            for(int i=0;i<stores.size();i++){
                store.add(stores.get(i).getCode());
            }
        }
        //查询当前员工的数据来源权限
        if(dataSource==null || dataSource.size()==0){
            List<EmployeeDataPermission> employeeDataPermissions = employeeDataPermissionService.empDataPermissionWithDataSource(username);
            dataSource=new ArrayList<String>();
            for(int i=0;i<employeeDataPermissions.size();i++){
                dataSource.add(employeeDataPermissions.get(i).getCode());
            }
        }
        if(store.size()==0){
            store=null;
        }
        if(dataSource.size()==0){
            dataSource=null;
        }
        if(isWchat!=null&&isWchat){
            pageSize=1000;
        }
        MapUtils.putNotNull(params, "dataSource", dataSource);
        MapUtils.putNotNull(params, "store", store);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.valueOf(orderSort), "job_num"));
        PageTable pageTable = userService.searchScrollPage(params, new Pagination(offset, pageSize, sort));
       //查询当日接单量
        if("Y".equals(orderFlag)){
           List<Employee> rows = pageTable.getRows();
           for (Employee employee:rows) {
               employee.setOrderNum( userService.countOrderNum(employee.getJobNum()));
           }
       }
        return StatusDto.buildSuccess(pageTable);
    }

    /**
     * 修改用户
     *
     * @param customerService
     * @return 操作情况
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody Employee customerService) {
        return userService.updateEmployee(customerService);
    }

    /**
     * 根据id 查询
     *
     * @param id
     * @return 查到的实体
     */
    @RequestMapping(value = "/getOneById", method = RequestMethod.GET)
    public Object getOneById(@RequestParam(value = "id", required = false) String id) {
        if (!StringUtils.isEmpty(id)) {
            long lid = Long.parseLong(id);
            Employee customerService = userService.getById(lid);
            return StatusDto.buildSuccess(customerService);
        }
        return StatusDto.buildFailure("id 不能为空");
    }

    /**
     * 添加用户
     *
     * @param customerService
     * @return 操作情况
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object insert(@RequestBody Employee customerService) {
        userService.insert(customerService);
        return StatusDto.buildSuccess("操作成功");
    }
    /**微信端接口  查询我的 当日
     * 查询当前用户基本信息和一些数量统计
     * @return
     */
    @RequestMapping(value = "/getDayInformation",method = RequestMethod.GET)
    public Object getDayInformation(){
        return   userService.getInforDayInfor(WebUtils.getLoggedUser().getId());
    }

    /**微信端接口  查询我的 当月
     * 查询当前用户基本信息和一些数量统计
     * @return
     */
    @RequestMapping(value = "/getMonthInformation",method = RequestMethod.GET)
    public Object getMonthInformation(){
        return   userService.getInforMomthInfor(WebUtils.getLoggedUser().getId());
    }

    /**微信端接口  查询我的 折线图
     * 查询当前用户基本信息和一些数量统计
     * @return
     */
    @RequestMapping(value = "/getLInformation",method = RequestMethod.GET)
    public Object getMyMessageAndStatistics(){
        return   userService.getInforLineChart(WebUtils.getLoggedUser().getId());
    }
    /**
     * 根据id 删除
     *
     * @param id
     * @return statusDto
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public Object deleteById(@PathVariable(value = "id") Long id) {
        if(id==null){
            return StatusDto.buildFailure("id 为空");
        }
        userService.deleteById(id);
        return StatusDto.buildSuccess("删除成功");
    }

    /**
     * 从单点登录中 同步用户信息
     * @author Paul
     * @date 2017-09-05
     */
    @RequestMapping(value = "/synUser")
    public Object synchroUser(){
        if( userService.synchroUser() ){
            return StatusDto.buildSuccess("同步用户账号成功！");
        }else{
            return StatusDto.buildFailure("同步用户账号失败！");
        }
    }


    /**
     * 查询员工队列的实时信息
     * @return
     */
    @RequestMapping(value = "/queueinfo")
    public Object queryEmpQueueRealTimeInfo(@RequestParam(value = "storeCode",required = false) String storeCode,
                                            @RequestParam(value="sourceCode",required = false) String sourceCode,
                                            @RequestParam(value = "keyWord",required = false) String keyWord){
        //校验用户
        StatusDto checkResult = checkFunctionAbleForRole("管理员");
        if(checkResult != null){
            return checkResult;
        }
        return employeeQueueService.findEmploeeQueueRealTimeInfo(storeCode,sourceCode,keyWord);
    }

    /**yu'zhi
     * 重刷新加载员工配置
     * @return
     */
    @RequestMapping(value = "/reloadConf")
    public Object reloadEmploeeConfig(){
        //校验用户
        StatusDto checkResult = checkFunctionAbleForRole("管理员");
        if(checkResult != null){
            return checkResult;
        }
        try {
            taskDistributeCoreService.reloadEmployeeConfigInRedis();
            return StatusDto.buildSuccess();
        }catch (Exception exp){
            exp.printStackTrace();
            return StatusDto.buildFailure();
        }
    }


    /**
     * 模拟每日quartz重新加载奖励单配置
     * @return
     */
    @RequestMapping("/sxjld")
    public Object refreshEmpRewardEvryday(){
        //校验用户
        StatusDto checkResult = checkFunctionAbleForRole("管理员");
        if(checkResult != null){
            return checkResult;
        }
        try {
            taskDistributeCoreService.initEmployeeParamToQueue(false,0);
            return StatusDto.buildSuccess();
        }catch (Exception exp){
            exp.printStackTrace();
            return StatusDto.buildFailure();
        }
    }
}
