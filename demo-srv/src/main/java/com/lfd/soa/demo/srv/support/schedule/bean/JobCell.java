package com.lfd.soa.demo.srv.support.schedule.bean;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * 任务执行的最小单元
 *
 * @author linfengda
 * @date 2021-02-03 16:52
 */
@Data
public class JobCell {
    /**
     * 任务id
     */
    private String value;
    /**
     * 任务描述
     */
    private String desc;
    /**
     * 任务目标类
     */
    private Class<?> targetClass;
    /**
     * 任务目标方法
     */
    private Method targetMethod;
}
