package com.lfd.soa.srv.demo.support.queue.consumer.interceptor;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * 描述: 拦截{@link RabbitListener}监听方法的增强器
 *
 * @author linfengda
 * @create 2020-03-24 15:43
 */
public class RabbitListenerMethodPointcutAdvisor extends AbstractBeanFactoryPointcutAdvisor {
    /**
     * 注解静态切入点
     */
    private final RabbitListenerMethodPointcut pointcut = new RabbitListenerMethodPointcut();

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }
}
