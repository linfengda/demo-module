package com.lfd.soa.demo.srv.service.springbootimport.target;

import com.lfd.soa.demo.srv.service.springbootimport.color.Black;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 描述: @Import可以传入ImportBeanDefinitionRegistrar的实现类
 *
 * @author linfengda
 * @create 2019-12-30 15:16
 */
public class ColorImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        registry.registerBeanDefinition("black", new RootBeanDefinition(Black.class));
    }
}
