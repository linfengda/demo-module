package com.lfd.soa.demo.srv.ctrl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lfd.soa.demo.api.req.ProduceOrderExcelReq;
import com.lfd.soa.demo.api.resp.ProduceOrderExcelResp;
import com.lfd.soa.demo.api.sdk.ProductionOrderApi;
import com.lfd.soa.common.bean.Resp;
import com.lfd.soa.demo.srv.service.ProduceOrderService;
import com.lfd.soa.demo.srv.bean.entity.ProduceOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 生产大货订单表 前端控制器
 * </p>
 *
 * @author linfengda
 * @since 2021-03-08
 */
@Slf4j
@RestController
public class ProductionOrderCtrl implements ProductionOrderApi {
    @Resource
    private ProduceOrderService produceOrderService;

    @Override
    public Resp<String> getOrderDetail(Integer id) throws Exception {
        log.info("查询生产订单信息，id={}", id);
        return new Resp<>("请求成功");
    }

    @Override
    public List<ProduceOrderExcelResp> queryExport(ProduceOrderExcelReq produceOrderExcelReq) throws Exception {
        Page<ProduceOrder> page = new Page<>(produceOrderExcelReq.getPage(), produceOrderExcelReq.getPageSize());
        Page<ProduceOrder> pageResult = produceOrderService.page(page);
        List<ProduceOrderExcelResp> produceOrderExcelRespList = new ArrayList<>();
        for (ProduceOrder produceOrder : pageResult.getRecords()) {
            ProduceOrderExcelResp excelResp = new ProduceOrderExcelResp();
            excelResp.setState(produceOrder.getState().getName());
            excelResp.setOrderNumber(produceOrder.getOrderNumber());
            excelResp.setSku(produceOrder.getSku());
            excelResp.setReferenceSku(produceOrder.getReferenceSku());
            excelResp.setPurchasePrice(produceOrder.getPurchasePrice());
            excelResp.setReferenceImageUrl(new URL(produceOrder.getReferenceImageUrl()));
            excelResp.setSupplier(produceOrder.getSupplier());
            excelResp.setSupplierId(produceOrder.getSupplierId());
            excelResp.setMerchandiser(produceOrder.getMerchandiser());
            produceOrderExcelRespList.add(excelResp);
        }
        return produceOrderExcelRespList;
    }
}
