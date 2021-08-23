package com.lfd.soa.srv.demo.util;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description:
 * @author: linfengda
 * @date: 2021-08-23 16:34
 */
public class ThreadPoolUtil {

    public static ThreadPoolTaskExecutor initThreadPool(int corePoolSize, int maxPoolSize, int queueCapacity, String prefix, ThreadPoolExecutor.DiscardPolicy discardPolicy){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(60);
        executor.setRejectedExecutionHandler(null);
        executor.setThreadNamePrefix(prefix);
        executor.afterPropertiesSet();
        return executor;
    }
}
