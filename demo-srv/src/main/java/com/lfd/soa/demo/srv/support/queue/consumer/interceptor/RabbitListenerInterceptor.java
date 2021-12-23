package com.lfd.soa.demo.srv.support.queue.consumer.interceptor;

import com.lfd.soa.common.util.JsonUtil;
import com.lfd.soa.demo.srv.support.queue.bean.RabbitQueueProperty;
import com.lfd.soa.demo.srv.support.queue.bean.RabbitServiceProperty;
import com.lfd.soa.demo.srv.support.queue.message.SysMqMessageTemplate;
import com.lfd.soa.demo.srv.support.queue.scanner.RabbitApplicationMeta;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 描述: {@link RabbitListener}监听方法拦截器
 *
 * @author linfengda
 * @create 2020-03-24 15:16
 */
@Slf4j
public class RabbitListenerInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Class<?> serviceClazz = invocation.getThis().getClass();
        Method method = invocation.getMethod();
        Object[] arguments = invocation.getArguments();
        log.debug("拦截@RabbitListener监听方法：[{}]，[{}]，[{}]", serviceClazz.getName(), method.getName(), arguments);
        String service = RabbitApplicationMeta.getClassService(serviceClazz);
        if (null == service) {
            invocation.proceed();
            return true;
        }
        RabbitServiceProperty rabbitServiceProperty = RabbitApplicationMeta.getRabbitService(service);
        if (null == rabbitServiceProperty) {
            invocation.proceed();
            return true;
        }
        List<RabbitQueueProperty> queues = rabbitServiceProperty.getListenerQueues();
        if (CollectionUtils.isEmpty(queues)) {
            invocation.proceed();
            return true;
        }
        Map<Method, RabbitQueueProperty> methodQueueMap = queues.stream().collect(Collectors.toMap(RabbitQueueProperty::getMethod, a -> a, (k1, k2) -> k1));
        RabbitQueueProperty rabbitQueueProperty = methodQueueMap.get(method);
        String queue = rabbitQueueProperty.getQueue();
        String message = serializeMessage(arguments[0]);
        HashMap msgMap = Optional.ofNullable(JsonUtil.readValue(message, HashMap.class)).orElse(new HashMap(0));
        try {
            invocation.proceed();
            SysMqMessageTemplate.consumeMessageSuccess(msgMap);
            log.info("mq消费成功，[service={}]，[queue={}]，[method={}]，[message={}]", service, queue, method.getName(), message);
            return true;
        } catch (Exception e) {
            log.error("mq消费失败", e);
            SysMqMessageTemplate.consumeMessageFail(service, queue, msgMap, e.getMessage());
            log.error("mq消费失败，[service={}]，[queue={}]，[method={}]，[message={}]，[error={}]", service, queue, method.getName(), message, e.getMessage());
            return false;
        }
    }

    private String serializeMessage(Object msg) {
        try {
            String msgType = msg.getClass().getName();
            if (Message.class.getName().equals(msgType)) {
                return new String(((Message) msg).getBody());
            } else if (byte[].class.getName().equals(msgType)) {
                return new String((byte[]) msg);
            } else if (String.class.getName().equals(msgType)) {
                return (String) msg;
            } else {
                return JsonUtil.toJson(msg);
            }
        } catch (Exception e) {
            return "";
        }
    }
}