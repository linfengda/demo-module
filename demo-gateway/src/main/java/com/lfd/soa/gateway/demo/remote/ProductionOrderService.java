package com.lfd.soa.gateway.demo.remote;

import com.lfd.soa.api.demo.sdk.ProductionOrderApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * production srv remote call
 *
 * @author linfengda
 * @date 2021-09-02 21:19
 */
@FeignClient("demo-service")
public interface ProductionOrderService extends ProductionOrderApi {

}
