package com.lfd.soa.srv.demo;

import com.lfd.soa.srv.demo.support.orm.auto.UserAware;
import com.lfd.soa.srv.demo.support.orm.auto.UserHolder;
import com.lfd.soa.srv.demo.util.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
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
        log.info("demo-srv应用程序初始化中......");
        ApplicationContext applicationContext = event.getApplicationContext();
        UserHolder.init(applicationContext.getBean(UserAware.class));
        ApplicationContextUtil.setApplicationContext(applicationContext);
        log.info("demo-srv应用程序初始化完成，当前版本{}", Constant.VERSION);
    }
}
