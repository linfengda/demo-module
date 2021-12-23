package com.lfd.soa.demo.srv.support.mq.annotation;

import com.lfd.soa.demo.srv.support.mq.GlobalMQConfig;
import com.lfd.soa.demo.srv.support.mq.consumer.interceptor.RabbitListenerImportSelector;
import com.lfd.soa.demo.srv.support.mq.scanner.RabbitApplicationRegister;
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
@Import({GlobalMQConfig.class, RabbitApplicationRegister.class, RabbitListenerImportSelector.class})
public @interface EnableAutoRabbit {
    /**
     * sender、listener类所在包路径
     * @return
     */
    String basePackage() default "";
}
