package com.lfd.soa.demo.srv.support.redis.cache.handler.impl;

import com.lfd.soa.demo.srv.support.redis.cache.resolver.CacheDataTypeResolver;
import com.lfd.soa.demo.srv.support.redis.cache.resolver.CacheDataTypeResolverHolder;
import com.lfd.soa.demo.srv.support.redis.cache.entity.dto.CacheTargetDTO;
import com.lfd.soa.demo.srv.support.redis.cache.entity.type.CacheAnnotationType;
import com.lfd.soa.demo.srv.support.redis.cache.handler.CacheHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 描述: 更新缓存注解处理 handler
 *
 * @author linfengda
 * @date 2020-07-26 11:00
 */
@AllArgsConstructor
@Slf4j
public class UpdateCacheHandler implements CacheHandler {

    @Override
    public boolean support(CacheAnnotationType annotationType) {
        return CacheAnnotationType.UPDATE == annotationType;
    }

    @Override
    public Object doCache(CacheTargetDTO cacheTargetDTO) throws Throwable {
        log.debug("更新缓存注解处理，dataType={}", cacheTargetDTO.getParam().getDataType());
        MethodInvocation invocation = cacheTargetDTO.getInvocation();
        Object value = invocation.proceed();
        CacheDataTypeResolver resolver = CacheDataTypeResolverHolder.INSTANCE.getResolver(cacheTargetDTO.getParam().getDataType());
        if (resolver.hasKey(cacheTargetDTO.getParam())) {
            resolver.setCache(cacheTargetDTO.getParam(), value);
        }
        return value;
    }
}
