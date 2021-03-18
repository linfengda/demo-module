package com.lfd.soa.srv.demo.support.queue.annotation;

import com.lfd.soa.srv.demo.support.queue.config.ConsumerConfigSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启rabbitmq队列消费扩展
 * @author linfengda
 * @date 2021-01-15 11:43
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ConsumerConfigSelector.class})
public @interface EnableConsumer {
    /**
     * listener所在包路径
     * @return
     */
    String basePackage() default "";
}
