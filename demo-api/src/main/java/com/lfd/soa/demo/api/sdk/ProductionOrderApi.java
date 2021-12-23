package com.lfd.soa.demo.api.sdk;

import com.lfd.soa.common.bean.resp.Resp;
import com.lfd.soa.demo.api.req.ProduceOrderExcelReq;
import com.lfd.soa.demo.api.resp.ProduceOrderExcelResp;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * @throws Exception
     */
    @GetMapping("/getOrderDetail")
    Resp<String> getOrderDetail(@RequestParam(value = "id") Integer id) throws Exception;

    /**
     * 查询订单导出数据
     *
     * @param produceOrderExcelReq  导出查询req
     * @return                      List<ProduceOrderExcelResp>
     * @throws Exception
     */
    @PostMapping("/queryExport")
    List<ProduceOrderExcelResp> queryExport(@RequestBody ProduceOrderExcelReq produceOrderExcelReq) throws Exception;
}
