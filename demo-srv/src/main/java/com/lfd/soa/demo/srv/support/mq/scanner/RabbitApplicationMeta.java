package com.lfd.soa.demo.srv.support.mq.scanner;

import com.lfd.soa.demo.srv.support.mq.bean.RabbitServiceProperty;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * RabbitMQ服务元数据缓存
 *
 * @author linfengda
 * @date 2021-03-25 10:30
 */
public class RabbitApplicationMeta {
    /**
     * RabbitMQ服务类缓存
     */
    private static final Set<Class<?>> RABBIT_SERVICE_CLAZZ = new HashSet<>(8);
    /**
     * RabbitMQ服务缓存：service:RabbitServiceProperty
     */
    private static final Map<String, RabbitServiceProperty> RABBIT_SERVICE_CACHE = new HashMap<>(8);
    /**
     * RabbitMQ服务映射：class:service
     */
    private static final Map<Class<?>, String> CLASS_SERVICE_CACHE = new HashMap<>(8);


    public static void addRabbitServiceClazz(Class<?> clazz) {
        RABBIT_SERVICE_CLAZZ.add(clazz);
    }

    public static Set<Class<?>> getRabbitServiceClazz() {
        return RABBIT_SERVICE_CLAZZ;
    }

    public static void addRabbitService(String service, RabbitServiceProperty rabbitServiceProperty) {
        RABBIT_SERVICE_CACHE.put(service, rabbitServiceProperty);
    }

    public static RabbitServiceProperty getRabbitService(String service) {
        return RABBIT_SERVICE_CACHE.get(service);
    }

    public static void addClassService(Class<?> clazz, String service) {
        CLASS_SERVICE_CACHE.put(clazz, service);
    }

    public static String getClassService(Class<?> clazz) {
        return CLASS_SERVICE_CACHE.get(clazz);
    }
}
