package com.lfd.soa.srv.demo.support.redis.cache.config;

import com.lfd.soa.srv.demo.support.redis.annotation.EnableRedis;
import com.lfd.soa.srv.demo.support.redis.cache.annotation.EnableRedisCache;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.Annotation;

/**
 *
 * @author linfengda
 * @date 2020-09-13 23:29
 */
public abstract class AbstractRedisImportSelector <A extends Annotation> implements ImportSelector {
    protected AnnotationAttributes attributes;

    @Override
    public final String[] selectImports(AnnotationMetadata importMetadata) {
        this.attributes = AnnotationAttributes.fromMap(importMetadata.getAnnotationAttributes(EnableRedisCache.class.getName(), false));
        if (this.attributes == null) {
            throw new IllegalArgumentException("@EnableRedisCache is not present on importing class " + importMetadata.getClassName());
        }
        RedisAttributeHolder.INSTANCE.init(this.attributes);
        return selectImports();
    }

    protected abstract String[] selectImports();
}
