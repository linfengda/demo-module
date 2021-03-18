package com.lfd.soa.srv.demo.support.schedule.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * 定时任务注解
 *
 * @author linfengda
 * @date 2021-02-03 15:50
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface JobMapping {

    /**
     * 任务id
     * @return
     */
    String value();

    /**
     * 任务描述
     *
     * @return
     */
    String desc() default "";
}
