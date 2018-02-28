package com.rocoinfo.rest.reportCenter;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.rocoinfo.Constants;
import com.rocoinfo.common.BaseController;
import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.dto.StatusBootTableDto;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.dto.page.PageTable;
import com.rocoinfo.dto.page.Pagination;
import com.rocoinfo.dto.page.Sort;
import com.rocoinfo.entity.task.Report;
import com.rocoinfo.entity.task.TaskDistribute;
import com.rocoinfo.enumeration.CustomerStatus;
import com.rocoinfo.service.employee.EmployeeService;
import com.rocoinfo.service.task.TaskDistributeService;
import com.rocoinfo.utils.CrmApiUtil;
import com.rocoinfo.utils.JsonUtils;
import com.rocoinfo.utils.MapUtils;
import com.rocoinfo.utils.excel.DateUtil;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * <dl>
 * <dd>Description: 美得你crm 报表中心</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/7/17</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@RestController
@RequestMapping("/api/reportCenter")
public class ReportCenterController extends BaseController {

    private static final String CUMULATIVE_DISPATCH = "cumulativeDispatch";
    private static final String CURRENT_INVITATIONS = "currentInvitations";
    private static final String CUMULATIVE_UNSOLICITED_QUANTITY = "cumulativeUnsolicitedQuantity";
    private static final String TOTAL_CUSTOMERS_RECEIVED = "totalCustomersReceived";
    private static final String JOB_NUM = "jobNum";
    private static final String END_DATE = "endDate";
    private static final String START_DATE = "startDate";
    public static final String CUMULATIVE_SMALL_NUMBER = "cumulativeSmallNumber";
    public static final String CUMULATIVE_BIG_NUMBER = "cumulativeBigNumber";
    public static final String CUMULATIVE_BACK = "cumulativeBack";
    @Autowired
    TaskDistributeService distributeService;
    @Autowired
    EmployeeService employeeService;

    /**
     * 统计 来源报表 //dataSources
     *
     * @param sourceCode 来源
     * @param startDate  开始时间（必填）
     * @param endDate    结束时间（必填）
     * @param storeCode  门店（必填）
     * @return
     */
    @RequestMapping("/source")
    public Object CountSource(@RequestParam(value = "sourceCode", required = false) String sourceCode,
                              @RequestParam(value = "startDate", required = false) String startDate,
                              @RequestParam(value = "endDate", required = false) String endDate,
                              @RequestParam(value = "storeCode", required = false) String storeCode) {
        return reportSourceOrPromote("sourceCode", sourceCode, TaskDistribute::getDataSourceName, (a, b) -> {
            a.setSourceName(b);
            return a;
        }, (a, b) -> {
            a.setSourceRate(b);
            return a;
        }, startDate, endDate, storeCode);
    }

    /**
     * 统计 渠道报表 //promoteWays
     *
     * @param promoteSource 渠道
     * @param startDate     开始时间（必填）
     * @param endDate       结束时间（必填）
     * @param storeCode     门店（必填）
     * @return
     */
    @RequestMapping("/channel")
    public Object CountChannel(@RequestParam(value = "promoteSource", required = false) String promoteSource,
                               @RequestParam(value = "startDate", required = false) String startDate,
                               @RequestParam(value = "endDate", required = false) String endDate,
                               @RequestParam(value = "storeCode", required = false) String storeCode) {
        return reportSourceOrPromote("promoteSource", promoteSource, TaskDistribute::getPromoteSourceName, (a, b) -> {
            a.setPromoteName(b);
            return a;
        }, (a, b) -> {
            a.setChannelRate(b);
            return a;
        }, startDate, endDate, storeCode);
    }

