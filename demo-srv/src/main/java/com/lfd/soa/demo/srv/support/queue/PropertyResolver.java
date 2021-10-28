package com.lfd.soa.demo.srv.support.queue;

import com.lfd.soa.demo.srv.support.queue.annotation.ExchangeMapping;
import com.lfd.soa.demo.srv.support.queue.annotation.QueueMapping;
import com.lfd.soa.demo.srv.support.queue.annotation.RabbitQueue;
import com.lfd.soa.demo.srv.support.queue.bean.RabbitQueueProperty;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

/**
 * @author linfengda
 * @date 2021-03-26 16:53
 */
@AllArgsConstructor
public class PropertyResolver {
    private Environment environment;

    public String resolveRabbitListener(RabbitListener rabbitListener) {
        String[] queues = rabbitListener.queues();
        if (0 != queues.length) {
            return resolve(queues[0]);
        }
        org.springframework.amqp.rabbit.annotation.Queue[] queuesToDeclare = rabbitListener.queuesToDeclare();
        if (0 != queuesToDeclare.length) {
            return resolve(queuesToDeclare[0].name());
        }
        QueueBinding[] bindings = rabbitListener.bindings();
        if (0 != bindings.length) {
            return resolve(bindings[0].value().name());
        }
        return "";
    }

    public RabbitQueueProperty resolveRabbitQueue(RabbitQueue rabbitQueue) {
        return RabbitQueueProperty.builder()
                .exchange(resolve(rabbitQueue.exchange()))
                .exchangeType(resolve(rabbitQueue.exchangeType()))
                .queue(resolve(rabbitQueue.queue()))
                .routingKey(resolve(rabbitQueue.routingKey()))
                .build();
    }

    public RabbitQueueProperty resolveQueueMapping(QueueMapping queueMapping) {
        return RabbitQueueProperty.builder()
                .exchange(resolve(queueMapping.exchange()))
                .exchangeType(resolve(queueMapping.exchangeType()))
                .queue(resolve(queueMapping.queue()))
                .routingKey(resolve(queueMapping.routingKey()))
                .build();
    }

    public RabbitQueueProperty resolveExchangeMapping(ExchangeMapping exchangeMapping) {
        return RabbitQueueProperty.builder()
                .exchange(resolve(exchangeMapping.exchange()))
                .exchangeType(resolve(exchangeMapping.exchangeType()))
                .routingKey(resolve(exchangeMapping.routingKey()))
                .build();
    }

    public String resolve(String value) {
        if (StringUtils.hasText(value) && !(value.startsWith("#{") && value.contains("}"))) {
            return this.environment.resolvePlaceholders(value);
        }
        return value;
    }
}
