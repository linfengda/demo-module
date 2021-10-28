package com.lfd.soa.demo.srv.support.queue.annotation;

import com.lfd.soa.demo.srv.support.queue.bean.RabbitServiceProfile;
import com.lfd.soa.demo.srv.support.queue.bean.RabbitServiceType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示MQ服务 {@link RabbitServiceProfile}
 * @author linfengda
 * @date 2020-11-16 11:52
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RabbitService {
    /**
     * Rabbit MQ服务类型
     * @return  类型
     */
    RabbitServiceType type();
    /**
     * Rabbit MQ服务key
     * @return  key
     */
    String value();
    /**
     * Rabbit MQ服务名称
     * @return  名称
     */
    String name();
    /**
     * 消息转换器
     * @return  bean名称
     */
    String messageConverter() default "";
}
