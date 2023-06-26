package com.lfd.soa.demo.srv.bean.dto;

import com.lfd.soa.demo.srv.bean.enums.OrderEvent;
import lombok.Data;

/**
 * 订单状态更新dto
 * @author linfengda
 * @date 2020-11-09 00:19
 */
@Data
public class OrderStateChangeDto {
    private OrderEvent event;
}
