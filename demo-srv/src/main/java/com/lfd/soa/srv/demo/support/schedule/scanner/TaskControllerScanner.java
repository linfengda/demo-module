package com.lfd.soa.srv.demo.support.schedule.scanner;

import com.lfd.soa.srv.demo.support.schedule.annotation.JobController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.HashSet;
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
     * @return              JobController Class Set
     */
    public Set<Class<?>> doScanner(String... basePackages) {
        Set<Class<?>> jobControllers = new HashSet<>(16);
        // 添加过滤条件
        addIncludeFilter(new AnnotationTypeFilter(JobController.class));
        // 调用spring的扫描
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        for (BeanDefinitionHolder holder : beanDefinitionHolders) {
            GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
            String className = definition.getBeanClassName();
            log.info("JobController：[class={}]", className);
            try {
                jobControllers.add(Class.forName(className));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jobControllers;
    }
}
