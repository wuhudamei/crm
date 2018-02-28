package com.rocoinfo.rest.crmorder;

import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.entity.order.Order;
import com.rocoinfo.entity.order.SyncOrderInfo;
import com.rocoinfo.quartz.SyncOrderInfoSchedule;
import com.rocoinfo.repository.order.OrderInfoDao;
import com.rocoinfo.service.order.OrderService;
import com.rocoinfo.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author liupengfei
 * @Description 订单接口API，公司内部用的
 * @Date Created in 2017/12/25 18:00
 */

@RestController
@RequestMapping(value = "/company/api/ord")
public class OrderCompanyApiController {

    @Autowired
    private OrderService orderService;



    /**
     * 同步订单的大小定信息(为新订单选选材系统提供)
     * @param requestJson 请求内容json串
     * @return
     */
    @RequestMapping("/syncdeposit")
    public Object syncOrderDepositInfo(@RequestParam("requestKey") String requestJson){
        Map<String,Object> reqMap = JsonUtils.fromJsonAsMap(requestJson,String.class,Object.class);
        //取出业务参数map
        String reqJsonStr = reqMap.get("reqJson").toString();
        Map<String,Object> paramMap = JsonUtils.fromJsonAsMap(reqJsonStr,String.class,Object.class);
        return orderService.syncOrderDepositInfo(paramMap);
    }


}
