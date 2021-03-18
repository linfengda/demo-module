package com.lfd.soa.srv.demo.support.queue.scanner;

import com.lfd.soa.srv.demo.support.queue.annotation.ConsumerService;
import com.lfd.soa.srv.demo.support.queue.annotation.EnableConsumer;
import com.lfd.soa.srv.demo.support.queue.bean.ConsumerProperty;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
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
import org.springframework.util.StringUtils;

/**
 * 扫描并初始化MQ消费服务
 *
 * @author linfengda
 * @date 2021-03-18 16:44
 */
public class ConsumerServiceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware, BeanFactoryAware {
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
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importMetadata.getAnnotationAttributes(EnableConsumer.class.getName(), false));
        if (attributes == null) {
            throw new IllegalArgumentException("@EnableConsumer is not present on importing class " + importMetadata.getClassName());
        }
        // 扫描MQ消费服务类
        ConsumerServiceScanner consumerServiceScanner = new ConsumerServiceScanner(registry,false);
        consumerServiceScanner.doScanner(attributes.getString("basePackage"));
        // 初始化MQ消费服务
        initConsumer();
    }

    private void initConsumer() {
        for (Class<?> consumerClass : ConsumerServiceClassMeta.getClazz()) {
            ConsumerService consumerService = consumerClass.getAnnotation(ConsumerService.class);
            if (null == consumerService) {
                return;
            }
            ConsumerProperty consumerProperty = initConsumerProperty(consumerService);
            CachingConnectionFactory cachingConnectionFactory = getConnectionFactory(consumerProperty.getHost(), consumerProperty.getPort(), consumerProperty.getUsername(), consumerProperty.getPassword(), consumerProperty.getVirtualHost());
            SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory = getSimpleRabbitListenerContainerFactory(cachingConnectionFactory);
            beanFactory.registerSingleton(consumerService.value() + "CachingConnectionFactory", cachingConnectionFactory);
            beanFactory.registerSingleton(consumerService.value() + "SimpleRabbitListenerContainerFactory", simpleRabbitListenerContainerFactory);
        }
    }

    private ConsumerProperty initConsumerProperty(ConsumerService consumerService) {
        return ConsumerProperty.builder()
                .name(resolve(consumerService.name()))
                .host(resolve(consumerService.host()))
                .port(Integer.valueOf(resolve(consumerService.port())))
                .username(resolve(consumerService.username()))
                .password(resolve(consumerService.password()))
                .virtualHost(resolve(consumerService.virtualHost()))
                .build();
    }

    private String resolve(String value) {
        if (StringUtils.hasText(value) && !(value.startsWith("#{") && value.contains("}"))) {
            return this.environment.resolvePlaceholders(value);
        }
        return value;
    }

    private CachingConnectionFactory getConnectionFactory(String host, Integer port, String username, String password, String virtualHost) {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost(host);
        cachingConnectionFactory.setPort(port);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setVirtualHost(virtualHost);
        cachingConnectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);
        return cachingConnectionFactory;
    }

    private SimpleRabbitListenerContainerFactory getSimpleRabbitListenerContainerFactory(CachingConnectionFactory cachingConnectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        factory.setPrefetchCount(10);
        factory.setAutoStartup(true);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }
}
