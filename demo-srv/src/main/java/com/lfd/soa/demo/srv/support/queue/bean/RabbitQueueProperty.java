package com.lfd.soa.demo.srv.support.queue.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;

/**
 * MQ服务队列信息
 *
 * @author linfengda
 * @date 2021-03-25 14:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RabbitQueueProperty {
    /**
     * 方法
     */
    private Method method;
    /**
     * 队列名
     */
    private String queue;
    /**
     * 交换机名
     */
    private String exchange;
    /**
     * 交换机类型{@link org.springframework.amqp.core.ExchangeTypes}
     */
    private String exchangeType;
    /**
     * 路由key
     */
    private String routingKey;
}
