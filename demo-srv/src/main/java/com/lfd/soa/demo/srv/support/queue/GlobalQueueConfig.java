package com.lfd.soa.demo.srv.support.queue;

import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

/**
 * <p>  </p>
 *
 * @author linfengda
 * @copyright Copyright (C) 2021 PatPat, Inc. All rights reserved. <br>
 * @company 深圳盈富斯科技有限公司
 * @date 2021-12-20 10:36
 */
@Configurable
@Getter
public class GlobalQueueConfig implements InitializingBean {
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

    private static GlobalQueueConfig config;

    @Override
    public void afterPropertiesSet() throws Exception {
        GlobalQueueConfig.config = this;
    }

    public static GlobalQueueConfig getConfig() {
        return config;
    }
}
