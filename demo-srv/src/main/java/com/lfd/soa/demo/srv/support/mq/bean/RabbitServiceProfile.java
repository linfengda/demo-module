package com.lfd.soa.demo.srv.support.mq.bean;

import lombok.AllArgsConstructor;

/**
 * 配置项匹配：rabbitmq.*.xxx
 *
 * @author linfengda
 * @date 2021-03-24 18:50
 */
@AllArgsConstructor
public enum RabbitServiceProfile {
    /**
     * 地址
     */
    HOST("${spring.rabbitmq.%s.host}"),
    /**
     * 端口
     */
    PORT("${spring.rabbitmq.%s.port}"),
    /**
     * 用户名
     */
    USER_NAME("${spring.rabbitmq.%s.username}"),
    /**
     * 密码
     */
    PASSWORD("${spring.rabbitmq.%s.password}"),
    /**
     * virtualHost
     */
    VIRTUAL_HOST("${spring.rabbitmq.%s.virtualHost}"),
    ;

    private String name;

    /**
     * 获取配置项名称
     * @param serviceName   服务
     * @return              配置项名称
     */
    public String getProfileName(String serviceName) {
        return String.format(this.name, serviceName);
    }
}
