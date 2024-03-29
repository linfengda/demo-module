package com.lfd.soa.demo.srv;

import com.lfd.soa.demo.srv.support.mq.annotation.EnableAutoRabbit;
import com.lfd.soa.demo.srv.support.redis.annotation.EnableRedis;
import com.lfd.soa.demo.srv.support.redis.cache.annotation.EnableRedisCache;
import com.lfd.soa.demo.srv.support.redis.lock.annotation.EnableBusinessLock;
import com.lfd.soa.demo.srv.support.apivalidator.annotation.EnableApiValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.Ordered;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 描述: 应用启动
 *
 * @author linfengda
 * @create 2020-01-09 09:18
 */
//@EnableAutoRabbit(basePackage = "com.lfd.soa.demo.srv.queue")
@EnableRedis
@EnableRedisCache
@EnableBusinessLock
@EnableApiValidator()
@EnableTransactionManagement(order = Ordered.LOWEST_PRECEDENCE-1)
@SpringBootApplication(exclude = {RabbitAutoConfiguration.class})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.lfd.soa.demo")
public class DemoSrvApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoSrvApplication.class, args);
	}
}
