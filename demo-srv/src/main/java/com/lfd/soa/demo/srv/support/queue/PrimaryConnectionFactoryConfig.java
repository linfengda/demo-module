package com.lfd.soa.demo.srv.support.queue;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 *
 * @author linfengda
 * @date 2021-01-15 11:47
 */
public class PrimaryConnectionFactoryConfig {
    @Value("${spring.rabbitmq.msp.host}")
    private String host;
    @Value("${spring.rabbitmq.msp.port}")
    private Integer port;
    @Value("${spring.rabbitmq.msp.username}")
    private String username;
    @Value("${spring.rabbitmq.msp.password}")
    private String password;
    @Value("${spring.rabbitmq.msp.virtualHost}")
    private String virtualHost;

    @Primary
    @Bean(name="connectionFactory")
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost(host);
        cachingConnectionFactory.setPort(port);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setVirtualHost(virtualHost);
        cachingConnectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);
        return cachingConnectionFactory;
    }
}
