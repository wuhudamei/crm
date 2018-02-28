package com.rocoinfo.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.net.ssl.*;
import javax.servlet.ServletContext;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;


/**
 * 在ServletContext中设置全局变量。可以在JSP中用EL获取。例如:${ctx}
 *
 * @author liuwei
 */
@Component
@Lazy(false)
public class PropertyHolder implements ServletContextAware, ApplicationContextAware {

    public static ApplicationContext appCtx;
    private static ServletContext servletContext;
    private static String baseurl;
    /*保存外围系统调用本系统open接口的callId和secret*/
    public static Map<String, String> callCertificateMap; 
    
    /** 微信授权中心APPID **/
    private static String wechatAppid;
    /** 美得你综管平台url **/
    private static String oaBaseUrl;
    /** 认证中心URL**/
    private static String oauthCenterUrl;
    /** 任务中心appid **/
    private static String oauthCenterAppid;
    /** 任务中心secret **/
    private static String oauthCenterSecret;
    
    /** 对接订单流转系统的CRMAPI的域名**/
    private static String crmApiUrl;
    
    /** 存放多套crmapi的url的json串(暂时部署多套crmapi应对多套订单系统) **/
    private static String crmApiUrlArrJson;

    /** 对接订单流转系统CRMAPI签名的key**/
    private static String secretKey;

    /** 对接新版CRMAPI签名的key**/
    private static String newApiSecretKey;
    
    /** 默认的重复任务的生成CD时间(秒)**/
    private static int repeatCDSec;

    /** 小美返单**/
    private static int xmfdRepeatCdSec;
    
    /** 任务派发间隔(秒)**/
    private static int distributeSeconds;

    /**从订单系统同步订单状态的时间段*/
    private static int synchronousInterval;
    /**每次同步订单状态的订单数量*/
    private static int batchNum;

    /**redis中存放的最近的客户电话号码的数量*/
    private static int recentCusPhoneNuMaxAmount;

    /** 认证中心 获取token url **/
    private static String oAuthAppTokenUrl;
    /** 认证中心 同步用户的 url **/
    private static String oAuthAppUserUrl;


    static {
        disableSslVerification();
    }

    public static ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletContext(ServletContext ctx) {
        PropertyHolder.servletContext = ctx;
        ctx.setAttribute("ctx", ctx.getContextPath());
    }

    public static String getBaseUrl() {
        return PropertyHolder.baseurl;
    }
    
    @Value("${base.url}")
    public void setBaseUrl(String baseurl) {
        PropertyHolder.baseurl = baseurl;
    }
    
    /** 获取 综管平台url **/
    public static String getOABaseUrl() {
        return PropertyHolder.oaBaseUrl;
    }
    /** 设置 综管平台url **/
    @Value("${oa.base.url}")
    public void setOABaseUrl(String oaBaseUrl) {
        PropertyHolder.oaBaseUrl = oaBaseUrl;
    }
    
    /** 获取 认证中心url **/
    public static String getOauthCenterUrl() {
        return PropertyHolder.oauthCenterUrl;
    }
    
    /** 设置 认证中心url **/
    @Value("${oauth.center.url}")
    public void setOauthCenterUrl(String oauthCenterUrl) {
        PropertyHolder.oauthCenterUrl = oauthCenterUrl;
    }

    /** 获取 微信授权中心appid **/
    public static String getWechatAppid() {
        return PropertyHolder.wechatAppid;
    }
    
    /** 设置 微信授权中心appid **/
    @Value("${wechat.appid}")
    public void setWechatAppid(String wechatAppid) {
        PropertyHolder.wechatAppid = wechatAppid;
    }
    
    /** 获取 认证中心appid **/
    public static String getOauthCenterAppid() {
        return PropertyHolder.oauthCenterAppid;
    }
    
    /** 设置 认证中心appid **/
    @Value("${oauth.center.appid}")
    public void setOauthCenterAppid(String oauthCenterAppid) {
        PropertyHolder.oauthCenterAppid = oauthCenterAppid;
    }
    
    /** 获取 认证中心secret **/
    public static String getOauthCenterSecret() {
        return PropertyHolder.oauthCenterSecret;
    }
    
