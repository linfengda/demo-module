package com.lfd.soa.srv.demo.support.schedule.scanner;

import com.lfd.soa.srv.demo.support.schedule.bean.JobCell;
import com.lfd.soa.srv.demo.support.schedule.config.ScheduleAttributeHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

import java.util.Map;
import java.util.Set;

/**
 * 定时任务注解扫描并注册
 *
 * @author linfengda
 * @date 2021-02-03 16:30
 */
@Slf4j
public class ExtendsBeanRegister implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        // 初始化扫描定时任务类
        TaskControllerScanner taskControllerScanner = new TaskControllerScanner(beanDefinitionRegistry, false);
        Set<Class<?>> jobControllers = taskControllerScanner.doScanner(ScheduleAttributeHolder.INSTANCE.getAttributes().getString("basePackage"));
        // 初始化扫描定时任务
        JobMappingScanner jobMappingScanner = new JobMappingScanner();
        Map<String, JobCell> jobCellMap = jobMappingScanner.scanJob(jobControllers);
        // 注册到调度中心
        JobCellRegister jobCellRegister = new JobCellRegister();
        jobCellRegister.register(jobCellMap);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
