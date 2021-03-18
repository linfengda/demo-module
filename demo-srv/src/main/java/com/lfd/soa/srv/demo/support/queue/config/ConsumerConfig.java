package com.lfd.soa.srv.demo.support.queue.config;

import com.lfd.soa.srv.demo.listener.SendConfirmCallback;
import com.lfd.soa.srv.demo.listener.SendReturnCallback;
import com.lfd.soa.srv.demo.support.queue.scanner.ExtendsBeanRegister;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 *
 * @author linfengda
 * @date 2021-01-15 11:47
 */
public class ConsumerConfig   {
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private Integer port;
    @Value("${spring.rabbitmq.username}")
    private String userName;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;


    @Primary
    @Bean(name="connectionFactory")
    public ConnectionFactory connectionFactory() {
        return getConnectionFactory(host, port, userName, password, virtualHost);
    }

    @Bean(name = "simpleRabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(@Qualifier("connectionFactory") ConnectionFactory cachingConnectionFactory) {
        return getSimpleRabbitListenerContainerFactory(cachingConnectionFactory);
    }

    @Bean(name = "rabbitTemplate")
    public RabbitTemplate rabbitTemplate(ConnectionFactory cachingConnectionFactory, SendConfirmCallback sendConfirmCallback, SendReturnCallback sendReturnCallback){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(sendConfirmCallback);
        rabbitTemplate.setReturnCallback(sendReturnCallback);
        return rabbitTemplate;
    }

    private ConnectionFactory getConnectionFactory(String host, Integer port, String username, String password, String virtualHost) {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost(host);
        cachingConnectionFactory.setPort(port);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setVirtualHost(virtualHost);
        cachingConnectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);
        return cachingConnectionFactory;
    }

    private SimpleRabbitListenerContainerFactory getSimpleRabbitListenerContainerFactory(ConnectionFactory cachingConnectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        factory.setPrefetchCount(10);
        factory.setAutoStartup(true);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    @Bean(name = "rabbitAdmin")
    @Primary
    public RabbitAdmin rabbitAdmin(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public ExtendsBeanRegister queueServiceBeanRegister() {
        return new ExtendsBeanRegister();
    }
}
