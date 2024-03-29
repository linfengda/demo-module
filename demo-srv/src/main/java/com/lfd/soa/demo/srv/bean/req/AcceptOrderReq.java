package com.lfd.soa.demo.srv.bean.req;

import com.lfd.soa.demo.srv.support.redis.lock.annotation.BusinessLockKey;
import lombok.Data;

/**
 * 工厂接单dto
 * @author linfengda
 * @date 2020-12-22 14:37
 */
@Data
public class AcceptOrderReq {
    /**
     * 订单id
     */
    @BusinessLockKey(index = 0)
    private Integer orderId;
    /**
     * 明细id
     */
    @BusinessLockKey(index = 1)
    private Integer detailId;
}
