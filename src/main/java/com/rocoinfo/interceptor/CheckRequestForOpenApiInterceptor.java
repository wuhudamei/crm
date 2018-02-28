package com.rocoinfo.interceptor;

/**
 * @Description: 拦截器，执行前验证参数签名，执行之后将要返回的结果写入日志文件
 * @Author liupengfei
 * @Date 2015年1月27日  
 */  

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.rocoinfo.common.PropertyHolder;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.utils.JsonUtils;
import com.rocoinfo.utils.des.Md5Utils;


public class CheckRequestForOpenApiInterceptor extends HandlerInterceptorAdapter {
	
	// 排除拦截的url列表
	private List<String> excludeUrlPatterns;
	// 模式匹配验证器
	private PathMatcher pathMatcher = new AntPathMatcher();

	public void setExcludeUrlPatterns(List<String> excludeUrlPatterns) {
		this.excludeUrlPatterns = excludeUrlPatterns;
	}
	
	
	
	/**
	 * 拦截器主体，校验请求是否合法
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		StatusDto checkResult = checkRequestSign(request);
		//测试用，绕过验证
		//checkResult = null;
		if(checkResult == null)
			return true;
		else{
			String resultStr = JsonUtils.toJson(checkResult);
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.println(resultStr);
			out.flush();
			out.close();
			return false;
		}
	}
	
	
	
	/**
	 * 校验请求的签名
	 */
	private StatusDto checkRequestSign(HttpServletRequest request){
		/*取到必要信息*/
		String reqJson = request.getParameter("reqJson");
		String reqSign = request.getParameter("reqSign");
		String callId = request.getParameter("callId");
		String ts = request.getParameter("ts");
		
		/*错误：必要信息没有值(reqJson可以为"")*/
		if(StringUtils.isBlank(reqSign) || StringUtils.isBlank(callId) || StringUtils.isBlank("ts"))
			return StatusDto.buildFailure("请求必要参数缺失");
		
		/*错误：签名异常*/
		String mySignStr = generateSign(reqJson,callId,ts);
		if(StringUtils.isBlank(mySignStr) || !reqSign.equals(mySignStr))
			return StatusDto.buildFailure("签名异常");
		
		/*正常*/
		return null;
	}
	
	
	/**
	 * 
	 * 函数功能描述:生成签名字符串
	 * @param reqJson
	 * @param callId
	 * @param ts
	 * @return
	 */
	private String generateSign(String reqJson,String callId,String ts){
		
		/*获取callSecret，获取不到直接返回一个乱起八糟的签名串*/
		String secretStr = "";
		if(PropertyHolder.callCertificateMap.containsKey(callId))
			secretStr = PropertyHolder.callCertificateMap.get(callId);
		if(StringUtils.isBlank(secretStr))
			return Md5Utils.generate("无法确定签名密钥"+new Date().getTime());
		
		/*生成用来比对的签名串*/
		String tsEncStr = Md5Utils.generate(ts);
		StringBuffer sb = new StringBuffer(tsEncStr);
		sb.append(reqJson==null?"":reqJson);
		sb.append(secretStr);
		return Md5Utils.generate(sb.toString());
	}
}
