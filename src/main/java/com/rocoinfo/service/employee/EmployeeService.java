package com.rocoinfo.service.employee;

import com.google.common.collect.Maps;
import com.rocoinfo.Constants;
import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.entity.employee.Employee;
import com.rocoinfo.entity.employee.EmployeeOrderReward;
import com.rocoinfo.entity.employee.EmployeeOrderSource;
import com.rocoinfo.enumeration.CustomerStatus;
import com.rocoinfo.repository.employee.EmployeeDao;
import com.rocoinfo.service.core.EmployeeQueueCore;
import com.rocoinfo.service.core.EmployeeRewardCore;
import com.rocoinfo.service.orderapi.NewApiDataService;
import com.rocoinfo.service.task.TaskDistributeService;
import com.rocoinfo.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <dl>
 * <dd>Description: 美得你crm 用户Service</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/5</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@SuppressWarnings("all")
@Service
public class EmployeeService extends CrudService<EmployeeDao, Employee> {

    @Autowired
    EmployeeQueueCore employeeQueueCore;
    @Autowired
    EmployeeRewardCore employeeRewardCore;
    @Autowired
    EmployeeOrderSourceService employeeOrderSourceService;
    @Autowired
    EmployeeDataPermissionService employeeDataPermissionService;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private NewApiDataService newApiService;
    @Autowired
    private TaskDistributeService taskDistributeService;

    @Autowired
    private EmployeeOrderRewardService employeeOrderRewardService;

    /**
     * 根据编号查询用户
     *
     * @param jobNum 编号
     * @return
     */
    public Employee getOneByJobNum(String jobNum) {
        return entityDao.getOneByJobNum(jobNum);
    }
    /**
     * 根据来源编号查询客户信息列表
     * @param SourceCode
     * @return
     */
    public List<Employee> findOneBySourceCode(String SourceCode){
        return entityDao.findOneBySourceCode(SourceCode);
    }

