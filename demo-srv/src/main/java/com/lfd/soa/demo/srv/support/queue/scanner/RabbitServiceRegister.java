package com.lfd.soa.demo.srv.support.queue.scanner;

import com.lfd.soa.demo.srv.support.queue.annotation.RabbitService;
import com.lfd.soa.demo.srv.support.queue.bean.RabbitServiceProfile;
import com.lfd.soa.demo.srv.support.queue.PropertyResolver;
import com.lfd.soa.demo.srv.support.queue.bean.RabbitServiceProperty;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

/**
 * Rabbit服务初始化
 *
 * @author linfengda
 * @date 2021-03-25 08:40
 */
@Slf4j
@AllArgsConstructor
public class RabbitServiceRegister {
    private PropertyResolver propertyResolver;
    private DefaultListableBeanFactory beanFactory;

    public void initService() {
        for (Class<?> serviceClazz : RabbitApplicationMeta.getRabbitServiceClazz()) {
            try {
                initServiceBean(serviceClazz);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initServiceBean(Class<?> serviceClazz) {
        RabbitService rabbitService = serviceClazz.getAnnotation(RabbitService.class);
        if (null == rabbitService) {
            return;
        }
        String service = rabbitService.value();
        if (null != RabbitApplicationMeta.getRabbitService(service)) {
            RabbitApplicationMeta.addClassService(serviceClazz, service);
            return;
        }
        RabbitServiceProperty rabbitServiceProperty = initProperty(rabbitService);
        // 初始化依赖bean
        String messageConverterBeanName = rabbitService.messageConverter();
        MessageConverter messageConverter = StringUtils.isEmpty(messageConverterBeanName) ? new Jackson2JsonMessageConverter() : beanFactory.getBean(messageConverterBeanName, MessageConverter.class);
        CachingConnectionFactory cachingConnectionFactory = getConnectionFactory(rabbitServiceProperty.getHost(), rabbitServiceProperty.getPort(), rabbitServiceProperty.getUsername(), rabbitServiceProperty.getPassword(), rabbitServiceProperty.getVirtualHost());
        RabbitTemplate rabbitTemplate = getRabbitTemplate(cachingConnectionFactory);
        SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory = getSimpleRabbitListenerContainerFactory(cachingConnectionFactory, messageConverter);
        RabbitAdmin rabbitAdmin = new RabbitAdmin(cachingConnectionFactory);
        registerBean(service + "ConnectionFactory", cachingConnectionFactory);
        registerBean(service + "RabbitTemplate", rabbitTemplate);
        registerBean(service + "SimpleRabbitListenerContainerFactory", simpleRabbitListenerContainerFactory);
        registerBean(service + "RabbitAdmin", rabbitAdmin);
        rabbitServiceProperty.setMessageConverter(messageConverter);
        rabbitServiceProperty.setCachingConnectionFactory(cachingConnectionFactory);
        rabbitServiceProperty.setRabbitTemplate(rabbitTemplate);
        rabbitServiceProperty.setSimpleRabbitListenerContainerFactory(simpleRabbitListenerContainerFactory);
        rabbitServiceProperty.setRabbitAdmin(rabbitAdmin);
        RabbitApplicationMeta.addRabbitService(service, rabbitServiceProperty);
        RabbitApplicationMeta.addClassService(serviceClazz, service);
        log.info("初始化RabbitService：[service={}]", rabbitService.value());
    }

    private RabbitServiceProperty initProperty(RabbitService rabbitService) {
        String service = rabbitService.value();
        return RabbitServiceProperty.builder()
                .service(service)
                .name(propertyResolver.resolve(rabbitService.name()))
                .host(propertyResolver.resolve(RabbitServiceProfile.HOST.getProfileName(service)))
                .port(Integer.valueOf(propertyResolver.resolve(RabbitServiceProfile.PORT.getProfileName(service))))
                .username(propertyResolver.resolve(RabbitServiceProfile.USER_NAME.getProfileName(service)))
                .password(propertyResolver.resolve(RabbitServiceProfile.PASSWORD.getProfileName(service)))
                .virtualHost(propertyResolver.resolve(RabbitServiceProfile.VIRTUAL_HOST.getProfileName(service)))
                .listenerQueues(new ArrayList<>())
                .build();
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

    private RabbitTemplate getRabbitTemplate(CachingConnectionFactory cachingConnectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setChannelTransacted(true);
        rabbitTemplate.setReplyTimeout(5000);
        return rabbitTemplate;
    }

    private SimpleRabbitListenerContainerFactory getSimpleRabbitListenerContainerFactory(CachingConnectionFactory cachingConnectionFactory, MessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory);
        factory.setMessageConverter(messageConverter);
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        factory.setPrefetchCount(10);
        factory.setAutoStartup(true);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }

    private void registerBean(String beanName, Object singletonObject) {
        if (!beanFactory.containsBean(beanName)) {
            beanFactory.registerSingleton(beanName, singletonObject);
        }
    }
}
