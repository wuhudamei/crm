package com.rocoinfo.common;

import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.shiro.ShiroUser;
import com.rocoinfo.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <dl>
 * <dd>描述:</dd>
 * <dd>公司: 大城若谷信息技术有限公司</dd>
 * <dd>创建时间：15/8/11 下午3:47</dd>
 * <dd>创建人： weiys</dd>
 * </dl>
 */
@SuppressWarnings("all")
public abstract class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @ExceptionHandler({Exception.class})
    public Object exception(Exception ex) {
        logger.error("异常:", ex);
        return StatusDto.buildFailure("操作失败");
    }


    /**
     * 判断当前登陆人角色是否足以调用当前action方法
     * @param aimRole
     * @return
     */
    protected StatusDto checkFunctionAbleForRole(String aimRole){
        ShiroUser curUser = WebUtils.getLoggedUser();
        if(curUser == null || !curUser.getRoles().contains(aimRole)){
            return StatusDto.buildFailure("身份不足以使用该功能");
        }
        return null;
    }
}
