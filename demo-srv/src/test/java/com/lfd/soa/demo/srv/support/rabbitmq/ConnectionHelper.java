package com.lfd.soa.demo.srv.support.rabbitmq;

import com.lfd.soa.common.exception.BusinessException;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述: 获取连接
 *
 * @author linfengda
 * @create 2019-04-18 17:02
 */
@Slf4j
public class ConnectionHelper {
    private static final String host = "159.75.8.254";
    private static final Integer port = 5672;
    private static final String userName = "backend";
    private static final String password = "stylewe";
    private static final String virtualHost = "mes";

    public static synchronized Connection getConnection() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            factory.setPort(port);
            factory.setUsername(userName);
            factory.setPassword(password);
            factory.setVirtualHost(virtualHost);
            return factory.newConnection();
        } catch (Exception e) {
            log.error("获取rabbitmq connection出错：", e);
            throw new BusinessException("获取rabbitmq connection出错：" + e);
        }
    }
}
