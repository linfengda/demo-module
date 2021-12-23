package com.lfd.soa.demo.srv.support.mq.producer.proxy;

import com.lfd.soa.common.exception.BusinessException;
import com.lfd.soa.demo.srv.support.mq.producer.SendMappingHandlerContainer;
import com.lfd.soa.demo.srv.support.mq.annotation.SendBody;
import com.lfd.soa.demo.srv.support.mq.producer.SendMappingHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * sender类方法动态代理
 *
 * @author linfengda
 * @date 2021-01-15 11:54
 */
@Slf4j
public class ProducerDynamicProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        SendMappingHandler sendMappingHandler = SendMappingHandlerContainer.getSendMethodHandler(method);
        if (null == sendMappingHandler) {
            throw new BusinessException("生产者的发送方法没有@SendMapping注解");
        }
        Parameter[] parameters = method.getParameters();
        if (0 == parameters.length) {
            throw new BusinessException("生产者的发送方法没有@SendBody注解");
        }
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            SendBody sendBody = parameter.getAnnotation(SendBody.class);
            if (null != sendBody) {
                sendMappingHandler.send(args[i]);
                return true;
            }
        }
        throw new BusinessException("生产者的发送方法没有@SendBody注解");
    }
}
