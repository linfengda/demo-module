package com.lfd.soa.srv.demo.support.schedule.scanner;

import com.lfd.soa.srv.demo.support.schedule.annotation.JobController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * 定时任务注解扫描{@link JobController}
 *
 * @author linfengda
 * @date 2021-02-03 16:33
 */
@Slf4j
public class TaskControllerScanner extends ClassPathBeanDefinitionScanner {

    public TaskControllerScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    /**
     * 扫描定时任务类
     * @param basePackages  包路径
     */
    public void doScanner(String... basePackages) {
        // 添加过滤条件
        addIncludeFilter(new AnnotationTypeFilter(JobController.class));
        // 调用spring的扫描
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        for (BeanDefinitionHolder holder : beanDefinitionHolders) {
            GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
            String beanClassName = definition.getBeanClassName();
            try {
                Class<?> clazz = Class.forName(beanClassName);
                TaskControllerClassMeta.addClazz(clazz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
