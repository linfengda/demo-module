package com.lfd.soa.srv.demo.support.queue.producer.proxy;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * 代理sender类
 * @author linfengda
 * @date 2021-01-15 11:52
 */
public class ProducerProxyFactoryBean<T> implements FactoryBean<T> {

    private Class<T> interfaceClass;

    public ProducerProxyFactoryBean(Class<T> interfaceClass){
        this.interfaceClass = interfaceClass;
    }

    @Override
    public T getObject() throws Exception {
        ClassLoader classLoader = interfaceClass.getClassLoader();
        Class<?>[] interfaces = new Class[]{interfaceClass};
        ProducerDynamicProxy proxy = new ProducerDynamicProxy();
        return (T) Proxy.newProxyInstance(classLoader, interfaces, proxy);
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
