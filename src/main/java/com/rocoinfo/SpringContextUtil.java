package com.rocoinfo;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <dl>
 * <dd>Description: 重SpringIOC中获取bean</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/4/9 13:31</dd>
 * <dd>@author：Kong</dd>
 * </dl>
 */
@Component
@Lazy(false)
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext context = null;

    @PostConstruct
    public void init() {}

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        context = applicationContext;
    }

    public synchronized static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    public synchronized static <T> T getBean(Class<T> requiredType) {
        return context.getBean(requiredType);
    }

}
