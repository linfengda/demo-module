package com.lfd.soa.api.demo.ctrl;

import com.lfd.soa.common.bean.Resp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 面料采购单api
 *
 * @author linfengda
 * @date 2021-09-02 21:19
 */
@RequestMapping("/v1/materialOrder")
public interface MaterialOrderApi {

    /**
     * 查询面料采购单信息
     * @param sku sku
     * @return  面料采购单信息
     */
    @GetMapping("/getOrderBigBomMaterials")
    Resp<String> getOrderBigBomMaterials(@RequestParam(value = "sku") String sku);
}
