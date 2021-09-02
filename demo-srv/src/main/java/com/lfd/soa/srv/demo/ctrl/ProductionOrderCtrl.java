package com.lfd.soa.srv.demo.ctrl;


import com.lfd.soa.api.demo.ctrl.ProductionOrderApi;
import com.lfd.soa.common.bean.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

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

    @Override
    public Resp<String> getOrderDetail(Integer id) {
        log.info("查询生产订单信息，id={}", id);
        return new Resp<>("请求成功");
    }
}