    /**
     * 修改客服 并调用接口 队列修改
     * @param customerService （客服）用户实体
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public StatusDto updateEmployee(Employee customerService){
            //取到以前的门店
            String oldEmpStoreCode = entityDao.getById(customerService.getId()).getStoreCode();
            //持久化对员工的修改
            entityDao.update(customerService);


            //影响redis中的该员工的接单配置
            String jobNum = customerService.getJobNum();
            String status = customerService.getStatus();
            String storeCode = customerService.getStoreCode();

            //如果更改了门店，则把原来门店的相关配置清除
            if(StringUtils.isNotEmpty(oldEmpStoreCode) && !oldEmpStoreCode.equals(storeCode)){
                //redis中移除该员工的接单来源
                List<EmployeeOrderSource> dataOrderSourceByEmpJobNum = employeeOrderSourceService.findDataOrderSourceByJobNum(jobNum);
                for (EmployeeOrderSource dataOrderSource:dataOrderSourceByEmpJobNum) {
                    employeeQueueCore.removeEmployeeByQueue(oldEmpStoreCode,dataOrderSource.getOrderSource(),jobNum);
                }
                //redis中移除该员工的接单规则
                employeeQueueCore.removeEmployeeGetOrderRule(oldEmpStoreCode,jobNum);

                //删除数据权限 和 接单来源
                employeeDataPermissionService.deleteByJobNum(jobNum);
                employeeOrderSourceService.deleteByJobNum(jobNum);
            }

            //——未变更员工禁用启用状态，只需修改接单规则
            if(StringUtils.isEmpty(status)){
                //更新redis中的员工规则
                employeeQueueCore.removeEmployeeGetOrderRule(storeCode,jobNum);
                employeeQueueCore.addEmployeeGetOrderRule(storeCode,jobNum,customerService.getAutoOrder(),customerService.getBusyThreshold());
            }
            //——更改了员工禁用启用状态，移除或添加redus中整套员工接单的配置
            else {
                //——禁用员工
                if ("0".equals(status)){
                    //redis中移除该员工的接单来源
                    List<EmployeeOrderSource> dataOrderSourceByEmpJobNum = employeeOrderSourceService.findDataOrderSourceByJobNum(jobNum);
                    for (EmployeeOrderSource dataOrderSource:dataOrderSourceByEmpJobNum) {
                        employeeQueueCore.removeEmployeeByQueue(storeCode,dataOrderSource.getOrderSource(),jobNum);
                    }
                    //redis中移除该员工的接单规则
                    employeeQueueCore.removeEmployeeGetOrderRule(storeCode,jobNum);
                    //redis中移除该员工的奖励单
                    employeeRewardCore.removeEmployeeRewardAndUpdateDataBase(jobNum);

                    //删除数据权限 和 接单来源
                    employeeDataPermissionService.deleteByJobNum(jobNum);
                    employeeOrderSourceService.deleteByJobNum(jobNum);
                }
                //——启用员工
                else if ("1".equals(status)){
                    //向redis中添加该员工的接单来源
                    List<EmployeeOrderSource> dataOrderSourceByEmpJobNum = employeeOrderSourceService.findDataOrderSourceByJobNum(jobNum);
                    for (EmployeeOrderSource dataOrderSource:dataOrderSourceByEmpJobNum) {
                        employeeQueueCore.addEmployeeToQueue(storeCode,dataOrderSource.getOrderSource(),jobNum);
                    }
                    //向redis中添加该员工的接单规则
                    Employee aimEmp = entityDao.getOneByJobNum(jobNum);
                    employeeQueueCore.addEmployeeGetOrderRule(storeCode, jobNum, aimEmp.getAutoOrder(), aimEmp.getBusyThreshold());
                    //——加载今日的奖励单配置，若已存在，会覆盖
                    List<EmployeeOrderReward> employeeRewardList = employeeOrderRewardService.findByEmpAndDate(jobNum,new Date());
                    if(!employeeRewardList.isEmpty()) {
                        EmployeeOrderReward empReward = employeeRewardList.get(0);
                        employeeRewardCore.setEmployeeRewardOrderNum(empReward.getJobNo(), empReward.getRewardOrderNum());
                        employeeRewardCore.setGiveEmployeeRewardOrderNum(empReward.getJobNo(),empReward.getActualNum());
                    }
                }
            }
            return StatusDto.buildSuccess("操作成功");
    }

    /**微信端 我的信息
     * 统计当日的数据
     * @param employeeId 客服id
     * @return
     */
    public StatusDto getInforDayInfor(Long employeeId ){
        Date date = getNowDateZero();
        if(employeeId==null){
            StatusDto.buildFailure("用户id 为空");
        }
        //个人信息 组装
        Employee employee = entityDao.getById(employeeId);
        String jobNum = employee.getJobNum();
        Long id = employee.getId();
        Map<String, Object> inforMap = MapUtils.of("employee", employee);
        String format = DateUtils.format(new Date(), DateUtils.DATE_SMALL_STR);
        Map<String, Object> param = MapUtils.of("date1",format);
        param.put("date2",format);
        param.put("userId",id);
        param.put("jobNum",jobNum);
        //当天的待邀约已邀
        param.put("type","1");
        Map<String, Object> dayData = MapUtils.of("toBeInvited", taskDistributeService.countPersonCommunicate(param));
        // 约数量统计
        param.put("type","2");
        dayData.put("hasBeenInvited",taskDistributeService.countPersonCommunication(param));
        //当天接待客户数
        param.put("type","3");
        dayData.put("dayReceptionCustomers",taskDistributeService.countPersonCommunication(param));
        //大定
        param.put("type","1");
        dayData.put("bigSet", taskDistributeService.countPersonOrderInfo(param));
        //小定
        param.put("type","2");
        dayData.put("smallSet", taskDistributeService.countPersonOrderInfo(param));
        //退单
        param.put("type","3");
        dayData.put("newClosedOrdNum",taskDistributeService.countPersonOrderInfo(param));
        inforMap.put("dayData",dayData);
        return  StatusDto.buildSuccess(inforMap);
    }
    /**微信端 我的信息
     * 统计当月的数据
     * @param employeeId 客服id
     * @return
     */
    public StatusDto getInforMomthInfor(Long employeeId ){
        Date date = getNowDateZero();
        if(employeeId==null){
            StatusDto.buildFailure("用户id 为空");
        }
        //个人信息 组装
        Employee employee = entityDao.getById(employeeId);
        String jobNum = employee.getJobNum();
        Long id = employee.getId();
        Map<String, Object> inforMap = MapUtils.of("employee", employee);
        Map<String, Object> param=MapUtils.of("date1",DateUtils.format(getFirstDay(),DateUtils.DATE_SMALL_STR));
        param.put("date2",DateUtils.format(getLastDay(),DateUtils.DATE_SMALL_STR));
        param.put("userId",id);
        param.put("jobNum",jobNum);
        param.put("type","3");
        //当月接待客户数
        Map<String, Object> dayData = MapUtils.of("monthReceptionCustomers", taskDistributeService.countPersonCommunication(param));
        //大定
        param.put("type","1");
        dayData.put("bigSet", taskDistributeService.countPersonOrderInfo(param));
        //小定
        param.put("type","2");
        dayData.put("smallSet", taskDistributeService.countPersonOrderInfo(param));
        //退单
        param.put("type","3");
        dayData.put("newClosedOrdNum",taskDistributeService.countPersonOrderInfo(param));
        inforMap.put("monthData",dayData);
        return  StatusDto.buildSuccess(inforMap);
    }

