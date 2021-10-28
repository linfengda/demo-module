package com.lfd.soa.demo.srv.support.queue.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 发送消息注解
 *
 * @author linfengda
 * @date 2021-04-23 20:41
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface SendBody {
}
