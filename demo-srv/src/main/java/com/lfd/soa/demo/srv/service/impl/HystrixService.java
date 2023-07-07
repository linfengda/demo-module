package com.lfd.soa.demo.srv.service.impl;

import com.lfd.soa.common.exception.BusinessException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 描述: 熔断测试服务
 *
 * @author linfengda
 * @create 2019-04-04 16:38
 */
@Slf4j
@Service
public class HystrixService {


    @HystrixCommand(
        // 服务降级方法
        fallbackMethod = "hystrixFallback1",
        // 线程池key
        threadPoolKey = "myHystrixThread",
        threadPoolProperties = {
            // 线程池核心线程数
            @HystrixProperty(name = "coreSize", value = "10")
        },
        commandProperties = {
            // 是否给方法执行设置超时，默认为true
            @HystrixProperty(name = "execution.timeout.enabled", value = "false"),
            // 统计请求的窗口大小
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"),
            // 启用熔断器功能窗口时间内的最小请求数
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
            // 熔断器打开后经过多长时间允许一次请求尝试执行
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "30000")
        })
    public void hystrixMethod1() throws Exception {
        log.info("-------------------------执行方法[hystrixMethod1]");
        Thread.sleep(60000);
    }

    public void hystrixFallback1() throws Exception {
        log.warn("-------------------------执行方法[hystrixFallback1]");
    }

    @HystrixCommand(
        // 服务降级方法
        fallbackMethod = "hystrixFallback2",
        // 线程池key
        threadPoolKey = "myHystrixThread",
        threadPoolProperties = {
            // 线程池核心线程数
            @HystrixProperty(name = "coreSize", value = "10"),
        },
        commandProperties = {
            // 是否给方法执行设置超时，默认为true
            @HystrixProperty(name = "execution.timeout.enabled", value = "false"),
            // 统计请求的窗口大小
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"),
            // 启用熔断器功能窗口时间内的最小请求数
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
            // 熔断器打开后经过多长时间允许一次请求尝试执行
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "30000")
        })
    public void hystrixMethod2(int i) throws Exception {
        log.info("-------------------------执行方法[hystrixMethod2]");
        if (i >= 2) {
            throw new BusinessException("调用外部接口出错！");
        }
    }

    public void hystrixFallback2(int i) throws Exception {
        log.warn("-------------------------执行方法[hystrixFallback2]");
    }

    @HystrixCommand(
        // 服务降级方法
        fallbackMethod = "hystrixFallback3",
        // 线程池key
        threadPoolKey = "myHystrixThread",
        threadPoolProperties = {
            // 线程池核心线程数
            @HystrixProperty(name = "coreSize", value = "10"),
        },
        commandProperties = {
            // 是否给方法执行设置超时，默认为true
            @HystrixProperty(name = "execution.timeout.enabled", value = "true"),
            // 方法执行超时时间
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
            // 统计请求的窗口大小
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"),
            // 启用熔断器功能窗口时间内的最小请求数
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
            // 熔断器打开后经过多长时间允许一次请求尝试执行
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "30000")
        })
    public void hystrixMethod3() throws Exception {
        log.info("-------------------------执行方法[hystrixMethod3]");
        Thread.sleep(3000L);
    }

    public void hystrixFallback3() throws Exception {
        log.warn("-------------------------执行方法[hystrixFallback3]");
    }

    @HystrixCommand(
        // 服务降级方法
        fallbackMethod = "hystrixFallback4",
        // 忽略空指针异常
        ignoreExceptions = NullPointerException.class,
        // 线程池key
        threadPoolKey = "myHystrixThread",
        threadPoolProperties = {
            // 线程池核心线程数
            @HystrixProperty(name = "coreSize", value = "100"),
            // 线程池最大线程数
            @HystrixProperty(name = "maxQueueSize", value = "100")
        },
        commandProperties = {
            // 是否给方法执行设置超时，默认为true
            @HystrixProperty(name = "execution.timeout.enabled", value = "false"),
            // 统计请求的窗口大小
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"),
            // 启用熔断器功能窗口时间内的最小请求数
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
            // 在窗口时间内失败比例>90%启用熔断，默认50%
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "90"),
            // 熔断器打开后经过多长时间允许一次请求尝试执行
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "30000")
        })
    public void hystrixMethod4(int i) throws Exception {
        log.info("-------------------------执行方法[hystrixMethod4]");
        if (i >= 10) {
            throw new BusinessException("调用外部接口出错！");
        }
    }

    public void hystrixFallback4(int i) throws Exception {
        log.warn("-------------------------执行方法[hystrixFallback4]");
    }

}
