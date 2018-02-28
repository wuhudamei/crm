package com.rocoinfo.rest.task;

import com.rocoinfo.common.BaseController;
import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.entity.task.TaskDistribute;
import com.rocoinfo.enumeration.TaskNodeStatus;
import com.rocoinfo.service.employee.EmployeeOrderSourceService;
import com.rocoinfo.service.task.TaskDistributeService;
import com.rocoinfo.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by javerliu on 2017/10/15.
 */
@RestController
@RequestMapping("/open/api/xmfd")
public class TaskOpenApiForXMFDController extends BaseController {

    private static String XMFD_DATASOURCE_CODE = "XMFD";

    @Autowired
    private TaskDistributeService taskService;

    @Autowired
    private EmployeeOrderSourceService empService;


    /**
     * 通过手机号查询任务是否重复
     *
     * @param cusMobile 客户手机号
     * @param storeCode 门店编号
     * @return
     */
    @RequestMapping(value="/ifTaskRepeatFromXMFD")
    public StatusDto ifRepeatTaskInCrm(@RequestParam(value="cusMobile") String cusMobile,
                                       @RequestParam(value = "storeCode") String storeCode){
        int xmfdRepeatSecondCD = PropertyHolder.getRepeatCDSec();
        boolean ifRepeat = taskService.checkIfTaskRepeatInPerioud(cusMobile,xmfdRepeatSecondCD,storeCode);
        return StatusDto.buildSuccess(ifRepeat);
    }


    /**
     * 查询某个门店的可以接小美返单的任务的客服员工列表
     *
     * @param storeCode
     * @return
     */
    @RequestMapping("/empsForXMFD")
    public StatusDto queryEmployeeOfXMFD(@RequestParam(value = "storeCode") String storeCode){
        List<String> storeCodes = new ArrayList<>();
        storeCodes.add(storeCode);

        List<String> sourceCodes = new ArrayList<>();
        sourceCodes.add(XMFD_DATASOURCE_CODE);
        return StatusDto.buildSuccess(empService.queryEmployeeInfoWithStoreAndSource(storeCodes,sourceCodes));
    }


    /**
     * 接收单个小美返单推送过来的任务线索
     *
     * @param reqJson
     * @return
     */
    @RequestMapping("/addTaskFromXMFD")
    public Object addTaskFromXMFDs(@RequestParam(value = "reqJson") String reqJson){

        TaskDistribute taskDistribute = JsonUtils.fromJson(reqJson,TaskDistribute.class);
        int xmfdRepeatSecondCD = PropertyHolder.getXmfdRepeatCdSec();
        taskDistribute.setDataSource(XMFD_DATASOURCE_CODE);
        return taskService.addBackTask(false, TaskNodeStatus.INVITATION.toString(), null, taskDistribute,xmfdRepeatSecondCD);
    }


    /**
     * 查询指定门店指定时间段内的客户进店信息
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("/inShopInfo")
    public Object queryInShopInfo(@RequestParam(value = "startTime") String startTime,
                                  @RequestParam(value = "endTime") String endTime){
        return taskService.queryCustomerIntoShop(startTime,endTime);
    }
}
