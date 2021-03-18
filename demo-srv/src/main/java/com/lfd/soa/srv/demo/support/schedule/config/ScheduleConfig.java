package com.lfd.soa.srv.demo.support.schedule.config;

import com.lfd.soa.srv.demo.support.schedule.scanner.ExtendsBeanRegister;
import org.springframework.context.annotation.Bean;

/**
 * @author linfengda
 * @date 2021-02-03 16:19
 */
public class ScheduleConfig {

    @Bean
    public ExtendsBeanRegister extendsBeanRegister() {
        return new ExtendsBeanRegister();
    }
}