    /**
     * 统计 个人报表
     *
     * @param startDate 开始时间（必填）
     * @param endDate   结束时间（必填）
     * @param storeCode 门店（必填）
     * @return
     */
    @RequestMapping("/person")
    public Object CountChannel(@RequestParam(value = "startDate", required = false) String startDate,
                               @RequestParam(value = "endDate", required = false) String endDate,
                               @RequestParam(value = "storeCode", required = false) String storeCode) {
        PageRequest pageable = new PageRequest(1, 1, null);
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "startDate", startDate);
        MapUtils.putNotNull(params, "endDate", endDate);
        MapUtils.putNotNull(params, "storeCode", storeCode);
        //查询出 大集合
        List<TaskDistribute> reportForCommunicateAndOrderList = distributeService.findReportForCommunicateAndOrder(params);
        List<Report> back = new ArrayList<Report>();
        if (reportForCommunicateAndOrderList != null) {
            final Date startDateFor = DateUtil.parse(startDate, DateUtil.DATE_SMALL_STR);
            final Date endDateFor = DateUtil.parse(endDate, DateUtil.DATE_SMALL_STR);
            //分组
            Map<String, List<TaskDistribute>> collect = reportForCommunicateAndOrderList.stream().filter(b -> b.getEmpName() != null).collect(Collectors.groupingBy(a -> a.getEmpName()));
            //统计相应的数值的工具 两位小数
            NumberFormat fmt = NumberFormat.getPercentInstance();
            fmt.setMaximumFractionDigits(2);
            for (Map.Entry<String, List<TaskDistribute>> map : collect.entrySet()) {
                Report report = new Report();
                String key = map.getKey();
                List<TaskDistribute> value = map.getValue();
                //名字
                report.setEmpName(key);
                //累计派单量
                report.setCumulativeDispatch(value.size()+"");
                //当前待邀约量
                report.setCurrentInvitations(value.stream().filter(a->a.getInvitation(startDateFor,endDateFor)==null).count()+"");
                //累计已邀约量
                report.setCumulativeUnsolicitedQuantity(value.stream().filter(a->a.getInvitation(startDateFor,endDateFor)!=null).count()+"");
                //累计接待客户数
                report.setTotalCustomersReceived(value.stream().filter(a->a.getInCustomer(startDateFor,endDateFor)!=null).count()+"");
                //累计小定数
                report.setCumulativeSmallNumber(value.stream().map(a -> a.getOrderSmallSet(startDateFor, endDateFor)).reduce((aLong, aLong2) -> aLong+aLong2)+"");
                //累计大定数
                report.setCumulativeBigNumber(value.stream().map(a -> a.getOrderBigSet(startDateFor, endDateFor)).reduce((aLong, aLong2) -> aLong+aLong2)+"");
                //累计退单数
                report.setCumulativeBack(value.stream().map(a -> a.getOrderBack(startDateFor, endDateFor)).reduce((aLong, aLong2) -> aLong+aLong2)+"");
                back.add(report);
            }
        }
        // 返回查询结果
        PageImpl page = new PageImpl(back, pageable, back.size());
        return StatusBootTableDto.buildDataSuccessStatusDto(page);
    }

    /**
     * @param name           名称
     * @param strValue       值
     * @param classifier     分组依据
     * @param biFunctionName set名称
     * @param biFunction     set率
     * @param startDate      开始时间
     * @param endDate        结束时间
     * @param storeCode      门店编号
     * @Description: 美得你智装
     * @date: 2018/1/11  10:56
     * @author: Ryze
     */
    private Object reportSourceOrPromote(String name, String strValue, Function<TaskDistribute, String> classifier,
                                         BiFunction<Report, String, Report> biFunctionName, BiFunction<Report, String, Report> biFunction,
                                         String startDate, String endDate, String storeCode) {
        PageRequest pageable = new PageRequest(1, 1, null);
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, name, strValue);
        MapUtils.putNotNull(params, "startDate", startDate);
        MapUtils.putNotNull(params, "endDate", endDate);
        MapUtils.putNotNull(params, "storeCode", storeCode);
        //查询出 大集合
        List<TaskDistribute> reportForCommunicateAndOrderList = distributeService.findReportForCommunicateAndOrder(params);
        List<Report> back = new ArrayList<Report>();
        if (reportForCommunicateAndOrderList != null) {
            final Date startDateFor = DateUtil.parse(startDate, DateUtil.DATE_SMALL_STR);
            final Date endDateFor = DateUtil.parse(endDate, DateUtil.DATE_SMALL_STR);
            //所有大定
            long allBigSet = reportForCommunicateAndOrderList.stream().map(a -> a.getOrderBigSet(startDateFor, endDateFor)).reduce(0L,(aLong, aLong2) -> aLong+aLong2);
            //分组
            Map<String, List<TaskDistribute>> collect = reportForCommunicateAndOrderList.stream().collect(Collectors.groupingBy(a -> classifier.apply(a)));
            //统计相应的数值的工具 两位小数
            NumberFormat fmt = NumberFormat.getPercentInstance();
            fmt.setMaximumFractionDigits(2);
            for (Map.Entry<String, List<TaskDistribute>> map : collect.entrySet()) {
                Report report = new Report();
                String key = map.getKey();
                List<TaskDistribute> value = map.getValue();
                //名字
                biFunctionName.apply(report, key);
                //进线数
                report.setCountLine(Long.valueOf(value.size()));
                //进店数
                report.setCountShop(value.stream().filter(a -> a.getOrderShop(startDateFor, endDateFor) != null).count());
                //大定数
                report.setBigSet(value.stream().map(a -> a.getOrderBigSet(startDateFor, endDateFor)).reduce(0L,(aLong, aLong2) -> aLong+aLong2));
                //邀约任务数量
                long count = value.stream().filter(a -> a.getInvitation(startDateFor, endDateFor) != null).count();
                //邀约率
                Long countLine = report.getCountLine();
                if (countLine != null && countLine.intValue() != 0) {
                    report.setInvitationRate(fmt.format(Double.longBitsToDouble(count) / Double.longBitsToDouble(countLine)));
                    //线索转化率
                    report.setTransformationRate(fmt.format(Double.longBitsToDouble(report.getBigSet()) / Double.longBitsToDouble(countLine)));
                    //进店转化率
                    report.setGoShopRate(fmt.format(Double.longBitsToDouble(report.getCountShop()) / Double.longBitsToDouble(countLine)));
                }
                //来源贡献率
                if (allBigSet != 0) {
                    biFunction.apply(report, fmt.format(Double.longBitsToDouble(report.getBigSet()) / Double.longBitsToDouble(allBigSet)));
                }
                back.add(report);
            }

        }
        // 返回查询结果
        PageImpl page = new PageImpl(back, pageable, back.size());
        return StatusBootTableDto.buildDataSuccessStatusDto(page);
    }

}
