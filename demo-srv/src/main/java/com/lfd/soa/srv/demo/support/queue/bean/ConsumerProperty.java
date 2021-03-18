package com.lfd.soa.srv.demo.support.queue.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消费者信息
 *
 * @author linfengda
 * @date 2021-03-18 17:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsumerProperty {
    /**
     * 监听服务名称
     */
    private String name;
    /**
     * 地址
     */
    private String host;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * virtualHost
     */
    private String virtualHost;
}
