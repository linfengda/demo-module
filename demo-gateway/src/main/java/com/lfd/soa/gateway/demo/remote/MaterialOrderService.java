package com.lfd.soa.gateway.demo.remote;

import com.lfd.soa.api.demo.ctrl.MaterialOrderApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * material srv remote call
 *
 * @author linfengda
 * @date 2021-09-02 21:19
 */
@FeignClient("demo-service")
public interface MaterialOrderService extends MaterialOrderApi {
}
