package com.lfd.soa.srv.demo.config;

import com.lfd.soa.srv.demo.listener.SendConfirmCallback;
import com.lfd.soa.srv.demo.listener.SendReturnCallback;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author linfengda
 * @date 2021-01-15 11:47
 */
@Configuration
public class ProducerConfig {

    //Bean(name = "rabbitTemplate")
    public RabbitTemplate rabbitTemplate(ConnectionFactory cachingConnectionFactory, SendConfirmCallback sendConfirmCallback, SendReturnCallback sendReturnCallback){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(sendConfirmCallback);
        rabbitTemplate.setReturnCallback(sendReturnCallback);
        return rabbitTemplate;
    }
}
