package com.lfd.soa.gateway.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

/**
 * 描述: 应用启动监听
 *
 * @author linfengda
 * @create 2020-01-09 09:18
 */
@Configuration
@Slf4j
public class ApplicationStartup {

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("demo-gateway应用程序初始化中......");
        log.info("demo-gateway应用程序初始化完成，当前版本{}", Constant.VERSION);
    }
}
