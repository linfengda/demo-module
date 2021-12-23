package com.lfd.soa.demo.srv.support.queue;

import com.lfd.soa.common.orm.OrmTemplate;
import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * <p>  </p>
 *
 * @author linfengda
 * @copyright Copyright (C) 2021 PatPat, Inc. All rights reserved. <br>
 * @company 深圳盈富斯科技有限公司
 * @date 2021-12-20 10:36
 */
@Configuration
@Getter
public class GlobalMQConfig implements InitializingBean, ApplicationContextAware {
    /**
     * mq消息最大消费次数
     */
    @Value("${queue.plus.maxConsume}")
    private Integer maxConsume;
    /**
     * mq消息traceId字段
     */
    @Value("${queue.plus.traceIdName}")
    private String traceIdName;
    /**
     * mq消息uuid字段
     */
    @Value("${queue.plus.uuidName}")
    private String uuidName;

    private static GlobalMQConfig config;

    @Override
    public void afterPropertiesSet() throws Exception {
        GlobalMQConfig.config = this;
    }

    public static GlobalMQConfig getConfig() {
        return config;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        OrmTemplate.setDataSource(dataSource);
    }
}
