package com.lfd.soa.srv.demo.support.schedule.scanner;

import com.lfd.soa.srv.demo.support.schedule.annotation.JobMapping;
import com.lfd.soa.srv.demo.support.schedule.task.bean.JobCell;
import com.lfd.soa.srv.demo.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 定时任务注解扫描{@link JobMapping}
 *
 * @author linfengda
 * @date 2021-02-03 16:33
 */
@Slf4j
public class JobMappingScanner {

    /**
     * 扫描定时任务
     *
     * @return 定时任务Map
     */
    public Map<String, JobCell> scanJob() {
        Set<Class<?>> classes = TaskControllerClassMeta.getTaskControllerClazz();
        Map<String, JobCell> jobMappingMap = new HashMap<>(32);
        for (Class<?> clazz : classes) {
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                JobMapping jobMapping = method.getAnnotation(JobMapping.class);
                if (null == jobMapping) {
                    continue;
                }
                log.info("JobMapped：[value=" + jobMapping.value() + ",desc=" + jobMapping.desc() + ",class=" + clazz.getName() + ",method=" + method.getName() + "]");
                JobCell jobCell = new JobCell();
                jobCell.setValue(jobMapping.value());
                jobCell.setDesc(jobMapping.desc());
                jobCell.setTarget(SpringUtil.getBean(clazz));
                jobCell.setMethod(method);
                jobMappingMap.put(jobMapping.value(), jobCell);
            }
        }
        return jobMappingMap;
    }
}
