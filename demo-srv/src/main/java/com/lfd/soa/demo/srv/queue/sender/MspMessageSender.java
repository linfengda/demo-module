package com.lfd.soa.demo.srv.queue.sender;

import com.lfd.soa.demo.srv.support.queue.annotation.QueueMapping;
import com.lfd.soa.demo.srv.support.queue.annotation.RabbitQueue;
import com.lfd.soa.demo.srv.support.queue.annotation.RabbitService;
import com.lfd.soa.demo.srv.support.queue.annotation.SendBody;
import com.lfd.soa.demo.srv.support.queue.bean.RabbitServiceType;
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

    @QueueMapping(queue = "mrp_vm_purchase_order", exchange = "msp_purchase_exchange", exchangeType = ExchangeTypes.DIRECT, routingKey = "order_process", desc = "同步mrp板料订单状态")
    @RabbitQueue(queue = "mrp_vm_purchase_order", exchange = "msp_purchase_exchange", exchangeType = ExchangeTypes.DIRECT, routingKey = "order_process")
    void syncOrder(@SendBody String msg);
}
