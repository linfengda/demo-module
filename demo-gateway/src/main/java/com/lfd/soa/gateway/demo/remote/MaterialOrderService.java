package com.lfd.soa.gateway.demo.remote;

import com.lfd.soa.api.demo.sdk.MaterialOrderApi;
import com.lfd.soa.gateway.demo.config.AppKeyInterceptor;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * material srv remote call
 *
 * @author linfengda
 * @date 2021-09-02 21:19
 */
@FeignClient(value = "demo-service", configuration = {AppKeyInterceptor.class})
public interface MaterialOrderService extends MaterialOrderApi {
}
