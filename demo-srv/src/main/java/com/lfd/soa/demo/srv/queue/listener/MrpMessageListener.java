package com.lfd.soa.demo.srv.queue.listener;

import com.lfd.soa.demo.srv.support.mq.annotation.RabbitService;
import com.lfd.soa.demo.srv.support.mq.bean.RabbitServiceType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.io.IOException;

/**
 * @author linfengda
 * @date 2021-01-13 15:23
 */
@Slf4j
@RabbitService(type = RabbitServiceType.CONSUMER, value = "mrp", name = "mrp系统RabbitMQ")
public class MrpMessageListener {

    @RabbitListener(containerFactory = "mrpSimpleRabbitListenerContainerFactory", queues = "demo_queue")
    public void onMessage(Message message) throws IOException {
        byte[] body = message.getBody();
        String msg = new String(body);
        log.info("监听mq消息，msg：{}", msg);
    }
}
