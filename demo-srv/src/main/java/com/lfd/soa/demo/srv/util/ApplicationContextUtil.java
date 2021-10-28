package com.lfd.soa.demo.srv.util;

import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

/**
 * <p>  </p>
 *
 * @author linfengda
 * @copyright Copyright (C) 2021 PatPat, Inc. All rights reserved. <br>
 * @company 深圳盈富斯科技有限公司
 * @date 2021-09-12 17:06
 */
public class ApplicationContextUtil {
    /**
     * bean上下文
     */
    private static ApplicationContext applicationContext;
    /**
     * 环境变量上下文
     */
    private static Environment environment;

    public static void setApplicationContext(ApplicationContext context){
        applicationContext = context;
        environment = context.getEnvironment();
    }

    public static <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String name,Class<T> clazz){
        return applicationContext.getBean(name,clazz);
    }

    public static String getEnvironmentToString(String name){
        return environment.getProperty(name);
    }

    public static Integer getInteger(String name){
        return Integer.valueOf(environment.getProperty(name));
    }

    public static Boolean getEnvironmentToBoolean(String name){
        return environment.getProperty(name,Boolean.class);
    }

    private ApplicationContextUtil(){}
}
