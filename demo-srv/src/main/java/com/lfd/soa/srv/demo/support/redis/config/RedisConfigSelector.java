package com.lfd.soa.srv.demo.support.redis.config;

import com.lfd.soa.srv.demo.support.redis.lock.config.BusinessLockConfig;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 开启redis
 * @author linfengda
 * @date 2020-07-26 22:36
 */
public class RedisConfigSelector implements ImportSelector {

    @Override
    public final String[] selectImports(AnnotationMetadata importMetadata) {
        return new String[]{RedisConfig.class.getName(), BusinessLockConfig.class.getName()};
    }
}

