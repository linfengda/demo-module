package com.lfd.soa.demo.srv.support.mq.scanner;

import com.lfd.soa.demo.srv.support.mq.annotation.RabbitService;
import com.lfd.soa.demo.srv.support.mq.bean.RabbitServiceType;
import com.lfd.soa.demo.srv.support.mq.producer.proxy.ProducerProxyFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * 扫描MQ服务注解{@link RabbitService}
 *
 * @author linfengda
 * @date 2021-01-15 11:07
 */
@Slf4j
public class RabbitServiceScanner extends ClassPathBeanDefinitionScanner {

    public RabbitServiceScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    /**
     * 扫描RabbitService类
     *
     * @param basePackages 包路径
     */
    public void doScanner(String... basePackages) {
        // 添加过滤条件
        addIncludeFilter(new AnnotationTypeFilter(RabbitService.class));
        // 调用spring的扫描
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        for (BeanDefinitionHolder holder : beanDefinitionHolders) {
            GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
            String className = definition.getBeanClassName();
            log.info("RabbitService：[class={}]", className);
            try {
                Class<?> serviceClazz = Class.forName(className);
                RabbitApplicationMeta.addRabbitServiceClazz(serviceClazz);
                RabbitService rabbitService = serviceClazz.getAnnotation(RabbitService.class);
                if (RabbitServiceType.PRODUCER == rabbitService.type()) {
                    proxyService(definition, serviceClazz);
                }
            } catch (Exception e) {
                log.error("扫描RabbitService类异常", e);
            }
        }
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        if(beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent()) {
            return true;
        }
        AnnotationMetadata metadata = beanDefinition.getMetadata();
        return (metadata.isIndependent() && (metadata.isConcrete() ||
                (metadata.isAbstract() && metadata.hasAnnotatedMethods(Lookup.class.getName()))));
    }

    private void proxyService(GenericBeanDefinition definition, Class<?> serviceClazz) {
        String className = serviceClazz.getSimpleName();
        definition.getConstructorArgumentValues().addGenericArgumentValue(serviceClazz);
        definition.setBeanClass(ProducerProxyFactoryBean.class);
        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
        super.getRegistry().registerBeanDefinition(convertClassSimpleName(className), definition);
        log.info("代理Producer ：class=[{}]", className);
    }

    private String convertClassSimpleName(String simpleName) {
        return Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
    }
}
