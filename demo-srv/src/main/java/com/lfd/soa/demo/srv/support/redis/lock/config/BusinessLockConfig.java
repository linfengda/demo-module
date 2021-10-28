package com.lfd.soa.demo.srv.support.redis.lock.config;

import com.lfd.soa.demo.srv.support.redis.lock.interceptor.BusinessLockInterceptor;
import com.lfd.soa.demo.srv.support.redis.lock.interceptor.BusinessLockMethodPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;

/**
 * 配置业务锁注解增强器
 * @author linfengda
 * @date 2020-12-29 22:14
 */
public class BusinessLockConfig {

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public BusinessLockInterceptor businessLockInterceptor() {
        return new BusinessLockInterceptor();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public BusinessLockMethodPointcutAdvisor businessLockMethodPointcutAdvisor(BusinessLockInterceptor businessLockInterceptor) {
        BusinessLockMethodPointcutAdvisor businessLockMethodPointcutAdvisor = new BusinessLockMethodPointcutAdvisor();
        businessLockMethodPointcutAdvisor.setAdvice(businessLockInterceptor);
        return businessLockMethodPointcutAdvisor;
    }
}
