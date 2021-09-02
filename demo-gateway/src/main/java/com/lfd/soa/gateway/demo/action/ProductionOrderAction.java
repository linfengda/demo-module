package com.lfd.soa.gateway.demo.action;

import com.lfd.soa.common.bean.Resp;
import com.lfd.soa.gateway.demo.remote.ProductionOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * production order action
 *
 * @author linfengda
 * @date 2021-09-02 21:24
 */
@Api(tags = "大货订单")
@RestController
@RequestMapping("/v1/productionOrder")
public class ProductionOrderAction {
    @Resource
    private ProductionOrderService productionOrderService;


    @GetMapping("/getOrderDetail")
    public Resp<String> getOrderDetail(@ApiParam("生产订单id") @RequestParam(value = "id") Integer id) {
        return productionOrderService.getOrderDetail(id);
    }
}
