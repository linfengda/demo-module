package com.lfd.soa.srv.demo.support.queue.consumer.interceptor;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;

/**
 * 配置监听方法增强器
 * @author linfengda
 * @date 2020-12-29 22:14
 */
public class RabbitListenerAnnotationConfig {

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public RabbitListenerInterceptor rabbitListenerInterceptor() {
        return new RabbitListenerInterceptor();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public RabbitListenerMethodPointcutAdvisor rabbitListenerMethodPointcutAdvisor(RabbitListenerInterceptor rabbitListenerInterceptor) {
        RabbitListenerMethodPointcutAdvisor rabbitListenerMethodPointcutAdvisor = new RabbitListenerMethodPointcutAdvisor();
        rabbitListenerMethodPointcutAdvisor.setAdvice(rabbitListenerInterceptor);
        return rabbitListenerMethodPointcutAdvisor;
    }
}
