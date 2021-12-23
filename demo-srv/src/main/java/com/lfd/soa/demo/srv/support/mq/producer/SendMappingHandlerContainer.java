package com.lfd.soa.demo.srv.support.mq.producer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息发送处理
 *
 * @author linfengda
 * @date 2021-04-23 18:33
 */
public class SendMappingHandlerContainer {
    /**
     * 消息发送方法缓存
     */
    private static final Map<Method, SendMappingHandler> SEND_MAPPING_HANDLER_MAP = new HashMap<>(32);

    public static void buildSendMethodHandler(Method method, SendMappingHandler sendMappingHandler) {
        SEND_MAPPING_HANDLER_MAP.put(method, sendMappingHandler);
    }

    public static SendMappingHandler getSendMethodHandler(Method method) {
        return SEND_MAPPING_HANDLER_MAP.get(method);
    }
}
