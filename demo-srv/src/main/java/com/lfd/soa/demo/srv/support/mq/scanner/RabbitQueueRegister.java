package com.lfd.soa.demo.srv.support.mq.scanner;

import com.lfd.soa.demo.srv.support.mq.PropertyResolver;
import com.lfd.soa.demo.srv.support.mq.annotation.ExchangeMapping;
import com.lfd.soa.demo.srv.support.mq.annotation.QueueMapping;
import com.lfd.soa.demo.srv.support.mq.annotation.RabbitQueue;
import com.lfd.soa.demo.srv.support.mq.annotation.RabbitService;
import com.lfd.soa.demo.srv.support.mq.bean.RabbitQueueProperty;
import com.lfd.soa.demo.srv.support.mq.bean.RabbitServiceType;
import com.lfd.soa.demo.srv.support.mq.consumer.MessageHandler;
import com.lfd.soa.demo.srv.support.mq.consumer.MessageHandlerContainer;
import com.lfd.soa.demo.srv.support.mq.producer.SendMappingHandler;
import com.lfd.soa.demo.srv.support.mq.producer.SendMappingHandlerContainer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.lang.reflect.Method;

/**
 * Rabbit队列交换机初始化
 *
 * @author linfengda
 * @date 2021-03-25 14:34
 */
@Slf4j
@AllArgsConstructor
public class RabbitQueueRegister {
    private PropertyResolver propertyResolver;
    private DefaultListableBeanFactory beanFactory;

    public void initQueue() {
        for (Class<?> serviceClazz : RabbitApplicationMeta.getRabbitServiceClazz()) {
            try {
                RabbitService rabbitService = serviceClazz.getAnnotation(RabbitService.class);
                if (null == rabbitService) {
                    continue;
                }
                declareQueue(serviceClazz, rabbitService);
                initSendMapping(serviceClazz, rabbitService);
                if (RabbitServiceType.CONSUMER == rabbitService.type()) {
                    bindQueueListenerToMessageHandler(serviceClazz, rabbitService);
                }
            } catch (Exception e) {
                log.error("初始化rabbitQueue异常", e);
            }
        }
    }

    private void declareQueue(Class<?> serviceClazz, RabbitService rabbitService) {
        String service = rabbitService.value();
        for (Method method : serviceClazz.getDeclaredMethods()) {
            RabbitQueue rabbitQueue = method.getAnnotation(RabbitQueue.class);
            if (null != rabbitQueue) {
                declareQueue(service, propertyResolver.resolveRabbitQueue(rabbitQueue));
            }
        }
    }

    private void initSendMapping(Class<?> serviceClazz, RabbitService rabbitService) {
        String service = rabbitService.value();
        for (Method method : serviceClazz.getDeclaredMethods()) {
            QueueMapping queueMapping = method.getAnnotation(QueueMapping.class);
            if (null != queueMapping) {
                RabbitQueueProperty rabbitQueueProperty = propertyResolver.resolveQueueMapping(queueMapping);
                SendMappingHandlerContainer.buildSendMethodHandler(method, SendMappingHandler.builder()
                        .messageConverter(RabbitApplicationMeta.getRabbitService(service).getMessageConverter())
                        .rabbitTemplate(RabbitApplicationMeta.getRabbitService(service).getRabbitTemplate())
                        .queue(rabbitQueueProperty.getQueue())
                        .exchange(rabbitQueueProperty.getExchange())
                        .routingKey(rabbitQueueProperty.getRoutingKey())
                        .build());
            }
            ExchangeMapping exchangeMapping = method.getAnnotation(ExchangeMapping.class);
            if (null != exchangeMapping) {
                RabbitQueueProperty rabbitQueueProperty = propertyResolver.resolveExchangeMapping(exchangeMapping);
                SendMappingHandlerContainer.buildSendMethodHandler(method, SendMappingHandler.builder()
                        .messageConverter(RabbitApplicationMeta.getRabbitService(service).getMessageConverter())
                        .rabbitTemplate(RabbitApplicationMeta.getRabbitService(service).getRabbitTemplate())
                        .exchange(rabbitQueueProperty.getExchange())
                        .routingKey(rabbitQueueProperty.getRoutingKey())
                        .build());
            }
        }
    }

    private void declareQueue(String service, RabbitQueueProperty rabbitQueueProperty) {
        // 创建交换机和队列
        Exchange exchange = new ExchangeBuilder(rabbitQueueProperty.getExchange(), rabbitQueueProperty.getExchangeType()).ignoreDeclarationExceptions().build();
        Queue queue = new Queue(rabbitQueueProperty.getQueue(), true, false, false);
        queue.setIgnoreDeclarationExceptions(true);
        Binding binding = new Binding(rabbitQueueProperty.getQueue(), Binding.DestinationType.QUEUE, rabbitQueueProperty.getExchange(), rabbitQueueProperty.getRoutingKey(), null);
        binding.setIgnoreDeclarationExceptions(true);
        RabbitAdmin rabbitAdmin = (RabbitAdmin) beanFactory.getBean(service + "RabbitAdmin");
        rabbitAdmin.declareExchange(exchange);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(binding);
    }

    private void bindQueueListenerToMessageHandler(Class<?> serviceClazz, RabbitService rabbitService) {
        String service = rabbitService.value();
        for (Method method : serviceClazz.getDeclaredMethods()) {
            RabbitListener rabbitListener = method.getAnnotation(RabbitListener.class);
            if (null == rabbitListener) {
                continue;
            }
            String queue = propertyResolver.resolveRabbitListener(rabbitListener);
            RabbitQueueProperty rabbitQueueProperty = RabbitQueueProperty.builder()
                    .method(method)
                    .queue(queue)
                    .build();
            RabbitApplicationMeta.getRabbitService(service).getListenerQueues().add(rabbitQueueProperty);
            MessageHandlerContainer.buildMessageHandler(service, queue, new MessageHandler(method, beanFactory, serviceClazz));
        }
    }
}
