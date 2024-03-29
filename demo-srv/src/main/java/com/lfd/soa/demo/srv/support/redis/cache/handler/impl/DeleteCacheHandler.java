package com.lfd.soa.demo.srv.support.redis.cache.handler.impl;

import com.lfd.soa.demo.srv.support.redis.cache.resolver.CacheDataTypeResolver;
import com.lfd.soa.demo.srv.support.redis.cache.resolver.CacheDataTypeResolverHolder;
import com.lfd.soa.demo.srv.support.redis.cache.entity.dto.CacheParamDTO;
import com.lfd.soa.demo.srv.support.redis.cache.entity.dto.CacheTargetDTO;
import com.lfd.soa.demo.srv.support.redis.cache.entity.type.CacheAnnotationType;
import com.lfd.soa.demo.srv.support.redis.cache.handler.CacheHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 描述: 删除缓存注解处理 handler
 *
 * @author linfengda
 * @date 2020-07-26 11:00
 */
@AllArgsConstructor
@Slf4j
public class DeleteCacheHandler implements CacheHandler {

    @Override
    public boolean support(CacheAnnotationType annotationType) {
        return CacheAnnotationType.DELETE == annotationType;
    }

    @Override
    public Object doCache(CacheTargetDTO cacheTargetDTO) throws Throwable {
        for (CacheParamDTO deleteParam : cacheTargetDTO.getDeleteParams()) {
            log.debug("删除缓存注解处理，dataType={}", deleteParam.getDataType());
            CacheDataTypeResolver resolver = CacheDataTypeResolverHolder.INSTANCE.getResolver(deleteParam.getDataType());
            resolver.delCache(deleteParam);
        }
        MethodInvocation invocation = cacheTargetDTO.getInvocation();
        return invocation.proceed();
    }
}
