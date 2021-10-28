package com.lfd.soa.demo.srv.support.redis.cache.config;

import com.lfd.soa.demo.srv.support.redis.cache.handler.CacheHandlerHolder;
import com.lfd.soa.demo.srv.support.redis.cache.resolver.CacheDataTypeResolverHolder;
import com.lfd.soa.demo.srv.support.redis.GenericRedisTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 初始化缓存注解服务的依赖
 * @author linfengda
 * @date 2020-08-18 18:55
 */
public class RedisCacheAnnotationInitializer implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        GenericRedisTemplate genericRedisTemplate = applicationContext.getBean(GenericRedisTemplate.class);
        CacheHandlerHolder.INSTANCE.initHandlers();
        CacheDataTypeResolverHolder.INSTANCE.initResolver(genericRedisTemplate);
    }
}
