package com.lfd.soa.srv.demo.support.redis.cache.handler;

import com.lfd.soa.common.exception.BusinessException;
import com.lfd.soa.srv.demo.support.redis.cache.entity.type.CacheAnnotationType;
import com.lfd.soa.srv.demo.support.redis.cache.handler.impl.DeleteCacheHandler;
import com.lfd.soa.srv.demo.support.redis.cache.handler.impl.QueryCacheHandler;
import com.lfd.soa.srv.demo.support.redis.cache.handler.impl.UpdateCacheHandler;
import com.lfd.soa.srv.demo.support.redis.lock.RedisDistributedLock;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 初始化全部handler
 *
 * @author linfengda
 * @date 2020-07-26 16:24
 */
public enum CacheHandlerHolder {
    /**
     * 单例
     */
    INSTANCE;
    private final List<CacheHandler> handlers = new ArrayList<>();

    /**
     * 初始化全部handler
     * @param redisDistributedLock
     */
    public void initHandlers(RedisDistributedLock redisDistributedLock) {
        handlers.add(new QueryCacheHandler(redisDistributedLock));
        handlers.add(new DeleteCacheHandler());
        handlers.add(new UpdateCacheHandler());
    }

    /**
     * 根据注解类型获取handler
     * @param annotationType    注解类型
     * @return                  handler
     */
    public CacheHandler getHandler(CacheAnnotationType annotationType) {
        for (CacheHandler handler : handlers) {
            if (handler.support(annotationType)) {
                return handler;
            }
        }
        throw new BusinessException("不支持的缓存注解类型！");
    }
}
