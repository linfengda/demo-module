package com.lfd.soa.srv.demo;

import com.lfd.soa.srv.demo.support.apivalidator.annotation.EnableApiValidator;
import com.lfd.soa.srv.demo.support.queue.annotation.EnableAutoRabbit;
import com.lfd.soa.srv.demo.support.redis.config.annotation.EnableRedis;
import com.lfd.soa.srv.demo.support.schedule.annotation.EnableSchedule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.Ordered;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 描述: 应用启动
 *
 * @author linfengda
 * @create 2020-01-09 09:18
 */
@EnableAutoRabbit(basePackage = "com.chicv.mineral.srv.scm.queue", reConsume = true)
@EnableSchedule(basePackage = "com.lfd.soa.srv.demo.task")
@EnableRedis(enableCacheAnnotation = true, enableBusinessLockAnnotation = true)
@EnableApiValidator()
@EnableTransactionManagement(order = Ordered.LOWEST_PRECEDENCE-1)
@SpringBootApplication()
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
