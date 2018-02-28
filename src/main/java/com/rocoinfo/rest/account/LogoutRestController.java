package com.rocoinfo.rest.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rocoinfo.Constants;
import com.rocoinfo.common.BaseController;
import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.service.account.LogoutService;
import com.rocoinfo.shiro.ShiroUser;
import com.rocoinfo.utils.HttpUtil;
import com.rocoinfo.utils.WebUtils;

/**
 * <dl>
 * <dd>Description: 登出接口</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-6-12 11:09:00</dd>
 * <dd>@author：Andy</dd>
 * </dl>
 */
@RestController
@RequestMapping(value = "/api/logout")
public class LogoutRestController extends BaseController {
	
	@Autowired
	private LogoutService logoutService;

	/**
	 * 登出,调用单点登录登出接口
	 * 
	 * @description 修改原登出接口,调用单点登录服务器
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Object logout() {
		ShiroUser loggedUser = WebUtils.getLoggedUser();
		if(loggedUser != null){
			try {
				String logout = logoutUse(loggedUser);
				return StatusDto.buildSuccess(logout);
			} catch (Exception e) {
				e.printStackTrace();
				return StatusDto.buildFailure("退出失败!" + e.getMessage());
			}
		}else{
			return StatusDto.buildSuccess();
		}
	}

	/**@Ryze
	 * @date 2017-7-31
	 * 登出 调用口的公共方法
	 */
	public String logoutUse(ShiroUser loggedUser) throws Exception {
		String logoutUrl = Constants.URL_PREFIX + PropertyHolder.getOauthCenterUrl() + Constants.OAUTH_LOGOUT_URL;
		try {
			String logout = HttpUtil.post(logoutUrl, PropertyHolder.getOauthCenterAppid(), PropertyHolder.getOauthCenterSecret(),
					loggedUser.getOrgCode());
			logoutService.logout();
			return logout;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
}
