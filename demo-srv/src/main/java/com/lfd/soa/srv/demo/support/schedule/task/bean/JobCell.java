package com.lfd.soa.srv.demo.support.schedule.task.bean;

import lombok.Data;
import org.springframework.util.ReflectionUtils;

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
     * 任务目标实例
     */
    private Object target;
    /**
     * 任务目标方法
     */
    private Method method;
    /**
     * 任务id
     */
    private String value;
    /**
     * 任务描述
     */
    private String desc;

    public void run() {
        ReflectionUtils.invokeMethod(method, target);
    }
}
