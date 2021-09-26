package com.lfd.soa.srv.demo.support.redis.lock.annotation;

import com.lfd.soa.srv.demo.support.redis.lock.config.BusinessLockConfigSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p> 开启业务锁注解 </p>
 *
 * @author linfengda
 * @copyright Copyright (C) 2021 PatPat, Inc. All rights reserved. <br>
 * @company 深圳盈富斯科技有限公司
 * @date 2021-09-18 17:04
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({BusinessLockConfigSelector.class})
public @interface EnableBusinessLock {
}
