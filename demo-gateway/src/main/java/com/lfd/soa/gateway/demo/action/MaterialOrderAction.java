package com.lfd.soa.gateway.demo.action;

import com.lfd.soa.common.bean.Resp;
import com.lfd.soa.gateway.demo.remote.MaterialOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * material order action
 *
 * @author linfengda
 * @date 2021-09-02 21:21
 */
@Api(tags = "面料采购单")
@RestController
@RequestMapping("/v1/materialOrder")
public class MaterialOrderAction {
    @Resource
    private MaterialOrderService materialOrderService;


    @ApiOperation("查询生产订单物料详情")
    @GetMapping("/getOrderBigBomMaterials")
    public Resp<String> getOrderBigBomMaterials(@ApiParam("sku") @RequestParam(value = "sku") String sku) {
        return materialOrderService.getOrderBigBomMaterials(sku);
    }
}
