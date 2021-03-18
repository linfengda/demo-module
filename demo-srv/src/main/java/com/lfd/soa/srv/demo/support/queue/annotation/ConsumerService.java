package com.lfd.soa.srv.demo.support.queue.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示MQ消费服务
 * @author linfengda
 * @date 2020-11-16 11:52
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConsumerService {
    /**
     * 监听服务
     * @return  名称
     */
    String value() default "";
    /**
     * 监听服务名称
     * @return  名称
     */
    String name() default "";
    /**
     * 地址
     * @return  host
     */
    String host() default "";
    /**
     * 端口
     * @return  port
     */
    String port() default "";
    /**
     * 用户名
     * @return  username
     */
    String username() default "";
    /**
     * 密码
     * @return  password
     */
    String password() default "";
    /**
     * virtualHost
     * @return  virtualHost
     */
    String virtualHost() default "";
}
