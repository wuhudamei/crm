
package com.rocoinfo;

/**
 * <dl>
 * <dd>描述:</dd>
 * <dd>公司: 大城若谷信息技术有限公司</dd>
 * <dd>@创建时间：2016/2/17 21:22</dd>
 * <dd>@author：Aaron</dd>
 * </dl>
 */
public class Constants {

	private Constants() {
	}

	/**
	 * 接口响应状态码 -成功
	 */
	public static final String RESP_STATUS_CODE_SUCCESS = "1";
	/**
	 * 接口响应状态码 -失败
	 */
	public static final String RESP_STATUS_CODE_FAIL = "0";
	
	public static final String URL_PREFIX = "http://";
	
	/** 需要注入的service名称 **/
	public static final String TASK_SERVICE_NAME = "taskDistributeCore";
	
	/** 循环派发规则 **/
	public static final String LOOP_DISTRIBUTE_RULE = "001";
	
	/** 是否自动接单-是 **/
	public static final String AUTO_ORDER_Y = "Y";
	/** 是否自动接单-否 **/
	public static final String AUTO_ORDER_N = "N";
	
	/** 认证中心获取code url**/
	public static final String OAUTH_CENTER_CODE_URL = "/oauth/code";
	/** 认证中心获取token url**/
	public static final String OAUTH_CENTER_TOKEN_URL = "/oauth/token";
	/** 认证中心回调 url**/
	public static final String OAUTH_CALL_BACK = "/oauthCallBack";
	/** 认证中心登出url**/
	public static final String OAUTH_LOGOUT_URL = "/oauth/logout";

	/** 认证中心修改密码url**/
	public static final String OAUTH_PASSWORD_URL = "/oauth/password";


	/** 未派发的任务队列 **/
	public static final String RECENT_CUSTOMER_PHONENO= "PHNOQ";
	/** 未派发的任务队列 **/
	public static final String TASK_DISTRIBUTE_QUEUE_KEY = "TQ";
	/** 跟单员队列 **/
	public static final String EMPLOYEE_ORDER_KEY = "EQ";
	/** 跟单员队列是否初始化数据 **/
	public static final String EMPLOYEE_ORDER_FLAG = "EFLAG";
	/** 跟单员接单规则 **/
	public static final String EMPLOYEE_ORDER_RULE_KEY = "ER";
	/** 跟单员奖励单数 **/
	public static final String EMPLOYEE_REWARD_NUM_KEY = "RN";
	/** 跟单员已奖励单数 **/
	public static final String EMPLOYEE_GIVE_REWARD_NUM_KEY = "GRN";
	/** 派发规则key **/
	public static final String DISTRIBUTE_RULE_KEY = "DR";

	/** 任务派发状态 已派发 **/
	public static final String AUTO_TASK_DISTRIBUTION_Y = "Y";
	/** 任务派发状态 未派发**/
	public static final String AUTO_TASK_DISTRIBUTION_N = "N";
	
	/** 订单标签列表 请求URL**/
	public static final String ORD_TAGINFO_LIST = "/api/ordTag/list";
	/** 新增订单标签 请求URL**/
	public static final String ORD_TAGINFO_ADD = "/api/ordTag/add"; 
	/** 给订单添加 订单标签 请求URL**/
	public static final String ORDER_WITH_TAG_UPDATE = "/api/ord/updateOrdWithTag";
	/** 请求crmapi查询订单系统中用户id **/
	public static final String MACTH_EMPID_URL = "/api/emp/macthEmpId";
	
	/** 数据来源名 **/
	public static final String DATASOURCE = "数据来源";
	/** 数据来源code **/
	public static final String DATASOURCE_CODE = "SJLY";
	
	/** 微信端 我的信息 统计  统计 统计时间段内某员工订单总结信息**/
	public static final String EMPLOYEE_SOME_TIME_MESSAGE = "/api/datarep/ordgkPeriod";
	/** 微信端 我的信息 统计  统计  某段内某员工订单每日信息**/
	public static final String EMPLOYEE_EVERY_MESSAGE = "/api/datarep/ordDtlPeriod";
	/** 报表中心 统计大定数 **/
	public static final String COUNT_BIG_SET = "/api/datarep/ordBigSet";
	/** 报表中心 统计一组员工的大定数  小定  退单 数量**/
	public static final String ORD_DTL_PERIOD_GROUP = "/api/datarep/ordDtlPeriodGroup";

	
	/************************************************微信发送模板消息相关 start ***************************************************************/
	/**	日程模板消息发送	url	**/
	public static final String SCHEDULE_REMINDER_URL = "/api/wx/template/sendScheduleTemplateMessage";
	/**	派发任务模板消息提醒发送	url	**/
	public static final String TASK_DISTRIBUTE_URL = "/api/wx/template/sendTaskDistributeTemplateMessage";
	
	/************************************************微信发送模板消息相关 end ***************************************************************/
}
