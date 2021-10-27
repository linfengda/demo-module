package com.lfd.soa.srv.demo.ctrl;

import com.lfd.soa.api.demo.sdk.MaterialOrderApi;
import com.lfd.soa.common.bean.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * 面料采购单ctrl
 *
 * @author linfengda
 * @date 2021-09-02 21:19
 */
@Slf4j
@RestController
public class MaterialOrderCtrl implements MaterialOrderApi {

    @Override
    public Resp<String> getOrderBigBomMaterials(String sku) {
        log.info("查询订单大货物料信息，sku={}", sku);
        return new Resp<>("请求成功");
    }
}
