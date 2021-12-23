package com.lfd.soa.demo.srv.support.queue.message;

import com.lfd.soa.common.orm.annontation.Id;
import com.lfd.soa.common.orm.annontation.Table;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * mq消息表
 * </p>
 *
 * @author linfengda
 * @since 2021-04-25
 */
@Data
@Table(name = "sys_mq_message")
public class SysMqMessage {
    /**
     * 主键id
     */
    @Id
    private Integer id;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 更新人
     */
    private String updateUser;
    /**
     * uuid
     */
    private String uuid;
    /**
     * 请求报文
     */
    private String message;
    /**
     * mq服务
     */
    private String service;
    /**
     * mq队列
     */
    private String queue;
    /**
     * 消息类型，PRODUCER：发送，CONSUMER：接收
     */
    private String type;
    /**
     * 发送状态(0:等待发送，1：发送成功，2：死亡)
     */
    private Integer sendState;
    /**
     * 消费状态(0:等待消费，1：消费成功，2：死亡)
     */
    private Integer consumeState;
    /**
     * 重试次数
     */
    private Integer tryCount;
    /**
     * 重试时间
     */
    private Date tryTime;
    /**
     * 处理成功时间
     */
    private Date successTime;
    /**
     * 执行错误信息
     */
    private String errorLog;
}