    /**
     * 微信端 我的信息
     * 统计折线图
     * @param employeeId
     * @return
     */
    public StatusDto getInforLineChart(Long employeeId){
        Date date = getNowDateZero();
        if(employeeId==null){
            StatusDto.buildFailure("用户id 为空");
        }
        //个人信息
        Employee employee = entityDao.getById(employeeId);
        String jobNum = employee.getJobNum();
        String empIdinord = employee.getEmpIdinord();
        //组装页面的一些数据
        //tab 页
        List<String> tabsData = new ArrayList<>();
        tabsData.add("客户数");
        tabsData.add("小定数");
        tabsData.add("大定数");
        tabsData.add("收款数");
        Map<String, Object> inforMap = MapUtils.of("tabs", tabsData);

        //Y坐标
        List<Map<String, Object>> maps = new ArrayList<>();
        //客户数
        Map<String, Object> customerCountMap = MapUtils.of("name", "客户数");
        customerCountMap.put("type","line");
        //客户数
        List customerCount = new ArrayList();
        //日期集合 X坐标
        List <String> dateList = new ArrayList <String>();
        for (int i=0;i<30;i++){
            Date beforeDate = getBeforeDate(date, -30+i);
            customerCount.add( entityDao.countCustomer(jobNum,beforeDate));
            dateList.add(DateUtils.format(beforeDate,"yyyy-MM-dd"));
        }
        customerCountMap.put("data",customerCount);
        maps.add(customerCountMap);

        //调接口  EMPLOYEE_EVERY_MESSAGE
        Map<String, String> parameterchart = MapUtils.of("empId",empIdinord);
        parameterchart.put("startTime",DateUtils.format(getBeforeDate(date, -30),"yyyy-MM-dd HH:mm:ss"));
        parameterchart.put("endTime",DateUtils.format(date,"yyyy-MM-dd HH:mm:ss"));

        List<String> small=null;
        List<String> big = null;
        List<String> money=null;

        try {
            //FIXME 新版接口——某段时间内某员工订单信息 getEmpPerioudInfo
            Map<String, Object> statusDto = null;
            if(CrmApiUtil.shouldUseOldApi()) {
                String post = CrmApiUtil.post(PropertyHolder.getCrmApiUrl()
                        + Constants.EMPLOYEE_EVERY_MESSAGE, parameterchart);
                statusDto = JsonUtils.fromJsonAsMap(post, String.class, Object.class);
            }
            else{
                statusDto = newApiService.getEmpPerioudInfo();
            }
            if(statusDto!=null){
                if (statusDto.get("code") != null && "1".equals(statusDto.get("code").toString())) {
                    if (statusDto.get("data") != null) {
                        List<Map<String,String>> daydata = ( List<Map<String,String>>) statusDto.get("data");
                        small = new ArrayList<String>();
                        big = new ArrayList<String>();
                        money = new ArrayList<String>();
                        for (Map<String, String> mapp:daydata){
                            small.add(mapp.get("newSmallOrdNum"));
                            big.add(mapp.get("newBigOrdNum"));
                            money.add(mapp.get("recMoney"));
                        }
                    }
                }
            }
        } catch (Exception e) {
            return StatusDto.buildFailure("接口调用错误");
        }


        //小定
        Map<String, Object> smallSet = MapUtils.of("name","小定数");
        smallSet.put("type","line");

        smallSet.put("data",small);
        maps.add(smallSet);

        //大定
        Map<String, Object> bigSet = MapUtils.of("name","大定数");
        bigSet.put("type","line");
        bigSet.put("data",big);
        maps.add(bigSet);

        //收款额
        Map<String, Object> receiptAmount = MapUtils.of("name","收款数");
        receiptAmount.put("type","line");
        receiptAmount.put("data",money);
        maps.add(receiptAmount);
        inforMap.put("X",dateList);
        inforMap.put("Y",maps);
        return  StatusDto.buildSuccess(inforMap);

    }




