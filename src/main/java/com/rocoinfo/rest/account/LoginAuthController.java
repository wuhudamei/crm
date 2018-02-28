package com.rocoinfo.rest.account;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.rocoinfo.Constants;
import com.rocoinfo.common.BaseController;
import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.entity.employee.Employee;
import com.rocoinfo.rest.crmorder.OrderSystemOpt;
import com.rocoinfo.service.account.LoginService;
import com.rocoinfo.service.employee.EmployeeService;
import com.rocoinfo.shiro.token.CustomUsernamePasswordToken;
import com.rocoinfo.utils.HttpUtil;
import com.rocoinfo.utils.JsonUtils;

/**
 * <dl>
 * <dd>Description: 用户登录授权Controller</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-6-9 15:51:37</dd>
 * <dd>@author：Andy</dd>
 * </dl>
 */
@RestController
@RequestMapping(value = "/oauthCallBack")
public class LoginAuthController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(LoginAuthController.class);// 日志

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private LoginService loginService;

	/** 认证中心 相关参数 start **/
	private static final String CODE = "code"; // 返回码
	private static final String CODE_1 = "1"; // 返回码1-成功
	private static final String MESSAGE = "message"; // 返回信息
	private static final String ROLES = "roles"; // 角色信息
	private static final String SCOPE = "scope"; // 操作权限
	private static final String DATA = "data"; // 数据
//	private static final String DATA_ACCESS_TOKEN = "access_token"; // token
	private static final String DATA_USERINFO = "userinfo"; // 用户信息
	private static final String DATA_USERINFO_NAME = "name"; // 用户名
	private static final String DATA_USERINFO_MOBILE = "mobile"; // 手机号
//	private static final String DATA_USERINFO_EMAIL = "email"; // 邮箱
	private static final String DATA_USERINFO_USERNAME = "username"; // 员工号(对应jobNo)
	private static final String DATA_USERINFO_DEPCODE = "depCode"; // 部门码
	private static final String DATA_USERINFO_ORGCODE = "orgCode"; // 集团码
	/** 认证中心 相关参数 start **/

	private static final String AUTO_ORDER = "Y";	//是否自动接单
	private static final String BUSY_THRESHOLD = "15";	//忙碌阈值
	private static final String STATUS = "1"; //默认状态为启用
		
	private static final String INDEX_PAGE = "redirect:/index";
	
	/**
	 * 认证中心回调
	 * 
	 * @param code
	 * @param state
	 * @return {"code":"1","message":"success!","data":{"access_token":
	 *         "3c0580d54eef39458881fd5b0559d0ce",
	 *         "userinfo":{"name":"张三","mobile":"13888888888","id":2,"email":
	 *         "test@163.com","username":"zzc"}},"success":true}
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Object oauthCallBack(@RequestParam String code, @RequestParam String state) {
		String requiredUrl = INDEX_PAGE;
		StringBuilder getTokenUrl = new StringBuilder();
		getTokenUrl.append(Constants.URL_PREFIX + PropertyHolder.getOauthCenterUrl()).append(Constants.OAUTH_CENTER_TOKEN_URL);
		String retParam = "";
		try {
			retParam = HttpUtil.post(getTokenUrl.toString(), PropertyHolder.getOauthCenterAppid(),
					PropertyHolder.getOauthCenterSecret(), code, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (StringUtils.isNotBlank(retParam)) {
			Map<String, Object> jsonMap = JsonUtils.fromJsonAsMap(retParam, String.class, Object.class);
			if (jsonMap.get(CODE) != null && CODE_1.equals(jsonMap.get(CODE).toString())) {
				if (jsonMap.get(DATA) != null) {
					Map<String, Object> userMap = (Map<String, Object>) jsonMap.get(DATA);
					Map<String, Object> userinfoMap = (Map<String, Object>) userMap.get(DATA_USERINFO);
					List<String> rolesList = (List<String>) userMap.get(ROLES);
					List<String> scopeList = (List<String>) userMap.get(SCOPE);

					Employee employee = employeeService
							.getOneByJobNum(userinfoMap.get(DATA_USERINFO_USERNAME).toString());
					int insertRow = 0;
					Long id = 0L;
					if(employee == null){
						Employee tmpEmployee = new Employee();
						tmpEmployee.setJobNum(userinfoMap.get(DATA_USERINFO_USERNAME) == null ? "" : userinfoMap.get(DATA_USERINFO_USERNAME).toString());
						tmpEmployee.setDepCode(userinfoMap.get(DATA_USERINFO_DEPCODE) == null ? "" : userinfoMap.get(DATA_USERINFO_DEPCODE).toString());
						tmpEmployee.setOrgCode(userinfoMap.get(DATA_USERINFO_ORGCODE) == null ? "" : userinfoMap.get(DATA_USERINFO_ORGCODE).toString());
						tmpEmployee.setEmpName(userinfoMap.get(DATA_USERINFO_NAME) == null ? "" : userinfoMap.get(DATA_USERINFO_NAME).toString());
						tmpEmployee.setMobile(userinfoMap.get(DATA_USERINFO_MOBILE) == null ? "" : userinfoMap.get(DATA_USERINFO_MOBILE).toString());
						tmpEmployee.setAutoOrder(AUTO_ORDER); //自动接单
						tmpEmployee.setBusyThreshold(BUSY_THRESHOLD); //忙碌阈值
						tmpEmployee.setSort(1L); //默认排序都为1
						tmpEmployee.setStatus(STATUS); //默认启用
						insertRow = employeeService.insert(tmpEmployee);
						id = tmpEmployee.getId();
					}else{
						id = employee.getId();
						Employee tmpEmployee = new Employee();
						tmpEmployee.setOrgCode(userinfoMap.get(DATA_USERINFO_ORGCODE) == null ? "" : userinfoMap.get(DATA_USERINFO_ORGCODE).toString());
						tmpEmployee.setId(id);
						employeeService.update(tmpEmployee);
					}
					if(employee !=null || insertRow > 0){
						login(new CustomUsernamePasswordToken(id,userinfoMap.get(DATA_USERINFO_USERNAME).toString(),
								userinfoMap.get(DATA_USERINFO_NAME).toString(), rolesList, scopeList, true));
						if(employee != null && StringUtils.isNotBlank(employee.getStoreCode()) && StringUtils.isBlank(employee.getEmpIdinord())){//如果crm客服没有订单系统的id,则接口同步(异步)
							OrderSystemOpt.synOrderSystemEmpId(id,employee.getEmpName(),employee.getMobile(), employeeService);
						}
					}
				}
			} else {
				logger.error("oauth return code is 0 , error message 【" + jsonMap.get(MESSAGE).toString() + "】");
			}
		}
		
		if(StringUtils.isNotBlank(state) && !"state".equals(state)){
			requiredUrl = "redirect:" + state;
		}
		return new ModelAndView(requiredUrl);
	}

	/**
	 * 执行登录
	 * 
	 * @param token
	 * @throws AuthenticationException
	 */
	public void login(AuthenticationToken token) throws AuthenticationException {
		loginService.login(token);
	}
}
