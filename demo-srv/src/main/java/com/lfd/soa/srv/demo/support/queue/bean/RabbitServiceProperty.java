package com.lfd.soa.srv.demo.support.queue.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;

import java.util.List;

/**
 * MQ服务信息
 *
 * @author linfengda
 * @date 2021-03-18 17:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RabbitServiceProperty {
    /**
     * connectionFactory
     */
    private CachingConnectionFactory cachingConnectionFactory;
    /**
     * rabbitTemplate
     */
    private RabbitTemplate rabbitTemplate;
    /**
     * simpleRabbitListenerContainerFactory
     */
    private SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory;
    /**
     * rabbitAdmin
     */
    private RabbitAdmin rabbitAdmin;
    /**
     * 消息转换器
     */
    private MessageConverter messageConverter;
    /**
     * 监听服务
     */
    private String service;
    /**
     * 监听服务名称
     */
    private String name;
    /**
     * 地址
     */
    private String host;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * virtualHost
     */
    private String virtualHost;
    /**
     * 监听队列
     */
    private List<RabbitQueueProperty> listenerQueues;
}
