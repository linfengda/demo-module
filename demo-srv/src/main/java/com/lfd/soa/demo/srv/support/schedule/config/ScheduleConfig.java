package com.lfd.soa.demo.srv.support.schedule.config;

import com.lfd.soa.demo.srv.support.schedule.scanner.JobBeanRegister;
import org.springframework.context.annotation.Bean;

/**
 * @author linfengda
 * @date 2021-02-03 16:19
 */
public class ScheduleConfig {

    @Bean
    public JobBeanRegister extendsBeanRegister() {
        return new JobBeanRegister();
    }
}