    /** 设置 认证中心secret **/
    @Value("${oauth.center.secret}")
    public void setOauthCenterSecret(String oauthCenterSecret) {
        PropertyHolder.oauthCenterSecret = oauthCenterSecret;
    }    

    
    /** 获取 对接订单流转系统的CRMAPI的域名 **/
	public static String getCrmApiUrl() {
		return PropertyHolder.crmApiUrl;
	}
	
	/** 设置 对接订单流转系统的CRMAPI的域名 **/
	@Value("${crmapi.hostname}")
	public void setCrmApiUrl(String crmApiUrl) {
		PropertyHolder.crmApiUrl = crmApiUrl;
	}


    /** 获取 存放多套crmapi的url的json串 **/
	public static String getCrmApiUrlArrJson() {
		return PropertyHolder.crmApiUrlArrJson;
	}
	
	/** 设置 存放多套crmapi的url的json串 **/
	@Value("${json.crmapi.hostarr}")
	public void setCrmApiUrlArrJson(String jsonStr) {
		PropertyHolder.crmApiUrlArrJson = jsonStr;
	}
	
	

    /** 获取对接订单流转系统CRMAPI签名的key **/
    public static String getSecretKey() {
        return PropertyHolder.secretKey;
    }
    /** 设置对接订单流转系统CRMAPI签名的key **/
    @Value("${crmapi.secretKey}")
    public  void setSecretKey(String secretKey) {
        PropertyHolder.secretKey = secretKey;
    }


    public static String getNewApiSecretKey() {
        return newApiSecretKey;
    }
    @Value("${newcrmapi.secretKey}")
    public void setNewApiSecretKey(String newApiSecretKey) {
        PropertyHolder.newApiSecretKey = newApiSecretKey;
    }

    public static int getRepeatCDSec() {
		return PropertyHolder.repeatCDSec;
	}
	@Value("${task.repeatCDSec}")
	public void setRepeatCDSec(int repeatCDSec) {
		PropertyHolder.repeatCDSec = repeatCDSec;
	}


    public static int getXmfdRepeatCdSec() {
        return PropertyHolder.xmfdRepeatCdSec;
    }
    @Value("${task.xmfdRepeatCdSec}")
    public void setXmfdRepeatCdSec(int xmfdRepeatCdSec) {
        PropertyHolder.xmfdRepeatCdSec = xmfdRepeatCdSec;
    }

    /** 获取  任务派发间隔(秒) **/
    public static int getDistributeSeconds() {
        return PropertyHolder.distributeSeconds;
    }
    
    /** 设置 任务派发间隔(秒)**/
    @Value("${task.distributeSeconds}")
    public void setDistributeSeconds(int distributeSeconds) {
        PropertyHolder.distributeSeconds = distributeSeconds;
    }   

    /**获取 从订单系统同步订单状态的时间段*/
    public static int getSynchronousInterval() {
        return PropertyHolder.synchronousInterval;
    }

    @Value("${crmapi.order.synchronous.interval}")
    public void setSynchronousInterval(int synchronousInterval) {
        PropertyHolder.synchronousInterval = synchronousInterval;
    }

    public static int getBatchNum() {
        return PropertyHolder.batchNum;
    }

    @Value("${crmapi.order.synchronous.batchnum}")
    public void setBatchNum(int batchNum) {
        PropertyHolder.batchNum = batchNum;
    }


    public static int getRecentCusPhoneNuMaxAmount() {
        return recentCusPhoneNuMaxAmount;
    }
    @Value("${redis.recentphone.amount.max}")
    public void setRecentCusPhoneNuMaxAmount(int recentCusPhoneNuMaxAmount) {
        PropertyHolder.recentCusPhoneNuMaxAmount = recentCusPhoneNuMaxAmount;
    }

    public static String getoAuthAppTokenUrl() {
        return oAuthAppTokenUrl;
    }
    /**获取 认证中心token url*/
    @Value("${oauth.appToken.url}")
    public void setoAuthAppTokenUrl(String oAuthAppTokenUrl) {
        PropertyHolder.oAuthAppTokenUrl = oAuthAppTokenUrl;
    }

    public static String getoAuthAppUserUrl() {
        return oAuthAppUserUrl;
    }
    /**获取 认证中心 获取用户 url*/
    @Value("${oauth.appUser.url}")
    public void setoAuthAppUserUrl(String oAuthAppUserUrl) {
        PropertyHolder.oAuthAppUserUrl = oAuthAppUserUrl;
    }



    private static void disableSslVerification() {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appCtx = applicationContext;
    }
}