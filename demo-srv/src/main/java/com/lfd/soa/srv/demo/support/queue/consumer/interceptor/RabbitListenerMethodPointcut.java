package com.lfd.soa.srv.demo.support.queue.consumer.interceptor;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

/**
 * 描述: 匹配{@link RabbitListener}的静态切入点
 *
 * @author linfengda
 * @create 2020-03-24 15:59
 */
public class RabbitListenerMethodPointcut extends StaticMethodMatcherPointcut {

    @Override
    public boolean matches(Method method, Class<?> clazz) {
        return null != AnnotationUtils.findAnnotation(method, RabbitListener.class);
    }
}
