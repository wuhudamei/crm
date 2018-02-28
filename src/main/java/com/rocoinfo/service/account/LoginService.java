package com.rocoinfo.service.account;

import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.shiro.ShiroUser;
import com.rocoinfo.shiro.token.CustomUsernamePasswordToken;
import com.rocoinfo.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <dl>
 * <dd>Description: </dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2016/10/27 13:32</dd>
 * <dd>@author：Aaron</dd>
 * </dl>
 */
@Service
public class LoginService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LogoutService logoutService;

    /**
     * 登录
     * @param token
     * @throws AuthenticationException
     */
    public void login(AuthenticationToken token) throws AuthenticationException {
        Subject subject = SecurityUtils.getSubject();
        //如果已登录，先退出
        if (subject.getPrincipal() != null) {
            logoutService.logout();
        }
        //登录
        subject.login(token);
    }
}
