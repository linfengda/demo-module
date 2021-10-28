package com.lfd.soa.demo.srv.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lfd.soa.demo.srv.bean.entity.ProduceOrder;
import com.lfd.soa.demo.srv.mapper.ProduceOrderMapper;
import com.lfd.soa.demo.srv.service.ProduceOrderService;
import com.lfd.soa.demo.srv.bean.dto.OrderStateChangeDto;
import com.lfd.soa.demo.srv.bean.type.OrderEvent;
import com.lfd.soa.demo.srv.bean.type.OrderState;
import com.lfd.soa.demo.srv.support.statemachine.GenericStateMachine;
import com.lfd.soa.demo.srv.support.statemachine.StateMachine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 生产大货订单表 服务实现类
 * </p>
 *
 * @author linfengda
 * @since 2021-03-08
 */
@Slf4j
@Service
public class ProduceOrderServiceImpl extends ServiceImpl<ProduceOrderMapper, ProduceOrder> implements ProduceOrderService {

    @Override
    public void changeOrderStatus(OrderStateChangeDto orderStateChangeDto) {
        StateMachine<OrderState, OrderEvent> orderStateMachine = this.buildOrderMachine();
        boolean result = orderStateMachine.sendEvent(orderStateChangeDto.getEvent());
        if (result) {
            log.info("状态机校验通过，允许订单状态更新！");
        }else {
            log.info("状态机校验未通过，不允许订单状态更新！");
        }
    }

    private StateMachine<OrderState, OrderEvent> buildOrderMachine() {
        StateMachine<OrderState, OrderEvent> orderStateMachine = new GenericStateMachine<>();
        orderStateMachine
                .initState(OrderState.waitAccept)
                .build(OrderState.waitAccept, OrderState.producing, OrderEvent.ACCEPT_ORDER)
                .build(OrderState.producing, OrderState.waitDelivery, OrderEvent.PACKAGE)
                .build(OrderState.waitDelivery, OrderState.waitReceive, OrderEvent.SEND)
                .build(OrderState.waitReceive, OrderState.waitQc, OrderEvent.RECEIVE)
                .build(OrderState.waitQc, OrderState.deliveryReturn, OrderEvent.QC_NO_PASS)
                .build(OrderState.waitQc, OrderState.done, OrderEvent.QC_PASS)
        ;
        return orderStateMachine;
    }
}
