package com.lfd.soa.demo.srv.support.redis.cache.annotation;

import com.lfd.soa.demo.srv.support.redis.cache.config.RedisCacheConfigSelector;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.lang.annotation.*;

/**
 * <p> 开启redis缓存 </p>
 *
 * @author linfengda
 * @copyright Copyright (C) 2021 PatPat, Inc. All rights reserved. <br>
 * @company 深圳盈富斯科技有限公司
 * @date 2021-09-09 14:31
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RedisCacheConfigSelector.class})
public @interface EnableRedisCache {
    /**
     * 查询注解aop优先级
     * @return
     */
    int queryOrder() default Ordered.LOWEST_PRECEDENCE-3;
    /**
     * 删除注解aop优先级
     * @return
     */
    int deleteOrder() default Ordered.LOWEST_PRECEDENCE-2;
    /**
     * 更新注解aop优先级
     * @return
     */
    int updateOrder() default Ordered.LOWEST_PRECEDENCE;
}
