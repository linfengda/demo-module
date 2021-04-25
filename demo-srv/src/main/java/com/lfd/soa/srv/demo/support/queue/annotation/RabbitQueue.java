package com.lfd.soa.srv.demo.support.queue.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示RabbitMQ队列
 * 
 * @author linfengda
 * @date 2021-03-25 17:41
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RabbitQueue {
    /**
     * 队列名
     */
    String queue();
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
}
