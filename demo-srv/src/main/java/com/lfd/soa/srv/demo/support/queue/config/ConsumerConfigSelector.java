package com.lfd.soa.srv.demo.support.queue.config;

import com.lfd.soa.srv.demo.support.queue.annotation.EnableConsumer;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 *
 * @author linfengda
 * @date 2021-01-15 11:45
 */
public class ConsumerConfigSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importMetadata) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importMetadata.getAnnotationAttributes(EnableConsumer.class.getName(), false));
        if (attributes == null) {
            throw new IllegalArgumentException("@EnableConsumer is not present on importing class " + importMetadata.getClassName());
        }
        ConsumerAttributeHolder.INSTANCE.init(attributes);
        return new String[]{ConsumerConfig.class.getName()};
    }
}
