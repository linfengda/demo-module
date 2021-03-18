package com.lfd.soa.srv.demo.support.queue.proxy;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.cglib.proxy.Enhancer;

/**
 * 代理类型
 * @author linfengda
 * @date 2021-01-15 11:52
 */
public class ConsumerProxyFactoryBean<T> implements FactoryBean<T> {

    private Class<T> interfaceClass;

    public ConsumerProxyFactoryBean(Class<T> interfaceClass){
        this.interfaceClass = interfaceClass;
    }

    @Override
    public T getObject() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.interfaceClass);
        enhancer.setCallback(new ConsumerDynamicProxy());
        return (T) enhancer.create();
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
