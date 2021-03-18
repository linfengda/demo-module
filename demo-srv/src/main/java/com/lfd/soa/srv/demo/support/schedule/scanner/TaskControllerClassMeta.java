package com.lfd.soa.srv.demo.support.schedule.scanner;

import java.util.HashSet;
import java.util.Set;

/**
 * 定时任务类元数据缓存
 *
 * @author linfengda
 * @date 2021-02-03 16:39
 */
public class TaskControllerClassMeta {
    private static final Set<Class<?>> TASK_CONTROLLER_CLAZZ = new HashSet<>(16);

    public static void addClazz(Class<?> clazz){
        TASK_CONTROLLER_CLAZZ.add(clazz);
    }

    public static Set<Class<?>> getTaskControllerClazz(){
        return TASK_CONTROLLER_CLAZZ;
    }
}
