package com.lfd.soa.srv.demo.support.queue.scanner;

import com.lfd.soa.srv.demo.support.queue.annotation.ConsumerService;
import com.lfd.soa.srv.demo.support.queue.proxy.ConsumerProxyFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * 扫描MQ消费服务注解{@link ConsumerService}
 * @author linfengda
 * @date 2021-01-15 11:07
 */
@Slf4j
public class ConsumerServiceScanner extends ClassPathBeanDefinitionScanner {

    public ConsumerServiceScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    /**
     * 扫描类
     * @param basePackages  包路径
     */
    public void doScanner(String... basePackages) {
        // 添加过滤条件
        addIncludeFilter(new AnnotationTypeFilter(ConsumerService.class));
        // 调用spring的扫描
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        for (BeanDefinitionHolder holder : beanDefinitionHolders) {
            GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
            String className = definition.getBeanClassName();
            log.info("Consumer：[class={}]", className);
            try {
                ConsumerServiceClassMeta.addClazz(Class.forName(className));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void registrarBean(Set<BeanDefinitionHolder> beanDefinitionHolders){
        for (BeanDefinitionHolder holder : beanDefinitionHolders) {
            GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
            Class<?> clazz = definition.getBeanClass();
            definition.getConstructorArgumentValues().addGenericArgumentValue(clazz);
            definition.setBeanClass(ConsumerProxyFactoryBean.class);
            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
            super.getRegistry().registerBeanDefinition(convertClassSimpleName(clazz.getSimpleName()), definition);
        }
    }

    private String convertClassSimpleName(String simpleName){
        return Character.toLowerCase(simpleName.charAt(0))+simpleName.substring(1);
    }
}
