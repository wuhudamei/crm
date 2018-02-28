package com.rocoinfo.rest.web;

import com.rocoinfo.common.BaseController;
import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.entity.task.TaskDistribute;
import com.rocoinfo.service.task.TaskDistributeService;
import com.rocoinfo.utils.JsonUtils;
import com.rocoinfo.utils.des.Md5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 微信端 controller
 * @author Paul
 * @date 2017/8/8
 */
@Controller
@RequestMapping(value = "/api/web")
public class WebController extends BaseController {

    @Autowired
    private TaskDistributeService taskDistributeService;

    /**
     * 获取小美返单列表数据
     * @return
     */
    @RequestMapping(value = "/findCustomerFromIntroducerId")
    public @ResponseBody Object findCustomerFromIntroducerId(String introducerId){

        if(StringUtils.isBlank(introducerId)){
            return StatusDto.buildFailure("推荐人id不能为空!");
        }
        //通过传入的推荐人id,去获取该推荐人下 所有的任务列表
        List<TaskDistribute> taskList = taskDistributeService.findTaskByIntroducerId(introducerId);
        return StatusDto.buildSuccess(taskList);
    }


    /**
     * 跳转小美返单列表页面
     * @param request 含有:reqJson请求json数据(map转json)  reqSign: 签名
     *                 callId  ts:时间戳
     * @return
     */
    /*@RequestMapping(value = "/taskListFromXMFD")
    public String taskListFromXMFD(HttpServletRequest request, Model model){
        //校验签名
        StatusDto checkResult = checkRequestSign(request);
        *//*if("0".equals(checkResult.getCode())){
            return null;
        }*//*

        HashMap<String, String> map = new HashMap<>();
        map.put("introducerId","35");
        String reqJson = JsonUtils.toJson(map);

        Map<String, String> reqJsonMap = JsonUtils.fromJsonAsMap(reqJson, String.class, String.class);

        model.addAttribute("introducerId",reqJsonMap.get("introducerId"));

        //跳转到列表页面
        return "forward:/WEB-INF/views/admin/wechat/task/taskListFromXMFD.jsp";
    }*/

    /**
     * 跳转小美返单首页 ,并将小美返单新增页/返单列表页 参数:
     *      推荐人/推荐人手机号/推荐人id/门店 传递到页面上
     * @param request 含有:reqJson请求json数据(map转json)  reqSign: 签名
     *                 callId  ts:时间戳
     * @param model
     * @return
     */
    @RequestMapping(value = "/indexToXMFD")
    public String addTaskFromXMFD(HttpServletRequest request, Model model){
        //校验签名
        StatusDto checkResult = checkRequestSign(request);
        /*if("0".equals(checkResult.getCode())){
            return null;
        }*/

        HashMap<String, String> map = new HashMap<>();
        map.put("introducer","我是推荐人");
        map.put("introducerTel","18500001122");
        map.put("introducerId","35");
        map.put("store","BJ1");
        String reqJson = JsonUtils.toJson(map);

        Map<String, String> reqJsonMap = JsonUtils.fromJsonAsMap(reqJson, String.class, String.class);

        model.addAttribute("introducer",reqJsonMap.get("introducer"));
        model.addAttribute("introducerTel",reqJsonMap.get("introducerTel"));
        model.addAttribute("introducerId",reqJsonMap.get("introducerId"));
        model.addAttribute("store",reqJsonMap.get("store"));

        //return "forward:/WEB-INF/views/admin/wechat/task/addTaskFromXMFD.jsp";
        return "forward:/WEB-INF/views/admin/wechat/task/indexToXMFD.jsp";
    }

    /**
     * 校验请求的签名
     */
    private StatusDto checkRequestSign(HttpServletRequest request){
		/*取到必要信息*/
        String reqJson = request.getParameter("reqJson");
        String reqSign = request.getParameter("reqSign");
        String callId = request.getParameter("callId");
        String ts = request.getParameter("ts");

		/*错误：必要信息没有值(reqJson可以为"")*/
        if(StringUtils.isBlank(reqSign) || StringUtils.isBlank(callId) || StringUtils.isBlank("ts"))
            return StatusDto.buildFailure("请求必要参数缺失");

		/*错误：签名异常*/
        String mySignStr = generateSign(reqJson,callId,ts);
        if(StringUtils.isBlank(mySignStr) || !reqSign.equals(mySignStr))
            return StatusDto.buildFailure("签名异常");

		/*正常*/
        return null;
    }

    /**
     *
     * 函数功能描述:生成签名字符串
     * @param reqJson
     * @param callId
     * @param ts
     * @return
     */
    private String generateSign(String reqJson,String callId,String ts){

		/*获取callSecret，获取不到直接返回一个乱起八糟的签名串*/
        String secretStr = "";
        if(PropertyHolder.callCertificateMap.containsKey(callId))
            secretStr = PropertyHolder.callCertificateMap.get(callId);
        if(StringUtils.isBlank(secretStr))
            return Md5Utils.generate("无法确定签名密钥"+new Date().getTime());

		/*生成用来比对的签名串*/
        String tsEncStr = Md5Utils.generate(ts);
        return Md5Utils.generate(tsEncStr+reqJson+secretStr);
    }
}
