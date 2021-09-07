package com.lfd.soa.srv.demo.remote;

import com.lfd.soa.api.demo.ctrl.MaterialOrderApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * material srv remote call
 *
 * @author linfengda
 * @date 2021-09-02 21:19
 */
@FeignClient(name = "materialOrderService", url = "http://localhost:9000", configuration = {AppKeyInterceptor.class})
public interface MaterialOrderService extends MaterialOrderApi {
}
