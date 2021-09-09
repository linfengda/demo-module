package com.lfd.soa.srv.demo.support.redis.annotation;

import com.lfd.soa.srv.demo.support.redis.config.RedisConfigSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启redis，自动引入redisTemplate，redisDistributedLock
 * @author linfengda
 * @date 2020-07-26 22:34
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RedisConfigSelector.class})
public @interface EnableRedis {
}