    /**
     * 获取指定日期 i天的日期 -->正数为i 天后 负数 为i天前
     * @param date 指定日期
     * @param i 间隔
     * @return
     */
    private Date getBeforeDate(Date date,int i){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,i);
        return calendar.getTime();
    }

    /**
     * 获取当天0点的时间
     * @return
     */
    private  Date getNowDateZero(){
        Date date = new Date();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        return  date;
    }

    /**
     * 获取当月第一天
     * @return
     */
    private  Date getFirstDay() {
        Calendar calendar = Calendar.getInstance();
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(getNowDateZero());
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
       return gcLast.getTime();
    }

    /**
     * 获取当月最后一天
     * @return
     */
    public  Date getLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( getFirstDay());
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date time = calendar.getTime();
        time.setHours(23);
        time.setMinutes(59);
        time.setSeconds(59);
        return  time ;
    }
    /**
     * 根据客服编号统计当日的接单量
     * @param jobNum 客服编号
     * @return
     */
    public  long countOrderNum(String jobNum){
        return  entityDao.countOrderNum(jobNum);
    }


    /**
     * 查询员工分页
     * @param params
     * @return
     */
    public List<Map<String,String>> findEmployeeByPage(Map<String, Object> params){
        return entityDao.findEmployeeByPage(params);
    }

    /**
     * 报表中心  统计 派单数
     * @param params
     * @return
     */
    public long countOrderNumByDate(Map<String, Object> params){
        return entityDao.countOrderNumByDate(params);
    }
    /**
     * 报表中心  统计 待邀约数
     * @param params
     * @return
     */
    public long countByTaskNodeAndDate(Map<String, Object> params){
        return entityDao.countByTaskNodeAndDate(params);
    }

    /**
     * 报表中心  统计 已邀约数
     * @param params
     * @return
     */
    public long countAlreadyInvitedAndDate(Map<String, Object> params){
        return entityDao.countAlreadyInvitedAndDate(params);
    }
    /**
     * 报表中心  统计 已接待客户数
     * @param params
     * @return
     */
    public long countReceptionCustomerAndDate(Map<String, Object> params){
        return entityDao.countReceptionCustomerAndDate(params);
    }

    /**
     * 从单点登录中 同步用户信息:
     *  1.首先请求认证中心获取accessToken，然后以accessToken为参数请求对应的接口，
     *    获取本系统下所有账户
     *  2.根据账户名称查询acct_employee表中，是否存在，不存在的话插入用户信息
     */
    public boolean synchroUser(){
        boolean resultFlag = false;
        try {
            //首先调用AppToken接口，获取AppToken
            NameValuePair appid = new BasicNameValuePair("appid", PropertyHolder.getOauthCenterAppid());
            NameValuePair secret = new BasicNameValuePair("secret", PropertyHolder.getOauthCenterSecret());

            String appTokenRespResult = HttpUtils.post(PropertyHolder.getoAuthAppTokenUrl(), appid,secret);
            logger.info("调用认证中心appToken接口，返回结果：" + appTokenRespResult);
            Map<String, Object> appTokenResultMap = JsonUtils.fromJsonAsMap(appTokenRespResult,String.class,Object.class);
            Map<String,String> appTokenData = (Map<String,String>)appTokenResultMap.get("data");
            NameValuePair accessToken = new BasicNameValuePair("accessToken", appTokenData.get("access_token"));
            //请求用户账号接口
            String appUserRespResult = HttpUtils.post(PropertyHolder.getoAuthAppUserUrl(), appid,accessToken);
            logger.info("调用认证中心appUser接口，返回结果：" + appUserRespResult);

            Map<String, Object> appUserResultMap = JsonUtils.fromJsonAsMap(appUserRespResult,String.class,Object.class);
            Map<String,Object> appUserData = (Map<String,Object>)appUserResultMap.get("data");
            //获取到的所有用户
            List<Map<String,String>> accountList = (List<Map<String,String>>)appUserData.get("users");
            if( accountList != null && accountList.size() >0 ){

                //查询本系统中所有用户
                List<Employee> employeeExcludeAdminList = employeeDao.findAllWithDelete();
                Map<String,Employee> employeeExcludeAdminMap = Maps.newHashMap();
                if( employeeExcludeAdminList != null && employeeExcludeAdminList.size() > 0 ){
                    for(Employee employee : employeeExcludeAdminList){
                        //使用jobNum 作为主键 去分辨用户
                        employeeExcludeAdminMap.put(employee.getJobNum(), employee);
                    }
                }

                for(Map<String,String> map: accountList){
                    Employee employee = new Employee();
                    if (employeeExcludeAdminMap.get( map.get("jobNum") ) == null) {
                        //当前系统中 没有该用户
                        employee.setEmpName(map.get("name"));
                        employee.setOrgCode(map.get("orgCode"));
                        employee.setMobile(map.get("mobile"));
                        employee.setJobNum(map.get("jobNum"));
                        employee.setStatus("1");
                        employee.setAutoOrder("Y");
                        employeeDao.insert(employee);
                    }else{
                        //当前系统中 有该用户,就将单点中的用户 name和orgCode更新到本地系统
                        employee.setId(((Employee)employeeExcludeAdminMap.get( map.get("jobNum"))).getId());
                        employee.setEmpName(map.get("name"));
                        employee.setOrgCode(map.get("orgCode"));
                        employee.setMobile(map.get("mobile"));
                        //修改时 都将状态变为 正常, 保证本地系统中用户唯一
                        employee.setStatus("1");
                        employeeDao.update(employee);
                        employeeExcludeAdminMap.remove(map.get("jobNum") );
                    }
                }

                //遍历删除剩余的原系统人员--将本系统中存在,但单点中没有的用户删除掉
                for(Employee delemployee : employeeExcludeAdminMap.values()){
                    this.employeeDao.deleteById(delemployee.getId());
                }
            }
            resultFlag = true;
        } catch (Exception e) {
            logger.error("调用认证中心接口出现异常，异常信息" + e.getMessage());
        }
        return resultFlag;
    }

}
