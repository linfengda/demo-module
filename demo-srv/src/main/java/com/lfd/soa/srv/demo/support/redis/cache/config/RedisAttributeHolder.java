package com.lfd.soa.srv.demo.support.redis.cache.config;

import lombok.Getter;
import org.springframework.core.annotation.AnnotationAttributes;

/**
 * 注解缓存元数据
 * @author linfengda
 * @date 2020-09-15 11:59
 */
@Getter
public enum RedisAttributeHolder {
    /**
     * 单例
     */
    INSTANCE;
    private AnnotationAttributes attributes = null;

    public void init(AnnotationAttributes att) {
        attributes = att;
    }
}
