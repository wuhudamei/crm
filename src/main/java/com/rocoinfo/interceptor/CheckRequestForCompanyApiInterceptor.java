package com.rocoinfo.interceptor;

import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.interceptor.sign.NewCrmApiSignatureHelper;
import com.rocoinfo.utils.JsonUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author liupengfei
 * @Description
 * @Date Created in 2017/12/27 16:12
 */
public class CheckRequestForCompanyApiInterceptor implements HandlerInterceptor {

    /**
     *  对请求进行 拦截过滤
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String reqJson = request.getParameter("requestKey");
        boolean signPass = NewCrmApiSignatureHelper.signAuth(reqJson);
        if(!signPass) {
            //向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8,其实设置了本句，也默认设置了Response的编码方式为UTF-8，但是开发中最好两句结合起来使用
            response.setHeader("Content-type","text/html;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(JsonUtils.toJson(StatusDto.buildFailure("签名错误")));
        }
        return signPass;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
