package com.rocoinfo.repository.schedule;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.schedule.Schedule;

/**
 * <dl>
 * <dd>Description: 美得你crm 日程dao</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/7</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@Repository
public interface ScheduleDao extends CrudDao<Schedule> {
	
	/**
	 * 根据日期查询
	 * @param date 日期
	 * @return
	 */
	List<Schedule> findScheduleByDate(@Param("date") String date);

	/**
	 * 删除该任务该客服当天以后的日程提醒
	 * @param jobNum
	 * @param taskNo
	 * @param date
	 */
	void deleteAfterNow(@Param("jobNum") String jobNum, @Param("taskNo") String taskNo, @Param("now") Date date);
}
