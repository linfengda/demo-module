package com.lfd.soa.demo.srv.bean.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单状态
 * @author linfengda
 * @date 2020-08-10 15:37
 */
@Getter
@AllArgsConstructor
public enum OrderState {
    /**
     * 待分单
     */
    waitAccept(1, "待分单"),
    /**
     * 生产中
     */
    producing(2, "生产中"),
    /**
     * 待发货
     */
    waitDelivery(3, "待发货"),
    /**
     * 待收货
     */
    waitReceive(4, "待收货"),
    /**
     * 待验收
     */
    waitQc(5, "待验收"),
    /**
     * 退货
     */
    deliveryReturn(6, "退货"),
    /**
     * 结束
     */
    done(7, "结束"),
    ;

    private Integer code;
    private String name;
}
