package com.lfd.soa.demo.srv.support.queue.consumer.interceptor;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 自动代理RabbitMQ监听方法
 *
 * @author linfengda
 * @date 2021-04-21 23:27
 */
public class RabbitListenerImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importMetadata) {
        return new String[] {RabbitListenerAnnotationConfig.class.getName()};
    }
}
