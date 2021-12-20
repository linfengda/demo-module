package com.lfd.soa.demo.srv.queue.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 消息发送到交换机监听类
 * @author linfengda
 * @date 2020-10-13 00:53
 */
@Slf4j
@Component
public class SendConfirmCallback implements RabbitTemplate.ConfirmCallback {


    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("Success... 消息成功发送到交换机! correlationData:{}", correlationData);
        } else {
            log.info("Fail... 消息发送到交换机失败! correlationData:{}", correlationData);
        }
    }
}
