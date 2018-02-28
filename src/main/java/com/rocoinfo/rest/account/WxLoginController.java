package com.rocoinfo.rest.account;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.rocoinfo.common.BaseController;

/**
 * <dl>
 * <dd>Description: 登出接口</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-6-12 11:09:00</dd>
 * <dd>@author：Andy</dd>
 * </dl>
 */
@RestController
@RequestMapping(value = "/api/wx/login")
public class WxLoginController extends BaseController {
	
	@RequestMapping(method = RequestMethod.GET)
	public Object login() {
		return new ModelAndView("redirect:/wx/api/appindex.html");
	}
}
