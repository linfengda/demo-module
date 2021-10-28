package com.lfd.soa.demo.gateway.remote;

import com.lfd.soa.demo.api.sdk.ProductionOrderApi;
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
