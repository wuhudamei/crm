package com.rocoinfo.quartz;

import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.entity.basicConfig.Dictionary;
import com.rocoinfo.entity.order.SyncOrderInfo;
import com.rocoinfo.repository.order.OrderInfoDao;
import com.rocoinfo.service.basicConfig.DictionaryService;
import com.rocoinfo.service.order.OrderService;
import com.rocoinfo.weixin.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：每隔几分钟从订单系统同步一段时间内的订单部分数据到CRM系统中
 *
 * @author tony
 * @创建时间 2017-07-17 14:31
 */
@SuppressWarnings("all")
@Service("syncOrderInfoSchedule")
public class SyncOrderInfoSchedule {

    @Autowired
    private OrderInfoDao orderInfoDao;

    /**订单服务*/
    @Autowired
    private OrderService orderService;

    /**数据字典服务*/
    @Autowired
    private DictionaryService dictionaryService;

    public void executor() {
        // 从现在往前推多少天内的订单
        int minusDays = PropertyHolder.getSynchronousInterval();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.minusDays(minusDays);

        Map map = new HashMap();
        map.put("type", "between");
        map.put("startTime", start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        map.put("endTime", now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        // 取得门店列表
        List<Dictionary> dictionaryList = dictionaryService.findSubDictListByCode("MD");

        // 假如没有门店，直接返回
        if (dictionaryList == null || dictionaryList.size() == 0) return;

        for (Dictionary d : dictionaryList) {
            map.put("storeCode", d.getCode());
            inBatches(orderInfoDao.search(map), d);
        }
    }

    private void inBatches(List<SyncOrderInfo> list, Dictionary dictionary) {
        // 如果没有需要同步的数据，则直接返回
        if (list != null && list.size() == 0) return;

        int batchNum = PropertyHolder.getBatchNum();

        // 如果小于等于同步数量，则直接开始同步
        if (list.size() <= batchNum) {
            syncData(list, dictionary);
        }
        // 否则分批同步
        else {
            int l = list.size();
            // 没设置的话直接返回
            if (batchNum == 0)return;

            int time = l % batchNum == 0 ? l / batchNum : l / batchNum + 1;
            for (int i = 0; i < time; i++) {
                int start = i * batchNum;
                int end = start + batchNum;
                if  (end > l) end = l;
                syncData(list.subList(start, end), dictionary);
            }
        }
    }

    private void syncData(List<SyncOrderInfo> list, Dictionary dictionary) {
        // 如果没有需要同步的数据，则直接返回
        if (list.isEmpty()) {
            return;
        }

        StringBuffer sbCusId = new StringBuffer();
        StringBuffer sbOrdNum = new StringBuffer();
        for (SyncOrderInfo syncOrderInfo : list) {
            //客户在crm中id为空的不同步
            if(StringUtils.isNotBlank(syncOrderInfo.getCustomerIdinord())) {
                sbCusId.append(syncOrderInfo.getCustomerIdinord()).append(",");
            }
            sbOrdNum.append((syncOrderInfo.getOrderNum())).append(",");
        }

        Map ordMap = new HashMap();
        if(sbCusId.length()>0) {
            ordMap.put("cusIdArrStr", sbCusId.substring(0, sbCusId.length() - 1));
        }
        if(sbOrdNum.length()>0) {
            ordMap.put("ordNoArrStr", sbOrdNum.substring(0, sbOrdNum.length() - 1));
        }

        List<SyncOrderInfo> resultList = orderService.ordsFinance(ordMap, dictionary.getCode());

        // 同步失败或没有数据可同步
        if (resultList == null || resultList.isEmpty()){
            return;
        }
        //因为需要按orderId更新，而crmapi没查出来orderId，
        // 所以要用这么笨的方法,两个循环匹配一下
        List<SyncOrderInfo> updateList = new ArrayList<SyncOrderInfo>();
        for (SyncOrderInfo syncOrderInfo : list) {
            for (SyncOrderInfo _soi : resultList) {
                if (syncOrderInfo.getOrderNum().equals(_soi.getOrderNum())) {
                    syncOrderInfo.setDepositAmount(_soi.getDepositAmount());
                    syncOrderInfo.setDepositAbleback(_soi.getDepositAbleback());
                    syncOrderInfo.setDepositFinish(_soi.getDepositFinish());
                    syncOrderInfo.setDepositTime(_soi.getDepositTime());
                    syncOrderInfo.setOrderClosed(_soi.getOrderClosed());
                    syncOrderInfo.setOrderCloseTime(_soi.getOrderCloseTime());
                    updateList.add(syncOrderInfo);
                    break;
                }
            }
        }

        orderInfoDao.updateList(updateList);
    }
}
