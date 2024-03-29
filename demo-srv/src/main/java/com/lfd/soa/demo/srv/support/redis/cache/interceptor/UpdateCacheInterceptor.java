package com.lfd.soa.demo.srv.support.redis.cache.interceptor;

import com.lfd.soa.demo.srv.support.redis.cache.annotation.UpdateCache;
import com.lfd.soa.demo.srv.support.redis.cache.entity.type.CacheAnnotationType;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 拦截{@link UpdateCache}注解
 * @author linfengda
 * @date 2020-06-27 11:52
 */
@Slf4j
public class UpdateCacheInterceptor extends CacheMethodHandlerAdapter implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.debug("更新缓存注解拦截，{}，参数：{}", invocation.getMethod().getName(), invocation.getArguments());
        return super.invokeCacheMethod(invocation, CacheAnnotationType.UPDATE);
    }
}
