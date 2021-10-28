package com.lfd.soa.demo.srv.support.queue.consumer.interceptor;

import com.lfd.soa.demo.srv.support.queue.annotation.EnableAutoRabbit;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author linfengda
 * @date 2021-04-21 23:27
 */
public class RabbitListenerImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importMetadata) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importMetadata.getAnnotationAttributes(EnableAutoRabbit.class.getName(), false));
        if (attributes == null) {
            throw new IllegalArgumentException("@EnableAutoRabbit is not present on importing class " + importMetadata.getClassName());
        }
        boolean reConsume = attributes.getBoolean("reConsume");
        if (reConsume) {
            return new String[] {RabbitListenerAnnotationConfig.class.getName()};
        }
        return new String[0];
    }
}
