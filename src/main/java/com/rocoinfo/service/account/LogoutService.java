package com.rocoinfo.service.account;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

/**
 * <dl>
 * <dd>Description: 登出service</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-6-21 15:49:05</dd>
 * <dd>@author：Andy</dd>
 * </dl>
 */
@Service
public class LogoutService {

	/**
	 * 登出
	 */
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipal() != null) {
            subject.logout();
        }
    }
}
