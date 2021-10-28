package com.lfd.soa.demo.srv.support.schedule.scanner;

import com.lfd.soa.demo.srv.support.schedule.annotation.JobMapping;
import com.lfd.soa.demo.srv.support.schedule.bean.JobCell;
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
     * @param jobControllers    JobController Class Set
     * @return                  任务集合
     */
    public Map<String, JobCell> scanJob(Set<Class<?>> jobControllers) {
        Map<String, JobCell> jobMappingMap = new HashMap<>(32);
        for (Class<?> clazz : jobControllers) {
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
                jobCell.setTargetClass(clazz);
                jobCell.setTargetMethod(method);
                jobMappingMap.put(jobMapping.value(), jobCell);
            }
        }
        return jobMappingMap;
    }
}
