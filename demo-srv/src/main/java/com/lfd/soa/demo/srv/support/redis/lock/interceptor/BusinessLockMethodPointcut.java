package com.lfd.soa.demo.srv.support.redis.lock.interceptor;

import com.lfd.soa.demo.srv.support.redis.lock.annotation.BusinessLock;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

/**
 * 描述: 匹配{@link BusinessLock}的静态切入点
 *
 * @author linfengda
 * @create 2020-03-24 15:59
 */
public class BusinessLockMethodPointcut extends StaticMethodMatcherPointcut {

    @Override
    public boolean matches(Method method, Class<?> clazz) {
        return null != AnnotationUtils.findAnnotation(method, BusinessLock.class);
    }
}
