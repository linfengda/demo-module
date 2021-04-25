package com.lfd.soa.srv.demo.support.queue.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 发送消息到exchange
 *
 * @author linfengda
 * @date 2021-04-24 11:41
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExchangeMapping {
    /**
     * 交换机名
     */
    String exchange();
    /**
     * 交换机类型{@link org.springframework.amqp.core.ExchangeTypes}
     */
    String exchangeType();
    /**
     * 路由key
     */
    String routingKey();
    /**
     * 描述
     */
    String desc();
}
