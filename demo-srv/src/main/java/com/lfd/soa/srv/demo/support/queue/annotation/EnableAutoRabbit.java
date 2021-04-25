package com.lfd.soa.srv.demo.support.queue.annotation;

import com.lfd.soa.srv.demo.support.queue.consumer.interceptor.RabbitListenerImportSelector;
import com.lfd.soa.srv.demo.support.queue.scanner.RabbitApplicationRegister;
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
@Import({RabbitApplicationRegister.class, RabbitListenerImportSelector.class})
public @interface EnableAutoRabbit {
    /**
     * sender、listener类所在包路径
     * @return
     */
    String basePackage() default "";
    /**
     * 开启本地消费重试：1.可以避免一个消息消费失败，其它消息全部挤压的情况，2.消息落表后可以自定义消费顺序
     * @return
     */
    boolean reConsume() default false;
}
