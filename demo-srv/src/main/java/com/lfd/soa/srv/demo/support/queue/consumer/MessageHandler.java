package com.lfd.soa.srv.demo.support.queue.consumer;

import com.lfd.soa.common.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.BeanFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 消息处理类
 *
 * @author linfengda
 * @date 2021-03-25 23:22
 */
@Data
@AllArgsConstructor
public class MessageHandler {
    /**
     * 消息处理方法
     */
    private Method method;
    /**
     * beanFactory
     */
    private BeanFactory beanFactory;
    /**
     * clazz
     */
    private Class<?> clazz;


    /**
     * 消息重新消费
     *
     * @param message mq消息
     * @throws InvocationTargetException InvocationTargetException
     * @throws IllegalAccessException    IllegalAccessException
     * @return true：消费成功，false：消费失败
     */
    public boolean execute(String message) throws InvocationTargetException, IllegalAccessException {
        Class<?> messageType = method.getParameterTypes()[0];
        Object arg = deserializeMessage(message, messageType);
        return (boolean) method.invoke(beanFactory.getBean(clazz), arg);
    }

    private Object deserializeMessage(String message, Class<?> messageType) {
        if (Message.class.getName().equals(messageType.getName())) {
            return new Message(message.getBytes(), new MessageProperties());
        } else if (byte[].class.getName().equals(messageType.getName())) {
            return message.getBytes();
        } else if (String.class.getName().equals(messageType.getName())) {
            return message;
        } else {
            return JsonUtil.readValue(message, messageType);
        }
    }
}
