package com.lfd.soa.demo.srv.service;

import com.lfd.soa.demo.srv.bean.req.MokitReq;
import com.lfd.soa.demo.srv.service.impl.HystrixService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 描述: 熔断调用
 *
 * @author linfengda
 * @create 2019-04-04 17:00
 */
@Slf4j
@Component
@EnableScheduling
public class HystrixTestJob {
    @Resource
    private HystrixService hystrixService;

    //@Scheduled(cron = "*/60 * * * * ?")
    public void test1() throws Exception {
        log.info("当并发请求数量 > Hystrix最大线程数量，会发生熔断");
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> {
                try {
                    hystrixService.hystrixMethod1();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
    }

    //@Scheduled(cron = "*/60 * * * * ?")
    public void test2() throws Exception {
        log.info("当总请求数量 > requestVolumeThreshold，且失败请求数量 > 50%时，会发生熔断");
        for (int i = 0; i < 5; i++) {
            hystrixService.hystrixMethod2(i);
        }
        Thread.sleep(10000);
        hystrixService.hystrixMethod2(-1);
        hystrixService.hystrixMethod2(-1);
        hystrixService.hystrixMethod2(-1);
    }

    //@Scheduled(cron = "*/60 * * * * ?")
    public void test3() throws Exception {
        log.info("当总请求数量 > requestVolumeThreshold，且超时请求数量 > 50%时，会发生熔断");
        for (int i = 0; i < 100; i++) {
            hystrixService.hystrixMethod3();
        }
    }

    //@Scheduled(cron = "*/60 * * * * ?")
    public void test4() throws Exception {
        log.info("当总请求数量 > requestVolumeThreshold，且超时请求数量 > 90%时，会发生熔断");
        for (int i = 0; i < 100; i++) {
            hystrixService.hystrixMethod4(i);
        }
        Thread.sleep(10000);
        hystrixService.hystrixMethod2(-1);
        hystrixService.hystrixMethod2(-1);
        hystrixService.hystrixMethod2(-1);
    }
}
