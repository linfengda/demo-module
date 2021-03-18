package com.lfd.soa.srv.demo.support.queue.scanner;

import java.util.HashSet;
import java.util.Set;

/**
 * 消费服务类元数据缓存
 *
 * @author linfengda
 * @date 2021-02-03 16:39
 */
public class ConsumerServiceClassMeta {
    private static final Set<Class<?>> CONSUMER_SERVICE_CLAZZ = new HashSet<>(16);

    public static void addClazz(Class<?> clazz){
        CONSUMER_SERVICE_CLAZZ.add(clazz);
    }

    public static Set<Class<?>> getClazz(){
        return CONSUMER_SERVICE_CLAZZ;
    }
}
