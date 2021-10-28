package com.lfd.soa.demo.srv.support.queue.scanner;

import com.lfd.soa.demo.srv.support.queue.annotation.EnableAutoRabbit;
import com.lfd.soa.demo.srv.support.queue.PropertyResolver;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 扫描并初始化RabbitMQ服务
 *
 * @author linfengda
 * @date 2021-03-18 16:44
 */
public class RabbitApplicationRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware, BeanFactoryAware {
    private Environment environment;
    private DefaultListableBeanFactory beanFactory;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importMetadata.getAnnotationAttributes(EnableAutoRabbit.class.getName(), false));
        if (attributes == null) {
            throw new IllegalArgumentException("@EnableAutoRabbit is not present on importing class " + importMetadata.getClassName());
        }
        String[] basePackage = attributes.getStringArray("basePackage");
        // 扫描MQ服务类
        RabbitServiceScanner rabbitServiceScanner = new RabbitServiceScanner(registry,false);
        rabbitServiceScanner.doScanner(basePackage);
        // 初始化MQ服务
        RabbitServiceRegister rabbitServiceRegister = new RabbitServiceRegister(new PropertyResolver(environment), beanFactory);
        rabbitServiceRegister.initService();
        // 初始化MQ队列
        RabbitQueueRegister rabbitQueueRegister = new RabbitQueueRegister(new PropertyResolver(environment), beanFactory);
        rabbitQueueRegister.initQueue();
    }
}
