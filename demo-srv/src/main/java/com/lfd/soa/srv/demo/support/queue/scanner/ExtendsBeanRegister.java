package com.lfd.soa.srv.demo.support.queue.scanner;

import com.lfd.soa.srv.demo.support.queue.config.ConsumerAttributeHolder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 *
 * @author liugenhua
 * @date created in 2019/9/30 15:08
 */
public class ExtendsBeanRegister implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        ConsumerScanner consumerScanner = new ConsumerScanner(beanDefinitionRegistry,false);
        consumerScanner.scanAndRegisterBean(ConsumerAttributeHolder.INSTANCE.getAttributes().getString("basePackage"));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
