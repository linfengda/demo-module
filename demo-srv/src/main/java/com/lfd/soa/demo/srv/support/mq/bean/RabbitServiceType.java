package com.lfd.soa.demo.srv.support.mq.bean;

/**
 * RabbitMQ服务类型
 *
 * @author linfengda
 * @date 2021-03-25 22:36
 */
public enum RabbitServiceType {
    /**
     * 生产者
     */
    PRODUCER,
    /**
     * 消费者
     */
    CONSUMER;
}
