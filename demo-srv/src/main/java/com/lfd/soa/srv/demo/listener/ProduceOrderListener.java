package com.lfd.soa.srv.demo.listener;

import com.lfd.soa.srv.demo.support.queue.annotation.ConsumerService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 *
 * @author linfengda
 * @date 2021-01-13 15:23
 */
@Slf4j
@ConsumerService(value = "mes", name = "MES系统RabbitMQ", host = "${spring.rabbitmq.host}", port = "${spring.rabbitmq.port}", username = "${spring.rabbitmq.username}", password = "${spring.rabbitmq.password}", virtualHost = "${spring.rabbitmq.virtual-host}")
public class ProduceOrderListener {

    @RabbitListener(containerFactory = "mesSimpleRabbitListenerContainerFactory", queues = "order_mes")
    public void onMessage(Channel channel, Message message) throws IOException {
        try {
            byte[] body = message.getBody();
            String msg = new String(body);
            log.info("监听mq消息，msg：{}", msg);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("监听mq消息，异常信息： ", e);
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
}
