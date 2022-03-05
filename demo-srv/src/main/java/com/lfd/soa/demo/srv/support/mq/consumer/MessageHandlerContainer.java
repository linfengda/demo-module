package com.lfd.soa.demo.srv.support.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息处理类缓存
 *
 * @author linfengda
 * @date 2021-03-26 14:13
 */
@Slf4j
public class MessageHandlerContainer {
    /**
     * 消息处理类缓存
     */
    private static final Map<String, Map<String, List<MessageHandler>>> MESSAGE_HANDLER_MAP = new HashMap<>(32);

    public static void buildMessageHandler(String service, String queue,  MessageHandler messageHandler) {
        if (MESSAGE_HANDLER_MAP.containsKey(service)) {
            Map<String, List<MessageHandler>> queueMap = MESSAGE_HANDLER_MAP.get(service);
            if (queueMap.containsKey(queue)) {
                queueMap.get(queue).add(messageHandler);
            }else {
                queueMap.put(queue, Arrays.asList(messageHandler));
            }
        } else {
            Map<String, List<MessageHandler>> queueMap = new HashMap<>(8);
            queueMap.put(queue, Arrays.asList(messageHandler));
            MESSAGE_HANDLER_MAP.put(service, queueMap);
        }
    }

    public static List<MessageHandler> getMessageHandler(String service, String queue) {
        if (!MESSAGE_HANDLER_MAP.containsKey(service)) {
            return null;
        }
        return MESSAGE_HANDLER_MAP.get(service).get(queue);
    }

    public static void handle(String service, String queue, String message) {
        try {
            List<MessageHandler> messageHandlers = getMessageHandler(service, queue);
            if (CollectionUtils.isEmpty(messageHandlers)) {
                log.error("mq消息重新消费，没有找到监听方法，[service={}]，[queue={}]", service, queue);
                return;
            }
            for (MessageHandler messageHandler : messageHandlers) {
                if (messageHandler.execute(message)) {
                    return;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            log.info("mq消息重新消费失败，[service={}]，[queue={}]，[message={}]", service, queue, message);
        }
    }
}
