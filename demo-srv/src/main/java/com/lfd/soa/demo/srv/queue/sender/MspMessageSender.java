package com.lfd.soa.demo.srv.queue.sender;

import com.lfd.soa.demo.srv.support.mq.annotation.QueueMapping;
import com.lfd.soa.demo.srv.support.mq.annotation.RabbitQueue;
import com.lfd.soa.demo.srv.support.mq.annotation.RabbitService;
import com.lfd.soa.demo.srv.support.mq.annotation.SendBody;
import com.lfd.soa.demo.srv.support.mq.bean.RabbitServiceType;
import org.springframework.amqp.core.ExchangeTypes;

/**
 * <p>  </p>
 *
 * @author linfengda
 * @copyright Copyright (C) 2021 PatPat, Inc. All rights reserved. <br>
 * @company 深圳盈富斯科技有限公司
 * @date 2021-12-20 11:30
 */
@RabbitService(type = RabbitServiceType.PRODUCER, value = "msp", name = "msp系统RabbitMQ")
public interface MspMessageSender {

    @QueueMapping(queue = "demo_queue", exchange = "demo_exchange", exchangeType = ExchangeTypes.DIRECT, routingKey = "test", desc = "发送测试消息")
    @RabbitQueue(queue = "demo_queue", exchange = "demo_exchange", exchangeType = ExchangeTypes.DIRECT, routingKey = "test")
    void syncOrder(@SendBody String msg);
}
