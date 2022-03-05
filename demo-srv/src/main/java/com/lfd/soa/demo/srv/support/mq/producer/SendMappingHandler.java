package com.lfd.soa.demo.srv.support.mq.producer;

import com.lfd.soa.common.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 消息发送handler
 *
 * @author linfengda
 * @date 2021-04-23 18:32
 */
@Slf4j
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendMappingHandler {
    /**
     * 消息转换器
     */
    private MessageConverter messageConverter;
    /**
     * 发送模版
     */
    private RabbitTemplate rabbitTemplate;
    /**
     * 队列名
     */
    private String queue;
    /**
     * 交换机名
     */
    private String exchange;
    /**
     * 路由key
     */
    private String routingKey;


    public void send(Object msgObj) {
        if (StringUtils.isEmpty(queue)) {
            doSend(exchange, routingKey, msgObj);
        }else {
            doSend(queue, msgObj);
        }
    }

    /**
     * 发送消息
     *
     * @param exchange 交换机
     * @param routeKey routeKey
     * @param msgObj   消息体
     */
    private void doSend(String exchange, String routeKey, Object msgObj) {
        log.info("发送消息：exchange：[{}]，routeKey：[{}]，msgType：[{}]，msg：[{}] ", exchange, routeKey, msgObj.getClass().getName(), JsonUtil.toJson(msgObj));
        // 构建MessageProperties
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentEncoding("UTF-8");
        messageProperties.setContentType("application/json");
        messageProperties.setMessageId(String.valueOf(new Date()));
        Message msg = messageConverter.toMessage(msgObj, messageProperties);
        rabbitTemplate.convertAndSend(exchange, routeKey, msg);
        log.info("发送消息成功：exchange：[{}]，routeKey：[{}]，msgType：[{}]，msg：[{}] ", exchange, routeKey, msgObj.getClass().getName(), JsonUtil.toJson(msgObj));
    }

    /**
     * 发送消息
     *
     * @param queue     队列
     * @param msgObj    消息体
     */
    public void doSend(String queue, Object msgObj) {
        log.info("发送消息：queue：[{}]，msgType：[{}]，msg：[{}] ", queue, msgObj.getClass().getName(), JsonUtil.toJson(msgObj));
        // 构建MessageProperties
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentEncoding("UTF-8");
        messageProperties.setContentType("application/json");
        messageProperties.setMessageId(String.valueOf(new Date()));
        Message msg = messageConverter.toMessage(msgObj, messageProperties);
        rabbitTemplate.convertAndSend(queue, msg);
        log.info("发送消息成功：queue：[{}]，msgType：[{}]，msg：[{}] ", queue, msgObj.getClass().getName(), JsonUtil.toJson(msgObj));
    }
}
