package com.lfd.soa.api.demo.sdk;

import com.lfd.soa.common.bean.Resp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 生产订单api
 *
 * @author zhangchaojie
 * @date 2021-03-18 17:23
 */
@RequestMapping("/v1/productionOrder")
public interface ProductionOrderApi {

    /**
     * 查询生产订单信息
     *
     * @param id 订单id
     * @return Resp<String>
     */
    @GetMapping("/getOrderDetail")
    Resp<String> getOrderDetail(@RequestParam(value = "id") Integer id);
}
