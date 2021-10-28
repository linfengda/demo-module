package com.lfd.soa.demo.srv.support.redis.cache.config;

import com.lfd.soa.demo.srv.support.redis.cache.annotation.EnableRedisCache;

/**
 * 开启redis缓存
 * @author linfengda
 * @date 2020-07-26 22:36
 */
public class RedisCacheConfigSelector extends AbstractRedisImportSelector<EnableRedisCache> {

    @Override
    protected String[] selectImports() {
        return new String[]{RedisCacheAnnotationConfig.class.getName()};
    }
}
