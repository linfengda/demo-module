package com.lfd.soa.demo.srv.support.queue.annotation;

import com.lfd.soa.demo.srv.support.queue.GlobalMQConfig;
import com.lfd.soa.demo.srv.support.queue.PrimaryConnectionFactoryConfig;
import com.lfd.soa.demo.srv.support.queue.consumer.interceptor.RabbitListenerImportSelector;
import com.lfd.soa.demo.srv.support.queue.scanner.RabbitApplicationRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启MQ服务扩展
 * @author linfengda
 * @date 2021-01-15 11:43
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({PrimaryConnectionFactoryConfig.class, GlobalMQConfig.class, RabbitApplicationRegister.class, RabbitListenerImportSelector.class})
public @interface EnableAutoRabbit {
    /**
     * sender、listener类所在包路径
     * @return
     */
    String basePackage() default "";
}
